package com.example.rpsls;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
  * Adapted from the class created by gabriel on 2/18/19.
  */

public class Connection {
    public static void broadcast(Socket target, String address) throws IOException {
        PrintWriter sockout = new PrintWriter(target.getOutputStream());
        sockout.print(address);
        sockout.flush();
    }

    public static String receive(Socket target) throws IOException {
        BufferedReader sockin = new BufferedReader(new InputStreamReader(target.getInputStream()));
        while (!sockin.ready()) {}
        StringBuilder input = new StringBuilder();
        while (sockin.ready()) {
            input.append(sockin.readLine());
        }
        return input.toString();
    }
}
