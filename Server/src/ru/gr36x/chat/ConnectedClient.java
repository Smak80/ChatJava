package ru.gr36x.chat;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ConnectedClient {

    private final Communicator communicator;

    public ConnectedClient(@NotNull Socket socket){
        try {
            communicator = new Communicator(socket);
            communicator.addMessageListener(this::parse);
            communicator.addErrorListener(ex->{
                stop();
            });
            clients.add(this);
            start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void start(){
        communicator.startAccepting();
    }

    public void send(String message){
        communicator.send(message);
    }

    public void parse(String message){
        sendToAll(message);
    }

    private void sendToAll(String message){
        for (ConnectedClient client : clients) {
            client.send(message);
        }
    }

    public void stop(){
        communicator.stop();
        clients.remove(this);
    }

    private static final List<ConnectedClient> clients = new ArrayList<>(2);
}
