package com.example.rpsls;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Invite {
    public static void resolve(Socket target, boolean accept) throws IOException {
        DataOutputStream sockout = new DataOutputStream(target.getOutputStream());
        sockout.writeBoolean(accept);
        sockout.flush();
    }

    public static boolean receive(Socket target) throws IOException {
        DataInputStream sockin = new DataInputStream(target.getInputStream());
        boolean accept = sockin.readBoolean();
        return accept;
    }
}
