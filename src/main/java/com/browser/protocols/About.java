package com.browser.protocols;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.*;

public class About extends JDialog {
    public void open(WebView view) {
        WebEngine engine = view.getEngine();
        JTextArea about = new JTextArea("GaniBrowse V1.0 \n\n" + "User-Agent: \n" + engine.getUserAgent());
        about.setEditable(false);
        this.add(about);
        this.setVisible(true);
        this.pack();
    }
}
