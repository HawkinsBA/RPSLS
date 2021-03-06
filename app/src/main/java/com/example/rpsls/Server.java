package com.example.rpsls;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
  * Adapted from the class created by gabriel on 2/15/19.
  */

public class Server {
    public static final int APP_PORT = 8888;
    private static Server instance;

    public static Server get() throws IOException {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }

    private ServerSocket acceptor;
    private ArrayList<ServerListener> listeners = new ArrayList<>();

    Server() throws IOException {
        acceptor = new ServerSocket(APP_PORT);
    }

    public void addListener(ServerListener listener) {
        this.listeners.add(listener);
    }

    public void listen() throws IOException {
        for (;;) {
            listenForConnections().run();
        }
    }

    public SocketConnectionThread listenForConnections() throws IOException {
        Socket s = acceptor.accept();
        SocketConnectionThread connection = new SocketConnectionThread(s, listeners);
        return connection;
    }
}
