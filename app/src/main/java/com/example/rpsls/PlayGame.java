package com.example.rpsls;

import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.net.Socket;

public class PlayGame {

    private void setUpServer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
            }
        });
        try{
            Server.get().addListener(new ServerListener() {
                @Override
                public void notifyConnection(String host) {

                }
            });
            Server.get().listen();
        }
        catch (IOException e){
            Log.e(GameScreen.class.getName(), "Could not connect to server");
        }
    }

    public static void sendMove(final String move, final String host, final int port){
        new Thread(){
            @Override
            public void run(){
                try{
                    Socket target = new Socket(host, port);
                    Connection.broadcast(target, move);
                    String opponentsMove = Connection.receive(target);
                }
                catch(final Exception e){

                }
            }
        };
    }


}
