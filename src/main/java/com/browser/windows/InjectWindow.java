package com.browser.windows;

import javafx.application.Platform;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InjectWindow extends JDialog {
    public void inject(WebView view) {
        WebEngine engine = view.getEngine();
        JTextField field = new JTextField(this.getX());
        JButton in = new JButton("Inject");
        in.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if(!field.getText().equals("")) {
                            engine.executeScript(field.getText());
                        }
                    }
                });
            }
        });
        this.add(in,BorderLayout.SOUTH);
        this.add(field, BorderLayout.NORTH);
        this.pack();
        this.setVisible(true);
        this.setTitle("Inject JavaScript");
    }
}
