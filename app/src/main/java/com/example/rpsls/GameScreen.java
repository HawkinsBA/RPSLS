package com.example.rpsls;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.net.Socket;

public class GameScreen extends AppCompatActivity {

    private TextView opponentMove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        setUpServer();

        final TextView clientIP = findViewById(R.id.opponentIP);
        TextView result = findViewById(R.id.roundResult);
        Button rock = findViewById(R.id.rock);
        Button paper = findViewById(R.id.paper);
        Button scissors = findViewById(R.id.scissors);
        Button lizard = findViewById(R.id.lizard);
        Button spock = findViewById(R.id.spock);
        Button quit = findViewById(R.id.quitButton);
        final TextView userMove = findViewById(R.id.userMove);
        opponentMove = findViewById(R.id.opponentMove);
        final PlayGame game = new PlayGame(this);

        Intent i = getIntent();
        String opponentIP = i.getExtras().get("opponentIP").toString();
        clientIP.setText(opponentIP);

        rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.sendMove("rock", clientIP.getText().toString(), Server.APP_PORT);
            }
        });

        paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.sendMove("paper", clientIP.getText().toString(), Server.APP_PORT);
            }
        });

        scissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.sendMove("scissors", clientIP.getText().toString(), Server.APP_PORT);
            }
        });

        lizard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.sendMove("lizard", clientIP.getText().toString(), Server.APP_PORT);
            }
        });

        spock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.sendMove("spock", clientIP.getText().toString(), Server.APP_PORT);
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

    public void setOpponentMoveToTextView(final String move, final TextView userMove){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                userMove.setText(move);
            }
        });
    }

    private void setUpServer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Server.get().addListener(new ServerListener() {
                        @Override
                        public void notifyConnection(String target) {
                            try{
                                Socket hostSocket = new Socket(target, Server.APP_PORT);
                                String clientMove = Connection.receive(hostSocket);
                                setOpponentMoveToTextView(clientMove, opponentMove);

                            }
                            catch (IOException e){
                                Log.e(GameScreen.class.getName(), "Opponents move could not be received");
                            }
                        }
                        @Override
                        public void notifyInviteResolution(boolean accept) { }
                    });
                    Server.get().listen();
                }
                catch (IOException e){
                    Log.e(GameScreen.class.getName(), "Could not connect to server");
                }
            }
        }).start();

    }

}
