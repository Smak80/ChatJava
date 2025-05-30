package ru.gr36x.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

public class Communicator {
    private final PrintWriter sender;
    private final Scanner receiver;
    private boolean isRunning = false;

    private final List<Consumer<String>> messageListeners = new ArrayList<>(1);
    private final List<Consumer<Throwable>> errorListeners = new ArrayList<>(1);

    public void addMessageListener(Consumer<String> listener){ messageListeners.add(listener); }
    public void removeMessageListener(Consumer<String> listener){ messageListeners.remove(listener); }
    public void addErrorListener(Consumer<Throwable> listener){ errorListeners.add(listener); }
    public void removeErrorListener(Consumer<Throwable> listener){ errorListeners.remove(listener); }

    public Communicator(Socket socket) throws IOException {
        sender = new PrintWriter(socket.getOutputStream());
        receiver = new Scanner(socket.getInputStream());
    }

    public void startAccepting(){
        if (!isRunning){
            isRunning = true;
            new Thread(()->{
                while (isRunning){
                    try {
                        var message = receiver.nextLine();
                        for (Consumer<String> messageListener : messageListeners) {
                            messageListener.accept(message);
                        }
                    } catch (Exception e){
                        for (Consumer<Throwable> errorListener : errorListeners) {
                            errorListener.accept(e);
                        }
                    }
                }
            }).start();
        }
    }

    public void send(String message){
        sender.println(message);
        sender.flush();
    }

    public void stop(){
        isRunning = false;
        receiver.close();
        sender.close();
    }
}
