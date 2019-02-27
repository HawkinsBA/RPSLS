package com.example.rpsls;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class CreateLobbyActivity extends AppCompatActivity {
    private static final String TAG = "CreateLobbyActivity";
    private ArrayList<String> mUsers;
    private String hostIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        Log.d(TAG, "onCreate: Started.");
        initComponents();
        initServer();
        initClient();
    }

    //Broadcast our IP to users looking to join a game.
    private void initClient() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (String user : mUsers) {
                    try{
                        Socket socket = new Socket(user, Server.APP_PORT);
                        Connection.broadcast(socket, hostIP);
                    } catch (IOException e) {
                        Log.e(TAG, "initClient: Could not broadcast to target user's device.");
                    }
                }
            }
        }).start();
    }

    public void addUser(final String address) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "addUser: Adding user.");
                mUsers.add(address);
            }
        });
    }

    private void initComponents() {
        Log.d(TAG, "initComponents: Initializing UI components.");
        mUsers = new ArrayList<>();
        try {
            hostIP = Utilities.getLocalIpAddress();
        } catch (SocketException e) {
            Log.e(TAG, "initComponents: Threw exception when finding IP address.");
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initRecyclerView();
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: Updating RecyclerView.");
        RecyclerView recyclerView = findViewById(R.id.usersRecycler);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mUsers);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    //The server in this activity should listen for devices that want to join a game.
    private void initServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Server.get().addListener(new ServerListeners() {
                        @Override
                        public void notifyLookingToJoin(String host) {
                            Log.d(TAG, "notifyLookingToJoin: Found user looking to join game.");
                            addUser(host);
                        }

                        @Override
                        public void notifyLookingToHost(String host, int port) {
                            Log.d(TAG, "notifyLookingToHost: Found user looking to host game.");
                        }
                    });
                    Server.get().listenForJoins();
                } catch (IOException e) {
                    Log.e(TAG, "initServer: Could not start server.");
                }
            }
        }).start();
    }
}
