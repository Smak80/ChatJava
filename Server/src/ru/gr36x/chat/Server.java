package ru.gr36x.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket sSocket;
    private boolean isRunning = true;

    public Server(int port) throws IOException {
        sSocket = new ServerSocket(port);
        new Thread(()->{
            while(isRunning) {
                try {
                    Socket socket = sSocket.accept();
                    new ConnectedClient(socket);
                } catch (IOException _) {
                }
            }
        }).start();
    }

    public void stop(){
        isRunning = false;
        try {
            sSocket.close();
        } catch (IOException e) {
        }
    }

}
