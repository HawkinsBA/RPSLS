package com.example.rpsls;

public interface ServerListeners {
    void notifyLookingToJoin(String host);
    void notifyLookingToHost(String host, int port);
}