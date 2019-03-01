package com.example.rpsls;

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

    private EditText host;
    private TextView myIPView;
    private Button connect, howToPlay, gotIt;
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
        host = findViewById(R.id.host);
        connect = findViewById(R.id.start);
        howToPlay = findViewById(R.id.howToPlay);

        //TODO: Ask Dr. Ferrer why this isn't working on my device.
        try {
            Log.d(TAG, "initComponents: Setting myIPView to my IP.");
            userIP = Utilities.getLocalIpAddress();
        } catch (SocketException e) {
            Log.e(TAG, "initComponents: Threw exception when finding IP address.");
        }

        myIPView.setText("Your IP: " + userIP);

        View mView = getLayoutInflater().inflate(R.layout.how_to_play_dialog, null);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.setContentView(R.layout.how_to_play_dialog);
        gotIt = mView.findViewById(R.id.done);

        howToPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: howToPlay pressed.");
                dialog.show();
            }
        });

        gotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: gotIt pressed.");
                dialog.dismiss();
            }
        });
    }

    //TODO: Ask Dr. Ferrer why a server will not initialize on my device.
    private void initServer() {
        new Thread(new Runnable() {
            @Override
            public void run(){
                try {
                    Server.get().addListener(new ServerListener() {
                        @Override
                        public void notifyConnection(Socket target) {
                            //TODO: Process an incoming invite to a game.
                            //opponentIP = Connection.receive(target);
                        }
                    });
                    Server.get().listen();
                } catch (IOException e) {
                    Log.e(MainActivity.class.getName(), "Could not start server.");
                }
            }
        }).start();
    }

    private void initClient() {
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendInvite(host.getText().toString(), Server.APP_PORT);
            }
        });
    }

    private void sendInvite(final String hostIP, final int appPort) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Socket hostSocket = new Socket(hostIP, appPort);
                    Connection.broadcast(hostSocket, myIPView.getText().toString());
                    //opponentIP = Connection.receive(hostSocket); Don't need to get opp's IP, but probably want to store it
                    //when processing an invite from them.
                    hostSocket.close(); //We actually probably don't want to close the socket here but I'll leave this for now.
                    //We probably want to wait for a notifier from the server indicating accept/decline.
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
