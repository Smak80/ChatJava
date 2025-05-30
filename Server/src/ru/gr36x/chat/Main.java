package ru.gr36x.chat;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            new Server(9360);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
