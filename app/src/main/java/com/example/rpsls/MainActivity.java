package com.example.rpsls;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button createNewGameButton = findViewById(R.id.createNewGameButton);
        Button joinGameButton = findViewById(R.id.joinGameButton);
        Button howToPlay = findViewById(R.id.howToPlay);

        View mView = getLayoutInflater().inflate(R.layout.how_to_play_dialog, null);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.setContentView(R.layout.how_to_play_dialog);

        final Button gotIt = mView.findViewById(R.id.done);

        createNewGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lobby = new Intent(MainActivity.this, CreateLobbyActivity.class);
                startActivity(lobby);
            }
        });

        joinGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent joinLobby = new Intent(MainActivity.this, JoinLobbyActivity.class);
                startActivity(joinLobby);
            }
        });

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
}
