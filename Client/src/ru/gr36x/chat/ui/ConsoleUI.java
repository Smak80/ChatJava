package ru.gr36x.chat.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

public class ConsoleUI implements UserInterface {

    private final List<Consumer<String>> inputListeners = new ArrayList<>(1);
    private final List<Consumer<Throwable>> errorListeners = new ArrayList<>(1);
    private boolean isRunning = false;
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void showInformation(String info) {
        System.out.println(info);
    }

    @Override
    public void showError(Throwable exception) {
        System.err.println(exception.getMessage());
    }

    @Override
    public void addInputListener(Consumer<String> listener) {
        inputListeners.add(listener);
    }

    @Override
    public void removeInputListener(Consumer<String> listener) {
        inputListeners.remove(listener);
    }

    @Override
    public void addErrorListener(Consumer<Throwable> listener) {
        errorListeners.add(listener);
    }

    @Override
    public void removeErrorListener(Consumer<Throwable> listener) {
        errorListeners.remove(listener);
    }

    public void start(){
        if (!isRunning) {
            isRunning = true;
            new Thread(()->{
                while (isRunning) {
                    try {
                        var input = scanner.nextLine();
                        if (input.equalsIgnoreCase("/stop"))
                            stop();
                        if (!input.isBlank()){
                            for (Consumer<String> inputListener : inputListeners) {
                                inputListener.accept(input);
                            }
                        }
                    } catch (Exception e) {
                        for (Consumer<Throwable> errorListener : errorListeners) {
                            errorListener.accept(e);
                        }
                        break;
                    }
                }
            }).start();
        }
    }

    public void stop(){
        isRunning = false;
        scanner.close();
        for (Consumer<Throwable> errorListener : errorListeners) {
            errorListener.accept(new Exception("Пользовательский интерфейс завершил работу"));
        }
    }
}