package com.browser.windows;

import javafx.scene.control.ScrollPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.w3c.dom.Document;

import javax.swing.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import javax.swing.JScrollPane;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

public class DebugWindow extends JDialog {
    public void debug(WebView view) {
        WebEngine engine = view.getEngine();
        Document doc = engine.getDocument();
        JTextArea de = new JTextArea();
        JScrollPane pane = new JScrollPane(de);
        String out = "";
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            de.setText(writer.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.setTitle("GaniBrowse Debug");
        this.add(pane, BorderLayout.CENTER);
        this.pack();
        this.setVisible(true);
    }
}
