package com.browser.windows;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import com.browser.events.Close;
import com.browser.events.Hide;
import com.browser.events.Open;
import com.browser.events.Show;
import com.browser.handlers.Config;
import com.browser.handlers.Cookies;
import com.browser.handlers.CustomCookieStore;
import com.browser.protocols.About;
import com.browser.protocols.JAVA;
import com.browser.protocols.settings;
import com.browser.vpn.ui.UI;
import com.browser.windows.DebugWindow;
import com.browser.windows.InjectWindow;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingNode;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.stage.WindowEvent;

import javax.swing.*;

public class MainWindowV2 extends Application {
    WebView webView = null;
    JTextField urls = new JTextField(50);
    String lastproctol = "";
    boolean failed = false;
    boolean ini = false;
    boolean released = true;
    boolean first = true;
    String lasttitle = "";
    int maxY = 0;
    @Override
    public void start(Stage primaryStage) throws IOException {
        Group root = new Group();
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        BorderPane borderPane = new BorderPane();
        double initialWidth = primaryScreenBounds.getWidth()*2/3;
        double initialHeight = primaryScreenBounds.getHeight()*2/3;
        Scene scene = new Scene(root, initialWidth, initialHeight);
        Cookies cookies = new Cookies();
        JButton reload = new JButton("Reload");
        JButton debug = new JButton("Debug");
        JButton vpn = new JButton("VPN");
        JButton inject = new JButton("Inject JS");
        JButton browse = new JButton("Go");
        JButton back = new JButton("Back");
        JButton news = new JButton("New Window");
        JButton forward = new JButton("Forward");
        JPanel tools = new JPanel();
        SwingNode tool = new SwingNode();
        webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        primaryStage.setTitle("GaniBrowse V1.0 - %%");
        borderPane.setTop(tool);
        tools.add(inject);
        news.setVisible(false);
        String[] petStrings = { "Google" };
        JComboBox petList = new JComboBox(petStrings);
        petList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(petList.getSelectedItem()=="") {
                }else{
                    if(petList.getSelectedItem().equals("Google")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                webEngine.load("https://www.google.de");
                            }
                        });
                    }
                }
            }
        });
        vpn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UI().initital();
            }
        });
        tools.add(vpn);
        tools.add(debug);
        tools.add(back);
        tools.add(forward);
        tools.add(urls);
        tools.add(browse);
        tools.add(reload);
        tools.add(news);
        tools.add(petList);
        // LIST VIEW (URLS)
        // BROWSER
        vpn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tool.setContent(tools);
            }
        });
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                webEngine.load("https://www.google.de");
            }
        });
        news.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainWindowV2().initial();
            }
        });
        webEngine.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.81 Safari/537.36");
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
        webEngine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<Worker.State>() {
                    @Override public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                        if (newState == Worker.State.SUCCEEDED) {
                            first = false;
                            if (new Config().read("AdBlock").equals("false")) {
                                released = false;
                            } else {
                                released = true;
                            }
                            cookieManager.getCookieStore().getCookies().forEach(cookie -> {
                                String name = cookie.getName();
                                String value = cookie.getValue();
                                String domain = cookie.getDomain();
                                long maxAge = cookie.getMaxAge(); // seconds
                                boolean secure = cookie.getSecure();
                                cookies.writeCookies(name, value, domain, String.valueOf(maxAge), String.valueOf(secure));
                            });
                            failed = false;
                            primaryStage.setTitle("GaniBrowse V1.0 - " + webEngine.getTitle());
                            //System.out.println(engine.getTitle());
                            if (urls.getText().toLowerCase().equals("about://")) {
                                new About().open(webView);
                            } else {
                                if (urls.getText().toLowerCase().equals("settings://")) {
                                    new settings().open();
                                } else {
                                    if (urls.getText().toLowerCase().equals("java://")) {
                                        new JAVA().open();
                                    } else {
                                        if (released) {
                                            if (urls.getText().toLowerCase().contains("https://www.youtube.com")) {
                                                if (!ini) {
                                                    webEngine.executeScript("document.cookie=\"VISITOR_INFO1_LIVE=oKckVSqvaGw; path=/; domain=.youtube.com\";\n" +
                                                            "window.location.reload();");
                                                }
                                                ini = true;
                                            } else {
                                                ini = false;
                                            }
                                        }
                                        if (urls.getText().toLowerCase().endsWith("zip")) {
                                            System.out.println("File download");
                                        }
                                    }
                                }
                            }
                        }
                        //if (newState == State.RUNNING) {
                        //    if(!first) {
                        //        new SaveWindow().open(frame, urls.getText());
                        //    }
                        //}
                        if (newState == Worker.State.FAILED) {
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
                                    webEngine.load("https://" + urls.getText());
                                }
                                if (!urls.getText().contains("https://")) {
                                    failed = true;
                                    webEngine.load("https://" + urls.getText());
                                }
                                if (failed) {
                                    webEngine.load("http://" + urls.getText().replace("https://", ""));
                                }
                            }
                        }
                        urls.setText(webEngine.getLocation());
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
                        webEngine.executeScript("history.forward()");
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
                        webEngine.executeScript("history.back()");
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
        // THESE METHODS DO GET CALLED (THIS RESIZE APPROACH WORKS)
        // https://blog.idrsolutions.com/2012/11/adding-a-window-resize-listener-to-javafx-scene/
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                //webView.setPrefWidth(newSceneWidth.doubleValue()*0.9);
                webView.setPrefWidth(scene.getWidth());
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                //webView.setPrefHeight(newSceneHeight.doubleValue()*0.98);
                webView.setPrefHeight(scene.getHeight());
            }
        });
        webView.setPrefHeight(scene.getHeight());
        webView.setPrefWidth(scene.getWidth());
        // BORDER PANE
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                                           @Override
                                           public void handle(WindowEvent windowEvent) {

                                           }
                                       });
                borderPane.setCenter(webView);
        root.getChildren().add(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
    public void initial() {
        launch();
    }

}