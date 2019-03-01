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

import java.net.SocketException;

public class MainActivity extends AppCompatActivity {
    final static String TAG = "MainActivity";

    private EditText host, port;
    private TextView myIPView;
    private Button connect, howToPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();

        Button howToPlay = findViewById(R.id.howToPlay);

        View mView = getLayoutInflater().inflate(R.layout.how_to_play_dialog, null);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.setContentView(R.layout.how_to_play_dialog);

        final Button gotIt = mView.findViewById(R.id.done);

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

    public void initComponents() {
        myIPView = findViewById(R.id.myIPView);
        host = findViewById(R.id.host);
        port = findViewById(R.id.port);
        connect = findViewById(R.id.start);

        try {
            myIPView.setText(Utilities.getLocalIpAddress());
        } catch (SocketException e) {
            Log.e("MainActivity", "Threw exception when finding ip address");
        }
    }
}
