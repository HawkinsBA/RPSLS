package com.example.rpsls;

import android.app.Dialog;
import android.content.Intent;
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
        final Dialog dialog = new Dialog(this);
        final Button gotIt = findViewById(R.id.done);
        dialog.setContentView(R.layout.howtoplaydialog);

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
                dialog.show();
                gotIt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
            }
        });
    }
}
