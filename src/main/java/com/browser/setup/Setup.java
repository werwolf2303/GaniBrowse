package com.browser.setup;

import com.browser.handlers.Cookies;
import com.browser.windows.MainWindow;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Setup {
    public Boolean firstLaunch() {
        File f = new File("C:/Users/" + System.getProperty("user.name") + "/AppData/Local/GaniBrowse");
        return f.exists();
    }
    int indexing = 0;
    public void showSetup() {
        JFrame main = new JFrame();
        JTextArea info = new JTextArea("Welcome to GaniBrowse version 1.0. \n\nThis browser is written in pure Java and uses cssbox as backend.\n\nThis browser has limited functionality!!\n\n\nKnown bugs:\n\nFTP Protocol creates browser hang");
        JTextArea info2 = new JTextArea("This setup will create files for the browser to work with.\n\nThe files will be created in AppData");
        JTextArea info3 = new JTextArea("\n\n\n Done!");
        JPanel control = new JPanel();
        JPanel infos = new JPanel();
        JButton next = new JButton("Next");
        JButton previous = new JButton("Previous");
        JButton done = new JButton("Done");
        control.add(previous);
        control.add(done);
        control.add(next);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(indexing==0) {
                    info.setVisible(false);
                    info2.setVisible(true);
                    previous.setVisible(true);
                    indexing = indexing + 1;
                }else {
                    if (indexing == 1) {
                        info2.setVisible(false);
                        info3.setVisible(true);
                        next.setVisible(false);
                        previous.setVisible(false);
                        done.setVisible(true);
                        new Cookies().createCookieFile();
                    }
                }
            }
        });
        previous.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(indexing==1) {
                    indexing = indexing -1;
                    info2.setVisible(false);
                    info.setVisible(true);
                    previous.setVisible(false);
                }
            }
        });
        done.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        info3.setEditable(false);
        info2.setEditable(false);
        info.setEditable(false);
        info2.setVisible(false);
        previous.setVisible(false);
        infos.add(info2, BorderLayout.CENTER);
        done.setVisible(false);
        info3.setVisible(false);
        main.add(control, BorderLayout.SOUTH);
        infos.add(info3, BorderLayout.CENTER);
        infos.add(info, BorderLayout.CENTER);
        main.add(infos, BorderLayout.CENTER);
        main.setVisible(true);
        main.pack();
    }
    public static void main(String[] args) {
        new Setup().showSetup();
    }
}
