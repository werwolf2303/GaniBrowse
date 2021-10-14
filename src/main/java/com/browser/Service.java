package com.browser;

import com.browser.console.DebugConsole;
import com.browser.setup.Setup;
import com.browser.windows.MainWindow;
import com.browser.windows.MainWindowV2;

public class Service {
    public static void main(String[] args) {
        try {
            if(!new Setup().firstLaunch()) {
                new Setup().showSetup();
            }else {
                new DebugConsole().consoleInit();
                new MainWindowV2().initial();
            }
        }catch (ArrayIndexOutOfBoundsException aiiboe) {
            if(!new Setup().firstLaunch()) {
                new Setup().showSetup();
            }else {
                new MainWindowV2().initial();
            }
        }
    }
}
