package com.example.rpsls;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

// Want a data structure, probably an ArrayList of Strings that updates when new sockets are opened on different threads.

/**
  * Adapted from the class created by gabriel on 2/15/19.
  */

public class SocketConnectionThread {
    private Socket socket;
    private ArrayList<ServerListeners> listeners = new ArrayList<>();

    public SocketConnectionThread(Socket socket, ArrayList<ServerListeners> listeners) {
        this.socket = socket;
        this.listeners.addAll(listeners);
    }

    public void run() {
        try {
            Connection.broadcast(socket);
            String address = Connection.receive(socket);
            socket.close();
            for (ServerListeners listener : listeners) {
                listener.notifyLookingToJoin(address, Server.APP_PORT);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
