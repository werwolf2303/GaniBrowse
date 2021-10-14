package com.browser.vpn.api;

import java.io.IOException;

public class Disconnect {
    public void disconnect(String name) {
        try {
            Runtime.getRuntime().exec("cmd.exe /c start cmd.exe /c rasdial " + name + " /DISCONNECT");
        }catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
