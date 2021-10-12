package com.browser;

import com.browser.events.*;
import com.browser.handlers.Cookies;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import org.fit.cssbox.demo.SimpleBrowser;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.text.html.CSS;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;

public class MainWindow extends Application {
    WebView webView = new WebView();
    JTextField urls = new JTextField(50);
    @Override
    public void start(final Stage stage) {
        Cookies cookies = new Cookies();
        JFrame frame = new JFrame();
        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("Browser");
        JMenuItem reload = new JMenuItem("Reload");
        JButton browse = new JButton("Go");
        JButton back = new JButton("Back");
        JButton forward = new JButton("Forward");
        JPanel tools = new JPanel();
        bar.add(menu);
        menu.add(reload);
        frame.setJMenuBar(bar);
        tools.add(back);
        tools.add(forward);
        tools.add(urls);
        tools.add(browse);
        cookies.createCookieFile();
        frame.add(tools, BorderLayout.NORTH);
        JFXPanel panels = new JFXPanel();
        WebEngine engine = webView.getEngine();
        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
        engine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<State>() {
                    @Override public void changed(ObservableValue ov, State oldState, State newState) {
                        if (newState == Worker.State.SUCCEEDED) {
                            cookieManager.getCookieStore().getCookies().forEach(cookie -> {
                                String name = cookie.getName();
                                String value = cookie.getValue();
                                String domain = cookie.getDomain();
                                long maxAge = cookie.getMaxAge(); // seconds
                                boolean secure = cookie.getSecure();
                                cookies.writeCookies(name,value,domain,String.valueOf(maxAge),String.valueOf(secure));
                            });
                        }
                    }
                });
        webView.getEngine().load("https://google.com");
        forward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        engine.executeScript("history.forward()");
                    }
                });
            }
        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        engine.executeScript("history.back()");
                    }
                });
            }
        });
        browse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if(!urls.getText().equals("")) {
                            webView.getEngine().load(urls.getText());
                            urls.setText(webView.getEngine().getLocation());
                        }
                    }
                });
            }
        });
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                urls.setText(webView.getEngine().getLocation());
            }
        });
        reload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        webView.getEngine().reload();
                    }
                });
            }
        });
        VBox vBox = new VBox(webView);
        Scene scene = new Scene(vBox, 960, 600);
        panels.setScene(scene);
        frame.setTitle("GaniBrowse V1.0");
        frame.add(panels,BorderLayout.SOUTH);
        frame.setVisible(true);
        frame.pack();
        frame.setAlwaysOnTop(false);
        frame.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                new Open().open();
            }

            public void windowClosing(WindowEvent e) {
                new Close().close();
            }

            public void windowClosed(WindowEvent e) {
                new Close().close();
            }

            public void windowIconified(WindowEvent e) {

            }

            public void windowDeiconified(WindowEvent e) {

            }

            public void windowActivated(WindowEvent e) {
                new Show().show();
            }

            public void windowDeactivated(WindowEvent e) {
                new Hide().hide();
            }
        });
        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                new Resize().resize();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
    public void init() {

    }
}
