package com.browser.handlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Cookies {
    public void createCookieFile() {
        File f = new File("cookies.txt");
        try {
            if(!f.exists()) {
                f.createNewFile();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
    public void writeCookies(String Name, String Value, String Domain, String MaxAge, String Secure) {
        String data1 = "";
        try {
            File myObj = new File("cookies.txt");
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
            FileWriter myWriter = new FileWriter("cookies.txt");
            myWriter.write(data1);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
