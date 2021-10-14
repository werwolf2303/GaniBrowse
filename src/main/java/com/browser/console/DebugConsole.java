package com.browser.console;

import java.util.Scanner;

public class DebugConsole {
    public void consoleInit() {
        Thread t = new Thread(this::console);
        t.start();
    }
    private void console() {
        System.out.print("\nGaniBrowse > ");
        Scanner input = new Scanner(System.in);
        String command = input.nextLine();
        if(command.equals("help")) {
            System.out.println("I cant give you any help yet");
        }else{
            if(command.equals("exit")) {
                System.exit(0);
            }else{
                if(command.equals("")) {

                }else {
                    System.out.println("Invalid Command " + command);
                }
            }
        }
        console();
    }
}
