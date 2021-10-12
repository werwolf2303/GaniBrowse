package com.browser;

public class Service {
    public static void main(String[] args) {
        try {
            new MainWindow().init();
        }catch (ArrayIndexOutOfBoundsException aiiboe) {
            new MainWindow().init();
        }
    }
}
