package ru.gr36x.chat;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Client {

    private final Communicator communicator;
    private final List<Consumer<String>> messageListeners = new ArrayList<>(1);
    private final List<Consumer<Throwable>> errorListeners = new ArrayList<>(1);

    public void addMessageListener(Consumer<String> listener) {
        messageListeners.add(listener);
    }

    public void removeMessageListener(Consumer<String> listener) {
        messageListeners.remove(listener);
    }

    public void addErrorListener(Consumer<Throwable> listener) {
        errorListeners.add(listener);
    }

    public void removeErrorListener(Consumer<Throwable> listener) {
        errorListeners.remove(listener);
    }

    public Client(String host, int port) throws IOException {
        var socket = new Socket(host, port);
        communicator = new Communicator(socket);
        communicator.addMessageListener(this::parse);
        communicator.addErrorListener(_ -> stop());
    }

    public void start() {
        communicator.startAccepting();
    }

    public void stop(){
        communicator.stop();
    }

    public void sendMessage(String message){
        communicator.send(message);
    }

    private void parse(String input){
        for (Consumer<String> messageListener : messageListeners) {
            messageListener.accept(input);
        }
    }
}