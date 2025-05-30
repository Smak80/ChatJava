package ru.gr36x.chat.ui;

import java.util.function.Consumer;

public interface UserInterface {

    void showMessage(String message);
    void showInformation(String info);
    void showError(Throwable exception);

    // void show...

    void addInputListener(Consumer<String> listener);
    void removeInputListener(Consumer<String> listener);
    void addErrorListener(Consumer<Throwable> listener);
    void removeErrorListener(Consumer<Throwable> listener);

    // void add_____Listener(Consumer<...> listener);
    // void remove_____Listener(Consumer<...> listener);

}
