package com.browser.protocols;

import com.browser.handlers.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class settings extends JFrame {
    public void open() {
        JTabbedPane tabs = new JTabbedPane();
        JTextArea info = new JTextArea("YouTube AdBlock");
        JButton change1 = new JButton("Change");
        JPanel first = new JPanel();
        JCheckBox adblock = new JCheckBox();
        first.add(info, BorderLayout.CENTER);
        first.add(adblock, BorderLayout.SOUTH);
        first.add(change1,BorderLayout.SOUTH);
        if(new Config().read("AdBlock").equals("false")) {
            adblock.setSelected(false);
        }else{
            adblock.setSelected(true);
        }
        change1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(adblock.isSelected()) {
                    new Config().overwrite("AdBlock", "true");
                }else{
                    new Config().overwrite("AdBlock", "false");
                }
            }
        });
        adblock.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()==0) {
                    new Config().overwrite("AdBlock", "false");
                }else{
                    new Config().overwrite("AdBlock", "true");
                }
            }
        });
        info.setEditable(false);
        tabs.add("Allgemein",first);
        this.add(tabs, BorderLayout.CENTER);
        this.setVisible(true);
        this.pack();
    }

    public static void main(String[] args) {
        new settings().open();
    }
}
