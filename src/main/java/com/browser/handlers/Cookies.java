package com.browser.handlers;


import net.lingala.zip4j.core.ZipFile;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.lang.reflect.Field;
import java.net.HttpCookie;
import java.net.URL;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;

public class Cookies {
    public void createCookieFile() {
        System.out.println("C:/Users/" + System.getProperty("user.name") + "/AppData/Local/GaniBrowse");
        File f3 = new File("C:/Users/" + System.getProperty("user.name") + "/AppData/Local/GaniBrowse");
        f3.mkdir();
        File f5 = new File("C:/Users/" + System.getProperty("user.name") + "/AppData/Local/GaniBrowse/" + "config.gb");
        try {
            if(!f5.exists()) {
                f5.createNewFile();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        try {
            FileWriter myWriter = new FileWriter(f5);
            myWriter.write("AdBlock:true");
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File f = new File("C:/Users/" + System.getProperty("user.name") + "/AppData/Local/GaniBrowse/" + "cookies.txt");
        try {
            if(!f.exists()) {
                f.createNewFile();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        File f2 = new File("C:/Users/" + System.getProperty("user.name") + "/AppData/Local/GaniBrowse/" + "cookies2.txt");
        try {
            if(!f2.exists()) {
                f2.createNewFile();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        //Downlaod binaries for CSSBOX
        // Download URL: https://download2.gluonhq.com/openjfx/17.0.0.1/openjfx-17.0.0.1_windows-x64_bin-sdk.zip
        File dest = new File("C:/Users/" + System.getProperty("user.name") + "/AppData/Local/GaniBrowse/" + "sdk.zip");
        try {
            URL url = new URL("https://download2.gluonhq.com/openjfx/17.0.0.1/openjfx-17.0.0.1_windows-x64_bin-sdk.zip");
            FileUtils.copyURLToFile(url, dest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Unpack zip
        if(dest.exists()) {
            String source = "C:/Users/" + System.getProperty("user.name") + "/AppData/Local/GaniBrowse/" + "sdk.zip";
            String destination = "C:/Users/" + System.getProperty("user.name") + "/AppData/Local/GaniBrowse/" + "sdk";
            String password = "password";

            try {
                ZipFile zipFile = new ZipFile(source);
                if (zipFile.isEncrypted()) {
                    zipFile.setPassword(password);
                }
                zipFile.extractAll(destination);
            } catch (net.lingala.zip4j.exception.ZipException e) {
                e.printStackTrace();
            }
            if(new File(source).exists()) {
                System.out.println("Downloaded");
            }
        }else{
            System.out.println("SDK not downloaded correctly! Abort!!!");
            System.exit(1);
        }
        try {
            FileWriter myWriter = new FileWriter("start.bat");
            myWriter.write("@echo off\necho GaniBrowse v1.0 startup bat\njava -jar GaniBrowse.jar --module-path " + "\"C:/Users/" + System.getProperty("user.name") + "/AppData/Local/GaniBrowse/" + "sdk/javafx-sdk-17.0.0.1/lib\"" + " --add-modules=javafx.swing,javafx.graphics,javafx.fxml,javafx.media,javafx.web");
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        public String[] readCoookies() throws ParseException {
        String data1 = "";
        try {
            File myObj = new File("C:/Users/" + System.getProperty("user.name") + "/AppData/Local/GaniBrowse/" + "cookies.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data1.equals("")) {
                    data1 = data;
                }else{
                    data1 = data1 + "\n" + data;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data1.split(":");
    }
    public void JsonCookies(String domain, String value) {

    }
    public void writeCookies(String Name, String Value, String Domain, String MaxAge, String Secure) {
        String data1 = "";
        try {
            File myObj = new File("C:/Users/" + System.getProperty("user.name") + "/AppData/Local/GaniBrowse/" + "cookies.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data1.equals("")) {
                    data1 = data;
                }else{
                    data1 = data1 + "\n" + data;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        data1 = data1 + "\n" + Name + ":" + Value + ":" + Domain + ":" + MaxAge + ":" + Secure;
        try {
            FileWriter myWriter = new FileWriter("C:/Users/" + System.getProperty("user.name") + "/AppData/Local/GaniBrowse/" + "cookies.txt");
            myWriter.write(data1);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //Experimental

    public static String toString(HttpCookie cookie) {
        try {
            Class<? extends HttpCookie> clazz = cookie.getClass();
            Field whenCreated = clazz.getDeclaredField("whenCreated");
            whenCreated.setAccessible(true);
            Field header = clazz.getDeclaredField("header");
            header.setAccessible(true);

            return cookie.getName() + "|" + cookie.getValue() +
                    ";Comment=" + cookie.getComment() +
                    ";CommentURL=" + cookie.getCommentURL() +
                    ";Discard=" + cookie.getDiscard() +
                    ";Domain=" + cookie.getDomain() +
                    ";Header=" + header.get(cookie) +
                    ";Max-Age=" + cookie.getMaxAge() +
                    ";Path=" + cookie.getPath() +
                    ";Port=" + cookie.getPortlist() +
                    ";Secure=" + cookie.getSecure() +
                    ";HttpOnly=" + cookie.isHttpOnly() +
                    ";Version=" + cookie.getVersion() +
                    ";WhenCreated=" + whenCreated.get(cookie);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static HttpCookie fromString(String string) {
        String[] split = string.split(";");
        String[] nameValue = split[0].split("|");

        if (nameValue.length < 2)
            return null;

        HttpCookie cookie = new HttpCookie(nameValue[1], nameValue[2]);

        for (int i = 1; i < split.length; i++) {
            String[] value = split[i].split("=");

            if (value.length < 2)
                continue;

            if (value[1].equals("null"))
                value[1] = null;

            try {
                switch (value[0]) {
                    case "Comment":
                        cookie.setComment(value[1]);
                        break;
                    case "CommentURL":
                        cookie.setCommentURL(value[1]);
                        break;
                    case "Discard":
                        cookie.setDiscard(Boolean.parseBoolean(value[1]));
                        break;
                    case "Domain":
                        cookie.setDomain(value[1]);
                        break;
                    case "Header":
                        Field header = cookie.getClass().getDeclaredField("header");
                        header.setAccessible(true);
                        header.set(cookie, value[1]);
                        break;
                    case "Max-Age":
                        cookie.setMaxAge(Long.parseLong(value[1] == null ? "0" : value[1]));
                        break;
                    case "Path":
                        cookie.setPath(value[1]);
                        break;
                    case "Port":
                        cookie.setPortlist(value[1]);
                        break;
                    case "Secure":
                        cookie.setSecure(Boolean.parseBoolean(value[1]));
                        break;
                    case "HttpOnly":
                        cookie.setHttpOnly(Boolean.parseBoolean(value[1]));
                        break;
                    case "Version":
                        cookie.setVersion(Integer.parseInt(value[1] == null ? "0" : value[1]));
                        break;
                    case "WhenCreated":
                        Field whenCreated = cookie.getClass().getDeclaredField("whenCreated");
                        whenCreated.setAccessible(true);
                        whenCreated.set(cookie, Long.parseLong(value[1]));
                        break;
                }
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return cookie;
    }
}
