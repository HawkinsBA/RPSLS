package com.example.rpsls;

import java.net.Socket;

public interface ServerListener {
    void notifyConnection(Socket target);
}