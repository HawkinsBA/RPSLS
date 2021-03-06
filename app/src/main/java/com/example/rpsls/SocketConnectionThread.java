package com.example.rpsls;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
  * Adapted from the class created by gabriel on 2/15/19.
  */

public class SocketConnectionThread {
    private Socket socket;
    private ArrayList<ServerListener> listeners = new ArrayList<>();

    public SocketConnectionThread(Socket socket, ArrayList<ServerListener> listeners) {
        this.socket = socket;
        this.listeners.addAll(listeners);
    }

    public void run() {
        try {
            String address = Connection.receive(socket);
            socket.close();
            for (ServerListener listener : listeners) {
                listener.notifyConnection(address);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
