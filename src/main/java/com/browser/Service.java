package com.browser;

import com.browser.setup.Setup;
import com.browser.windows.MainWindow;

public class Service {
    public static void main(String[] args) {
        try {
            if(!new Setup().firstLaunch()) {
                new Setup().showSetup();
            }else {
                new MainWindow().init(args);
            }
        }catch (ArrayIndexOutOfBoundsException aiiboe) {
            if(!new Setup().firstLaunch()) {
                new Setup().showSetup();
            }else {
                new MainWindow().init(args);
            }
        }
    }
}
