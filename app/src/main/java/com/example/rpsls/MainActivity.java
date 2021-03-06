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
    private TextView myIPView, inviteText, inviteResText;
    private Button connect, howToPlay, gotIt, accept, decline, resolve;
    private String userIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        initServer();
        initClient();
    }

    public void initUI() {
        myIPView = findViewById(R.id.myIPView);
        targetIP = findViewById(R.id.host);
        connect = findViewById(R.id.start);
        howToPlay = findViewById(R.id.howToPlay);

        try {
            Log.d(TAG, "initComponents: Setting myIPView to my IP.");
            userIP = Utilities.getLocalIpAddress();
            myIPView.setText(userIP);
        } catch (SocketException e) {
            Log.e(TAG, "initComponents: Threw exception when finding IP address.");
            myIPView.setText("Could not determine your IP address.");
        }

        View howToPlayView = getLayoutInflater().inflate(R.layout.dialog_tutorial, null);
        AlertDialog.Builder howToPlayBuilder = new AlertDialog.Builder(MainActivity.this);
        howToPlayBuilder.setView(howToPlayView);
        final AlertDialog howToPlayDialog = howToPlayBuilder.create();
        howToPlayDialog.setContentView(R.layout.dialog_tutorial);
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

    private void initServer() {
        new Thread(new Runnable() {
            @Override
            public void run(){
                try {
                    Log.d(TAG, "initServer: Setting up server.");
                    Server.get().addListener(new ServerListener() {
                        @Override
                        public void notifyConnection(String incoming) {
                            Log.d(TAG, "notifyConnection: Connection received.");
                            processIncomingConnection(incoming);
                        }
                    });
                    Server.get().listen();
                } catch (IOException e) {
                    Log.e(TAG, "initServer: Could not start server.");
                }
            }
        }).start();
    }

    private void processIncomingConnection(final String incoming) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(incoming.equals("yes")) {
                    Log.d(TAG, "processIncomingConnection: Processing positive response.");
                    initResponseDialog(true).show();
                } else if (incoming.equals("no")){
                    Log.d(TAG, "processIncomingConnection: Processing negative response.");
                    initResponseDialog(false).show();
                } else {
                    Log.d(TAG, "processIncomingConnection: Processing incoming invite.");
                    initIncomingInviteDialog(incoming).show();
                }
            }
        });
    }

    private AlertDialog initResponseDialog(boolean inviteAccepted) {
        Log.d(TAG, "initInviteResolutionDialog: Initializing invite response dialog.");
        View resolutionView = getLayoutInflater().inflate(R.layout.dialog_response, null);
        AlertDialog.Builder resolutionBuilder = new AlertDialog.Builder(MainActivity.this);
        resolutionBuilder.setView(resolutionView);
        final AlertDialog inviteResolutionDialog = resolutionBuilder.create();
        inviteResolutionDialog.setContentView(R.layout.dialog_response);

        final String opponentIP = targetIP.getText().toString();
        resolve = resolutionView.findViewById(R.id.resolve);
        inviteResText = resolutionView.findViewById(R.id.inviteResText);

        if (inviteAccepted) {
            Log.d(TAG, "initInviteResolutionDialog: Invite accepted.");
            inviteResText.setText(opponentIP + " has accepted your invite.");
            resolve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent toGameIntent = new Intent(MainActivity.this, GameActivity.class);
                    toGameIntent.putExtra("OPPONENT_IP", opponentIP);
                    startActivity(toGameIntent);
                }
            });
        } else {
            Log.d(TAG, "initInviteResolutionDialog: Invited declined.");
            inviteResText.setText(opponentIP + " has refused your invite.");
            resolve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    inviteResolutionDialog.dismiss();
                }
            });
        }

        return inviteResolutionDialog;
    }

    private AlertDialog initIncomingInviteDialog(final String incomingIP) {
        Log.d(TAG, "initInviteDialog: Initializing invite dialog.");
        View inviteView = getLayoutInflater().inflate(R.layout.dialog_invite, null);
        AlertDialog.Builder inviteBuilder = new AlertDialog.Builder(MainActivity.this);
        inviteBuilder.setView(inviteView);
        final AlertDialog incomingInviteDialog = inviteBuilder.create();
        incomingInviteDialog.setContentView(R.layout.dialog_invite);

        accept = inviteView.findViewById(R.id.accept);
        decline = inviteView.findViewById(R.id.decline);
        inviteText = inviteView.findViewById(R.id.inviteResText);
        inviteText.setText(incomingIP + " would like to start a game with you.");

        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Incoming invite denied.");
                sendResponse(incomingIP, "no");
                incomingInviteDialog.dismiss();
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Incoming invite accepted.");
                sendResponse(incomingIP, "yes");
                Intent toGameIntent = new Intent(MainActivity.this, GameActivity.class);
                toGameIntent.putExtra("OPPONENT_IP", incomingIP);
                startActivity(toGameIntent);
            }
        });

        return incomingInviteDialog;
    }

    private void initClient() {
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendInvite(targetIP.getText().toString());
            }
        });
    }

    private void sendResponse(final String targetIP, final String response) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Socket hostSocket = new Socket(targetIP, Server.APP_PORT);
                    Connection.broadcast(hostSocket, response);
                    hostSocket.close();
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

    private void sendInvite(final String targetIP) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Socket hostSocket = new Socket(targetIP, Server.APP_PORT);
                    Connection.broadcast(hostSocket, myIPView.getText().toString());
                    hostSocket.close();
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
