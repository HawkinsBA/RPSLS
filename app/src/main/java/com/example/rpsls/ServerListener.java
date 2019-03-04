package com.example.rpsls;

public interface ServerListener {
    void notifyConnection(String target);
    void notifyInviteResolution(boolean accept);
}