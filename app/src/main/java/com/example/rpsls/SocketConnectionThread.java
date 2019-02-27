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

    //TODO: Ask Dr. Ferrer about two listeners. Recieve to get address of person wanting to join and
    // broadcast for person wanting to host?
    public void run() {
        try {
            String address = Connection.receive(socket);
            Connection.broadcast(socket, address);
            socket.close();
            for (ServerListeners listener : listeners) {
                listener.notifyLookingToJoin(address);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
