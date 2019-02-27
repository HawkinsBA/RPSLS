package com.example.rpsls;

import java.io.IOException;

public class PlayGame {

    private void setUpServer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
            }

        });
        try{
            Server s = new Server();
            //s.addListener

        }
        catch (IOException e){

        }
    }

}
