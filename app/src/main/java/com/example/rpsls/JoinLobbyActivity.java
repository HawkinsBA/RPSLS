package com.example.rpsls;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.*;


public class JoinLobbyActivity extends AppCompatActivity {
    private ArrayList<String> Hosts;
    private String SystemMsgs = "JoinLobbyActivity";
    private int port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_lobby);
        DisplayingAvailableGames();
        // ListeningServer();
        //LookingForIP();

    }

    public void DisplayingAvailableGames() {
        RecyclerView HostViewer = findViewById(R.id.HostViewer);
        RecyclerViewAdapter adapt = new RecyclerViewAdapter(this, Hosts);
        HostViewer.setAdapter(adapt);
        HostViewer.setLayoutManager(new LinearLayoutManager(this));

    }

    //Checks if host is looking for User
    private boolean LookingForJoin() {
        return true;
    }
}
    //Looks for devices that are broadcasting their IP address

    //Code Adapted from StackedOverflow

  /*  private int LookingForIP(){
            int ipadd = 0;
            try {
                Socket socket = new Socket();
                SocketAddress sockAddress = new InetSocketAddress(ip);
                ((InetSocketAddress) sockAddress).getAddress();
                socket.connect(sockAddress, TIMEOUT);
                //OPEN
                socket.close();
            } catch (UnknownHostException e) {
                //Wrong Address
            } catch (SocketTimeoutException e) {
                //TIMEOUT
            } catch (IOException e) {
                //CLOSED
            }

         return  0;

        }*/

   /*     private void ListeningServer(){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Server.get().addListener(new ServerListeners() {
                            @Override
                            public void notifyLookingToJoin(String host, int port) {

                            }
                        });
                    } catch (IOException e){

                    }
                }
            }).start();
        }

    */


