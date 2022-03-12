package com.javarush.caesarcipher;

import com.javarush.caesarcipher.dialog.UserDialog;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        UserDialog userDialog = new UserDialog();
        try {
            userDialog.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
