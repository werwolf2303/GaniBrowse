package com.browser.protocols;

import javax.swing.*;
import java.awt.*;

public class JAVA extends JFrame {
    public void open() {
        JTextArea area = new JTextArea("This dont work");
        JButton execute = new JButton("Execute");
        area.setEditable(false);
        this.add(area, BorderLayout.CENTER);
        this.add(execute, BorderLayout.SOUTH);
        this.setVisible(true);
        this.pack();
    }
}
