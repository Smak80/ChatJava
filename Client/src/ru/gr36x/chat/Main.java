package ru.gr36x.chat;

import ru.gr36x.chat.ui.ConsoleUI;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        var ui = new ConsoleUI();
        try {
            var client = new Client("127.0.0.1", 9360);
            client.addMessageListener(ui::showMessage);
            client.addErrorListener(e -> {
                ui.showError(e);
                client.stop();
                ui.stop();
            });
            ui.addErrorListener(_ -> {
                ui.stop();
                client.stop();
            });
            ui.addInputListener(client::sendMessage);
            client.start();
            ui.start();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
