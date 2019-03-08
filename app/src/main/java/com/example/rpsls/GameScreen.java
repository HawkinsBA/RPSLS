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

    private String userMove;
    private String opponentMove;
    private TextView result;
    private PlayGame game;
    public static int decider;
    private TextView opponentScore;
    private TextView userScore;
    private String [] moveCheck = new String [2];
    private boolean userSent = false;
    private boolean opponentSent = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        setUpServer();

        final TextView clientIP = findViewById(R.id.opponentIP);
        result = findViewById(R.id.roundResult);
        Button rock = findViewById(R.id.rock);
        Button paper = findViewById(R.id.paper);
        Button scissors = findViewById(R.id.scissors);
        Button lizard = findViewById(R.id.lizard);
        Button spock = findViewById(R.id.spock);
        Button quit = findViewById(R.id.quitButton);
        game = new PlayGame(this);


        Intent i = getIntent();
        String opponentIP = i.getExtras().get("opponentIP").toString();
        clientIP.setText(opponentIP);
        rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.sendMove("rock", clientIP.getText().toString(), Server.APP_PORT);
                userMove = "rock";
                userSent = true;
                if(!opponentSent){

                }else if(opponentSent){
                    game.calculateWinner(userMove, opponentMove,result);
                    if(decider == 1){
                        game.changeScore(userScore);
                    }
                    else if(decider == 2){
                        game.changeScore(opponentScore);
                    }
                }
            }
        });

        paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.sendMove("paper", clientIP.getText().toString(), Server.APP_PORT);
                userMove = "paper";
                moveCheck[0] = "paper";
                boolean bothMoves = MoveSentCheck.checkIfMovesExist(moveCheck);
                if(!bothMoves){

                }else if(bothMoves){
                    game.calculateWinner(userMove, moveCheck[1],result);
                    if(decider == 1){
                        game.changeScore(userScore);
                    }
                    else if(decider == 2){
                        game.changeScore(opponentScore);
                    }
                }
            }
        });

        scissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.sendMove("scissors", clientIP.getText().toString(), Server.APP_PORT);
                userMove = "scissors";
                moveCheck[0] = "scissors";
                boolean bothMoves = MoveSentCheck.checkIfMovesExist(moveCheck);
                if(!bothMoves){

                }else if(bothMoves){
                    game.calculateWinner(userMove, moveCheck[1],result);
                    if(decider == 1){
                        game.changeScore(userScore);
                    }
                    else if(decider == 2){
                        game.changeScore(opponentScore);
                    }
                }            }
        });

        lizard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.sendMove("lizard", clientIP.getText().toString(), Server.APP_PORT);
                userMove = "lizard";
                moveCheck[0] = "lizard";
                boolean bothMoves = MoveSentCheck.checkIfMovesExist(moveCheck);
                if(!bothMoves){

                }else if(bothMoves){
                    game.calculateWinner(userMove, moveCheck[1],result);
                    if(decider == 1){
                        game.changeScore(userScore);
                    }
                    else if(decider == 2){
                        game.changeScore(opponentScore);
                    }
                }            }
        });

        spock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.sendMove("spock", clientIP.getText().toString(), Server.APP_PORT);
                userMove = "spock";
                moveCheck[0] = "spock";
                boolean bothMoves = MoveSentCheck.checkIfMovesExist(moveCheck);
                if(!bothMoves){

                }else if(bothMoves){
                    game.calculateWinner(userMove, moveCheck[1],result);
                    if(decider == 1){
                        game.changeScore(userScore);
                    }
                    else if(decider == 2){
                        game.changeScore(opponentScore);
                    }
                }            }
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
 ;



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
    public static String playerChoices (String userChoice, String opponentChoice, TextView user){
        String winOrLose = "Play!";//0 = tie, 1 = user wins/opponent loses, 2 = user loses/opponent wins

        if(userChoice == "rock"){
            if (opponentChoice == "scissors" || opponentChoice == "lizard"){
                winOrLose = "Rock beats" + " " + opponentChoice + "!";
                decider = 1;
            }
            else if (opponentChoice == "spock" || opponentChoice == "paper"){
                winOrLose = "Rock lost to" + " " + opponentChoice + "!";
                decider = 2;
            }
            else if (opponentChoice == "rock"){
                winOrLose = "It's a tie!";
                decider = 0;
            }
        }

        else if(userChoice == "paper"){
            if (opponentChoice == "rock" || opponentChoice == "spock"){
                winOrLose = "Paper beats" + " " + opponentChoice + "!";
                decider = 1;
            }
            else if (opponentChoice == "scissors" || opponentChoice == "lizard"){
                winOrLose = "Paper lost to" + " " + opponentChoice + "!";
                decider = 2;
            }
            else if (opponentChoice == "paper"){
                winOrLose = "It's a tie!";
                decider = 0;
            }
        }
        else if(userChoice == "scissors"){
            if (opponentChoice == "paper" || opponentChoice == "lizard"){
                winOrLose = "Scissors beats" + " " + opponentChoice + "!";
                decider = 1;
            }
            else if (opponentChoice == "spock" || opponentChoice == "rock"){
                winOrLose = "Scissors lost to" + " " + opponentChoice + "!";
                decider = 2;
            }
            else if (opponentChoice == "scissors"){
                winOrLose = "It's a tie!";
                decider = 0;
            }
        }
        else if(userChoice == "lizard"){
            if (opponentChoice == "paper" || opponentChoice == "spock"){
                winOrLose = "Lizard beats" + " " + opponentChoice + "!";
                decider = 1;
            }
            else if (opponentChoice == "scissors" || opponentChoice == "rock"){
                winOrLose = "Lizard lost to" + " " + opponentChoice + "!";
                decider = 2;
            }
            else if (opponentChoice == "lizard"){
                winOrLose = "It's a tie!";
                decider = 0;
            }
        }
        else if(userChoice == "spock"){
            if (opponentChoice == "rock" || opponentChoice == "scissors"){
                winOrLose = "Spock beats" + " " + opponentChoice + "!";
                decider = 1;
            }
            else if (opponentChoice == "paper" || opponentChoice == "lizard"){
                winOrLose = "Spock lost to" + " " + opponentChoice + "!";
                decider = 2;
            }
            else if (opponentChoice == "spock"){
                winOrLose = "It's a tie!";
                decider = 0;
            }
        }
        return winOrLose;
    }


    private void setUpServer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Server.get().addListener(new ServerListener() {
                        @Override
                        public void notifyConnection(String target) {
                            opponentMove = target;
                            opponentSent = true;
                            if(!userSent){

                            }else if(userSent){
                                game.calculateWinner(userMove,opponentMove,result);
                            }
                            Log.d("GameScreen: ","notifyConnection: " + opponentMove);
                            Log.d("GameScreen: ","notifyConnection: " + userMove);
                            if(decider == 1){
                                game.changeScore(userScore);
                            }
                            else if(decider == 2){
                                game.changeScore(opponentScore);
                            }
                        }
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
