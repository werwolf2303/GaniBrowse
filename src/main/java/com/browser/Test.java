package com.browser;

import com.browser.experimental.media.CustomMediaPlayer;
import com.browser.experimental.media.MP3;
import com.browser.experimental.media.Sound;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.fit.cssbox.awt.BrowserCanvas;
import org.fit.cssbox.css.CSSNorm;
import org.fit.cssbox.css.DOMAnalyzer;
import org.fit.cssbox.demo.SimpleBrowser;
import org.fit.cssbox.io.DOMSource;
import org.fit.cssbox.io.DefaultDOMSource;
import org.fit.cssbox.io.DefaultDocumentSource;
import org.fit.cssbox.io.DocumentSource;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Test extends Application {
    @Override
    public void start(Stage primaryStage) {
        MP3 mp3 = new MP3();
        mp3.play("Audio.mp3");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
