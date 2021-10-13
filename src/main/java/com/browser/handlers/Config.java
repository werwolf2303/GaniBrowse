package com.browser.handlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Config {
    File config = new File("C:/Users/" + System.getProperty("user.name") + "/AppData/Local/GaniBrowse/" + "config.gb");
    public Boolean write(String name, String value) {
        if(config.exists()) {
            String content = "";
            Scanner myReader = new Scanner(content);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(content.equals("")) {
                    content = data;
                }else{
                    content = content + "\n" + data;
                }
            }
            myReader.close();
            try {
                FileWriter myWriter = new FileWriter(config);
                myWriter.write(content + name + ":" + value);
                myWriter.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }
    public Boolean overwrite(String name, String value) {
        if(config.exists()) {
            String content = "";
            Scanner myReader = new Scanner(content);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.contains("name")) {
                    data = "";
                }
                if(!data.equals("")) {
                    if (content.equals("")) {
                        content = data;
                    } else {
                        content = content + "\n" + data;
                    }
                }
            }
            myReader.close();
            try {
                FileWriter myWriter = new FileWriter(config);
                myWriter.write(content + name + ":" + value);
                myWriter.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }
    public String read(String name) {
        if(config.exists()) {
            String content = "";
            Scanner myReader = new Scanner(content);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(content.equals("")) {
                    content = data;
                }else{
                    content = content + "\n" + data;
                }
            }
            myReader.close();
            return content;
        }
        return null;
    }
}
