package com.browser.windows;

import com.browser.handlers.Downloads;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;

public class SaveWindow {
    public void open(JFrame frame, String download) {
        frame.setEnabled(false);
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        chooser.setDialogTitle("Choose download directory");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File path = chooser.getSelectedFile();
            String[] n = download.split("/");
            frame.setEnabled(true);
            new Downloads().download(path+"\\"+n[n.length-1].replace("%20", " "),download);
        }
    }
}
