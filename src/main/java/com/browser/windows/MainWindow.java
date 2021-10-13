package com.browser.windows;

import com.browser.events.*;
import com.browser.handlers.Config;
import com.browser.handlers.Cookies;
import com.browser.handlers.CustomCookieStore;
import com.browser.protocols.About;
import com.browser.protocols.JAVA;
import com.browser.protocols.settings;
import com.browser.setup.Setup;
import com.browser.windows.DebugWindow;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class MainWindow extends Application {
    WebView webView = new WebView();
    JTextField urls = new JTextField(50);
    String lastproctol = "";
    boolean failed = false;
    boolean ini = false;
    boolean released = true;
    String lasttitle = "";
    int maxY = 0;
    @Override
    public void start(final Stage stage) {
        if(new Config().read("AdBlock").equals("false")) {
            released = false;
        }
        Cookies cookies = new Cookies();
        JFrame frame = new JFrame();
        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("Browser");
        JMenuItem reload = new JMenuItem("Reload");
        JMenuItem debug = new JMenuItem("Debug");
        JMenuItem inject = new JMenuItem("Inject JS");
        JButton browse = new JButton("Go");
        JButton back = new JButton("Back");
        JButton forward = new JButton("Forward");
        JPanel tools = new JPanel();
        JFXPanel panels = new JFXPanel();
        maxY = tools.getY();
        bar.add(menu);
        menu.add(reload);
        menu.add(debug);
        menu.add(inject);
        frame.setJMenuBar(bar);
        tools.add(back);
        tools.add(forward);
        tools.add(urls);
        tools.add(browse);
        frame.add(tools, BorderLayout.NORTH);
        frame.add(panels,BorderLayout.CENTER);
        WebEngine engine = webView.getEngine();
        engine.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.81 Safari/537.36");
        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
        urls.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER) {
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
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        engine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<State>() {
                    @Override public void changed(ObservableValue ov, State oldState, State newState) {
                        if (newState == Worker.State.SUCCEEDED) {
                            if(new Config().read("AdBlock").equals("false")) {
                                released = false;
                            }else{
                                released = true;
                            }
                            cookieManager.getCookieStore().getCookies().forEach(cookie -> {
                                String name = cookie.getName();
                                String value = cookie.getValue();
                                String domain = cookie.getDomain();
                                long maxAge = cookie.getMaxAge(); // seconds
                                boolean secure = cookie.getSecure();
                                cookies.writeCookies(name,value,domain,String.valueOf(maxAge),String.valueOf(secure));
                            });
                            failed = false;
                            frame.setTitle("GaniBrowse V1.0 - " + engine.getTitle());
                            //System.out.println(engine.getTitle());
                            if(urls.getText().toLowerCase().equals("about://")) {
                                new About().open(webView);
                            }else{
                                if(urls.getText().toLowerCase().equals("settings://")) {
                                    new settings().open();
                                }else{
                                    if(urls.getText().toLowerCase().equals("java://")) {
                                        new JAVA().open();
                                    }else{
                                        if(released) {
                                            if (urls.getText().toLowerCase().contains("https://www.youtube.com")) {
                                                if (!ini) {
                                                    engine.executeScript("document.cookie=\"VISITOR_INFO1_LIVE=oKckVSqvaGw; path=/; domain=.youtube.com\";\n" +
                                                            "window.location.reload();");
                                                }
                                                ini = true;
                                            } else {
                                                ini = false;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (newState == State.FAILED) {
                            if(new Config().read("AdBlock").equals("false")) {
                                released = false;
                            }else{
                                released = true;
                            }
                            if(urls.getText().toLowerCase().equals("about://") | urls.getText().toLowerCase().equals("settings://") | urls.getText().toLowerCase().equals("java://")) {
                                if(urls.getText().toLowerCase().equals("settings://")) {
                                    new settings().open();
                                }else{
                                    if(urls.getText().toLowerCase().equals("java://")) {
                                        new JAVA().open();
                                    }else{
                                        new About().open(webView);
                                    }
                                }
                            }else {
                                if (!urls.getText().contains("http://")) {
                                    failed = true;
                                    engine.load("https://" + urls.getText());
                                }
                                if (!urls.getText().contains("https://")) {
                                    failed = true;
                                    engine.load("https://" + urls.getText());
                                }
                                if (failed) {
                                    engine.load("http://" + urls.getText().replace("https://", ""));
                                }
                            }
                        }
                        urls.setText(engine.getLocation());
                        CookieManager man = (CookieManager) CookieHandler.getDefault();
                        CookieStore store = man.getCookieStore();
                        try {
                            Path cookieFilePath = new File("C:/Users/" + System.getProperty("user.name") + "/AppData/Local/GaniBrowse/" + "cookies2.txt").toPath();

                            Files.write(cookieFilePath, ("").getBytes(), StandardOpenOption.TRUNCATE_EXISTING);

                            for (URI uri : store.getURIs()) {
                                for (HttpCookie cookie : store.get(uri)) {
                                    if (cookie.hasExpired())
                                        continue;

                                    Files.write(cookieFilePath, (uri.toString() + "|" + Cookies.toString(cookie) + "~").getBytes(), StandardOpenOption.APPEND);
                                }
                            }

                            Files.write(cookieFilePath, "\n".getBytes(), StandardOpenOption.APPEND);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        CookieManager manager = new CookieManager(new CustomCookieStore(), CookiePolicy.ACCEPT_ALL);

                        try {
                            for (String line : Files.readAllLines(new File("C:/Users/" + System.getProperty("user.name") + "/AppData/Local/GaniBrowse/" + "cookies2.txt").toPath())) {
                                String[] values = line.split("~");

                                for (String header : values) {
                                    HttpCookie cookie = Cookies.fromString(header);

                                    if (cookie == null || cookie.hasExpired())
                                        continue;

                                    manager.getCookieStore().add(new URI(header.split("|")[0]), cookie);
                                }
                            }
                        } catch (IOException | URISyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                });
        webView.getEngine().load("https://google.com");

        inject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InjectWindow window = new InjectWindow();
                window.inject(webView);
            }
        });
        debug.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DebugWindow window = new DebugWindow();
                window.debug(webView);
            }
        });
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
        frame.setTitle("GaniBrowse V1.0 - %%");
        frame.add(panels, BorderLayout.SOUTH);
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
                webView.resize(panels.getX(),panels.getY());
                new Resize().resize();
            }
        });
    }

    public static void main(String[] args) {
        if(!new Setup().firstLaunch()) {
            new Setup().showSetup();
        }else {
            launch();
        }
    }
    public void init(String[] args) {
        launch();
    }
}
