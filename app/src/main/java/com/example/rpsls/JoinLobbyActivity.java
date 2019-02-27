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
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.*;


public class JoinLobbyActivity extends AppCompatActivity {
    private ArrayList<String> Hosts;
    private String SystemMsgs = "JoinLobbyActivity";
    private int TIMEOUT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_lobby);
//        DisplayingavailableGames();

    }

//    public void DisplayingavailableGames() {
//        RecyclerView Hostviewer = findViewById(R.id.HostViewer);
//        RecyclerViewAdapter adapt = new RecyclerViewAdapter(this, Hosts);
//        Hostviewer.setAdapter(adapt);
//        Hostviewer.setLayoutManager(new LinearLayoutManager(this));
//            try {
//                Socket socket = new Socket();
//                SocketAddress address = new InetSocketAddress(ip, port);
//                socket.connect(address, TIMEOUT);
//                //OPEN
//                socket.close();
//            } catch (UnknownHostException e) {
//                //WRONG ADDRESS
//            } catch (SocketTimeoutException e) {
//                //TIMEOUT
//            } catch (IOException e) {
//                //CLOSED
//            }
//
//    }
        
    //Checks if host is looking for User
    private void LookingForJoin(){ }

    public String DisplayingavailableGames(String host,int port) {
        List<String> ListofHostNames = new ArrayList<>();

        if (host.length() != 0) {
            ListofHostNames.add(host);
            ListofHostNames.add(String.valueOf(port));
            return ListofHostNames.toString();
        } else {
            return "No hosts found";
        }
    }
}
