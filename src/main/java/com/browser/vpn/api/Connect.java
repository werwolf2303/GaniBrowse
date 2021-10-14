package com.browser.vpn.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Connect {
    boolean debug = false;
    public void connect(String name) {
        try {
            if(debug) {
                Process process = Runtime.getRuntime().exec("cmd.exe /c rasdial \""+ name + "\" vpn vpn");
                BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = null;
                try {
                    while ((line = input.readLine()) != null) {
                        System.out.println(line);
                    }
                }catch (IOException es) {

                }
            }else{
                if(name.contains(" ")) {
                    Runtime.getRuntime().exec("cmd.exe /c start cmd.exe /c rasdial \"" + name + "\" vpn vpn");
                }else{
                    Runtime.getRuntime().exec("cmd.exe /c start cmd.exe /c rasdial " + name + " vpn vpn");
                }
            }
        }catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
