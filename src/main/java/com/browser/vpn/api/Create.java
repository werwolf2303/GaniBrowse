package com.browser.vpn.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Create {
    String names = "";
    boolean debug = false;
    public void create(String name, String serveraddress) {
        try {
            if(debug) {
                Process process = Runtime.getRuntime().exec("powershell Add-VpnConnection -Name '" + names.replace(" ", "") + "' -ServerAddress " + serveraddress + "");
                BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = null;
                try {
                    while ((line = input.readLine()) != null) {
                        System.out.println(line);
                    }
                }catch (IOException es) {

                }
            }else {
                Runtime.getRuntime().exec("powershell Add-VpnConnection -Name '" + name + "' -ServerAddress " + serveraddress + "");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
