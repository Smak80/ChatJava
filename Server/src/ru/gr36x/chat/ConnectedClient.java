package ru.gr36x.chat;

import org.jetbrains.annotations.NotNull;

import java.net.Socket;

public class ConnectedClient {

    private final Socket socket;

    public ConnectedClient(@NotNull Socket socket){
        this.socket = socket;
        start();
    }

    private void start(){
        new Thread(()->{

        }).start();
    }

}
