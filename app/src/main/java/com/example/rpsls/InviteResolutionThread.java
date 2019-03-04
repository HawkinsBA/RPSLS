package com.example.rpsls;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

class InviteResolutionThread {
    private Socket socket;
    private ArrayList<ServerListener> listeners = new ArrayList<>();

    public InviteResolutionThread(Socket socket, ArrayList<ServerListener> listeners) {
        this.socket = socket;
        this.listeners.addAll(listeners);
    }

    public void run() {
        try {
            boolean accept = Invite.receive(socket);
            socket.close();
            for (ServerListener listener : listeners) {
                listener.notifyInviteResolution(accept);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }
}
