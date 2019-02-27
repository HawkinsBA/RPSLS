package com.example.rpsls;

public interface ServerListeners {
    void notifyLookingToJoin(String host, int port);
    void notifyLookingToHost(String host, int port);
}