package dev.mayuna.nostalgiamanager.api;

public interface Module {

    boolean isEnabled();

    void onLoad();

    default void onUnload() {
        // Empty
    }
}
