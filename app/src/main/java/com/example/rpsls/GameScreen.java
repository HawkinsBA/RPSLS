package com.example.rpsls;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        final TextView clientIP = findViewById(R.id.opponentIP);

        Button rock = findViewById(R.id.rock);
        Button paper = findViewById(R.id.paper);
        Button scissors = findViewById(R.id.scissors);
        Button lizard = findViewById(R.id.lizard);
        Button spock = findViewById(R.id.spock);
        Button quit = findViewById(R.id.quitButton);

        rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayGame.sendMove("rock", clientIP.getText().toString(), Server.APP_PORT);
            }
        });

        paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayGame.sendMove("paper", clientIP.getText().toString(), Server.APP_PORT);
            }
        });

        scissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayGame.sendMove("scissors", clientIP.getText().toString(), Server.APP_PORT);
            }
        });

        lizard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayGame.sendMove("lizard", clientIP.getText().toString(), Server.APP_PORT);
            }
        });

        spock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayGame.sendMove("spock", clientIP.getText().toString(), Server.APP_PORT);
            }
        });

        final AlertDialog.Builder rageQuit = new AlertDialog.Builder(GameScreen.this);
        rageQuit.setMessage("Are you sure you would like to quit?");
        rageQuit.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent backToMainScreen = new Intent(GameScreen.this, MainActivity.class);
                startActivity(backToMainScreen);
            }
        });
        rageQuit.setPositiveButton("Resume", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        final AlertDialog quitPressed = rageQuit.create();


        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitPressed.show();
            }
        });
    }

}
