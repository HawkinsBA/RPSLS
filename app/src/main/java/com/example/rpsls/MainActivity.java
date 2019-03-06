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

//TODO: Test invite resolution host-side.

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

        initComponents();
        initServer();
        initClient();
    }

    public void initComponents() {
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
                            processIncomingInvite(incomingIP);
                        }

                        @Override
                        public void notifyInviteResolution(boolean inviteAccepted) {
//                            processOutgoingInvite(inviteAccepted);
                        }
                    });
                    Server.get().listen();
                } catch (IOException e) {
                    Log.e(TAG, "initServer: Could not start server.");
                }
            }
        }).start();
    }

    private void processOutgoingInvite(final boolean inviteAccepted) {
        Log.d(TAG, "processOutgoingInvite: Resolving outgoing invite.");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initInviteResolutionDialog(inviteAccepted).show();
            }
        });
    }

    private AlertDialog initInviteResolutionDialog(boolean inviteAccepted) {
        Log.d(TAG, "initInviteResolutionDialog: Initializing invite resolution dialog.");
        View resolutionView = getLayoutInflater().inflate(R.layout.invite_resolution_dialog, null);
        AlertDialog.Builder resolutionBuilder = new AlertDialog.Builder(MainActivity.this);
        resolutionBuilder.setView(resolutionView);
        final AlertDialog inviteResolutionDialog = resolutionBuilder.create();
        inviteResolutionDialog.setContentView(R.layout.invite_resolution_dialog);

        final String opponentIP = targetIP.getText().toString();
        resolve = resolutionView.findViewById(R.id.resolve);
        inviteResText = resolutionView.findViewById(R.id.inviteResText);

        if (inviteAccepted) {
            Log.d(TAG, "initInviteResolutionDialog: Invite accepted.");
            inviteResText.setText(opponentIP + " has accepted your invite.");
            resolve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent toGameIntent = new Intent(MainActivity.this, GameScreen.class);
                    toGameIntent.putExtra("opponentIP", opponentIP);
                    startActivity(toGameIntent);
                    MainActivity.this.finish();
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

    private void processIncomingInvite(final String incomingIP) {
        Log.d(TAG, "processInvite: Processing incoming invite.");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initIncomingInviteDialog(incomingIP).show();
            }
        });
    }

    private AlertDialog initIncomingInviteDialog(final String incomingIP) {
        Log.d(TAG, "initInviteDialog: Initializing invite dialog.");
        View inviteView = getLayoutInflater().inflate(R.layout.invite_dialog, null);
        AlertDialog.Builder inviteBuilder = new AlertDialog.Builder(MainActivity.this);
        inviteBuilder.setView(inviteView);
        final AlertDialog incomingInviteDialog = inviteBuilder.create();
        incomingInviteDialog.setContentView(R.layout.invite_dialog);

        accept = inviteView.findViewById(R.id.accept);
        decline = inviteView.findViewById(R.id.decline);
        inviteText = inviteView.findViewById(R.id.inviteResText);
        inviteText.setText(incomingIP + " would like to start a game with you.");

        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Incoming invite denied.");
                resolveInvite(incomingIP, false);
                incomingInviteDialog.dismiss();
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Incoming invite accepted.");
                sendInvite(incomingIP);
                Intent toGameIntent = new Intent(MainActivity.this, GameScreen.class);
                toGameIntent.putExtra("opponentIP", incomingIP);
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

    private void resolveInvite(final String incomingIP, final boolean accept) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Log.d(TAG, "resolveInvite: Resolving invite.");
                    Socket socket = new Socket(incomingIP, Server.APP_PORT);
                    Invite.resolve(socket, accept);
                    socket.close();
                } catch (final Exception e) {
                    Log.d(TAG, "resolveInvite: Caught error when resolving invite.");
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
