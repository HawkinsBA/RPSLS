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
import java.net.SocketException;

public class MainActivity extends AppCompatActivity {
    final static String TAG = "MainActivity";

    private EditText host, port;
    private TextView myIPView;
    private Button connect, howToPlay, gotIt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        initServer();
    }

    public void initComponents() {
        myIPView = findViewById(R.id.myIPView);
        host = findViewById(R.id.host);
        port = findViewById(R.id.port);
        connect = findViewById(R.id.start);
        howToPlay = findViewById(R.id.howToPlay);

        try {
            Log.d(TAG, "initComponents: Setting myIPView to my IP.");
            myIPView.setText(Utilities.getLocalIpAddress());
        } catch (SocketException e) {
            Log.e(TAG, "initComponents: Threw exception when finding IP address.");
        }

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

    private void initServer() {
        new Thread(new Runnable() {
            @Override
            public void run(){
                try {
                    Server s = new Server();
                    s.addListener(new ServerListener() {
                        @Override
                        public void notifyConnection(String host) {
                            //TODO: Start game.
                        }
                    });
                    s.listen();
                } catch (IOException e) {
                    Log.e(MainActivity.class.getName(), "Could not start server.");
                }
            }
        }).start();
    }
}
