package com.example.rpsls;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class MainActivity extends AppCompatActivity {
    final static String TAG = "MainActivity";

    private EditText targetIP;
    private TextView myIPView, inviteText;
    private Button connect, howToPlay, gotIt, accept, decline;
    private String userIP, opponentIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        initServer();
        initClient();
    }

    public void initComponents() {
        myIPView = findViewById(R.id.myIPView);
        targetIP = findViewById(R.id.host);
        connect = findViewById(R.id.start);
        howToPlay = findViewById(R.id.howToPlay);

        //TODO: Ask Dr. Ferrer why this isn't working on my device.
        try {
            Log.d(TAG, "initComponents: Setting myIPView to my IP.");
            userIP = Utilities.getLocalIpAddress();
            myIPView.setText("Your IP: " + userIP);
        } catch (SocketException e) {
            Log.e(TAG, "initComponents: Threw exception when finding IP address.");
            myIPView.setText("Could not determine your IP address.");
        }

        View howToPlayView = getLayoutInflater().inflate(R.layout.how_to_play_dialog, null);
        AlertDialog.Builder howToPlayBuilder = new AlertDialog.Builder(MainActivity.this);
        howToPlayBuilder.setView(howToPlayView);
        final AlertDialog howToPlayDialog = howToPlayBuilder.create();
        howToPlayDialog.setContentView(R.layout.how_to_play_dialog);
        gotIt = howToPlayView.findViewById(R.id.done);


        howToPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: howToPlay pressed.");
                howToPlayDialog.show();
            }
        });

        gotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: gotIt pressed.");
                howToPlayDialog.dismiss();
            }
        });
    }

    //TODO: Ask Dr. Ferrer why a server will not initialize on my device.
    private void initServer() {
        new Thread(new Runnable() {
            @Override
            public void run(){
                try {
                    Log.d(TAG, "initServer: Setting up server.");
                    Server.get().addListener(new ServerListener() {
                        @Override
                        public void notifyConnection(String incomingIP) {
                            Log.d(TAG, "notifyConnection: Connection received.");
                            processInvite(incomingIP);
                        }
                    });
                    Server.get().listen();
                } catch (IOException e) {
                    Log.e(TAG, "initServer: Could not start server.");
                }
            }
        }).start();
    }

    private void processInvite(final String incomingIP) {
        Log.d(TAG, "processInvite: Processing incoming invite.");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initInviteDialog(incomingIP).show();
            }
        });
    }

    private AlertDialog initInviteDialog(final String incomingIP) {
        Log.d(TAG, "initInviteDialog: Initializing invite dialog.");
        View inviteView = getLayoutInflater().inflate(R.layout.invite_dialog, null);
        AlertDialog.Builder inviteBuilder = new AlertDialog.Builder(MainActivity.this);
        inviteBuilder.setView(inviteView);
        final AlertDialog inviteDialog = inviteBuilder.create();
        inviteDialog.setContentView(R.layout.invite_dialog);

        accept = inviteView.findViewById(R.id.accept);
        decline = inviteView.findViewById(R.id.decline);
        inviteText = inviteView.findViewById(R.id.inviteText);
        inviteText.setText(incomingIP + " would like to start a game with you.");

        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Incoming invite denied.");
                inviteDialog.dismiss();
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Incoming invite accepted.");
                opponentIP = incomingIP;
                Intent toGameIntent = new Intent(MainActivity.this, GameScreen.class);
                toGameIntent.putExtra("opponentIP", opponentIP);
                startActivity(toGameIntent);
            }
        });

        return inviteDialog;
    }

    private void initClient() {
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendInvite(targetIP.getText().toString());
            }
        });
    }

    //Broadcast our IP to the user with the target IP.
    private void sendInvite(final String targetIP) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Socket hostSocket = new Socket(targetIP, Server.APP_PORT);
                    Connection.broadcast(hostSocket, myIPView.getText().toString());
                    hostSocket.close(); //I'm not sure want to close the socket here but I'll leave this for now.
                } catch (final Exception e) {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Utilities.notifyException(MainActivity.this, e);
                        }
                    });
                }

            }
        }.start();

    }
}
