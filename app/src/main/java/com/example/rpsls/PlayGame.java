package com.example.rpsls;

import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.net.Socket;

public class PlayGame {

    //set up methods to receive and send moves and make sure to
    //save opponents move in a variable before revealing the string to
    //the user that the client is sending the move to or in the TextView

    public static void sendMove(final String move, final String host, final int port){
        new Thread(new Runnable() {
            @Override
            public void run(){
                try{
                    Socket target = new Socket(host, port);
                    Connection.broadcast(target, move);
                }
                catch(final Exception e){

                }
            }
        });
    }



}
