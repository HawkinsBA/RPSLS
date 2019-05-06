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

    public GameScreen(){

    }
    final static String TAG = "GameScreen";

    String userMove;
    String opponentMove;
    TextView result;
    public static int decider;
    private TextView opponentScore;
    private TextView userScore;
    public String [] moveCheck = new String [2];
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
        userScore = findViewById(R.id.userScore);
        opponentScore = findViewById(R.id.opponentScore);


        Intent i = getIntent();
        String opponentIP = i.getExtras().get("opponentIP").toString();
        clientIP.setText(opponentIP);

        rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMove("rock", clientIP.getText().toString());
                moveCheck[0] = "rock";
                updateGame();

            }
        });

        paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Sending move to " + clientIP.getText().toString());
                sendMove("paper", clientIP.getText().toString());
                moveCheck[0] = "paper";
                updateGame();
            }
        });

        scissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Sending move to " + clientIP.getText().toString());
                sendMove("scissors", clientIP.getText().toString());
                moveCheck[0] = "scissors";
                updateGame();
            }
        });

        lizard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Sending move to " + clientIP.getText().toString());
                sendMove("lizard", clientIP.getText().toString());
                moveCheck[0] = "lizard";
                updateGame();
            }
        });

        spock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Sending move to " + clientIP.getText().toString());
                sendMove("spock", clientIP.getText().toString());
                moveCheck[0] = "spock";
                updateGame();
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

    public static String playerChoices(String userChoice, String opponentChoice) {
        String winOrLose = "Play!";//0 = tie, 1 = user wins/opponent loses, 2 = user loses/opponent wins

        if(userChoice.equals("rock")){
            if (opponentChoice.equals("scissors") || opponentChoice.equals("lizard")){
                winOrLose = "Rock beats" + " " + opponentChoice + "!";
                decider = 1;
            }
            else if (opponentChoice.equals("spock") || opponentChoice.equals("paper")){
                winOrLose = "Rock lost to" + " " + opponentChoice + "!";
                decider = 2;
            }
            else if (opponentChoice.equals("rock")){
                winOrLose = "It's a tie!";
                decider = 0;
            }
        }

        else if(userChoice.equals("paper")){
            if (opponentChoice.equals("rock") || opponentChoice.equals("spock")){
                winOrLose = "Paper beats" + " " + opponentChoice + "!";
                decider = 1;
            }
            else if (opponentChoice.equals("scissors") || opponentChoice.equals("lizard")){
                winOrLose = "Paper lost to" + " " + opponentChoice + "!";
                decider = 2;
            }
            else if (opponentChoice.equals("paper")){
                winOrLose = "It's a tie!";
                decider = 0;
            }
        }
        else if(userChoice.equals("scissors")){
            if (opponentChoice.equals("paper") || opponentChoice.equals("lizard")){
                winOrLose = "Scissors beats" + " " + opponentChoice + "!";
                decider = 1;
            }
            else if (opponentChoice.equals("spock") || opponentChoice.equals("rock")){
                winOrLose = "Scissors lost to" + " " + opponentChoice + "!";
                decider = 2;
            }
            else if (opponentChoice.equals("scissors")){
                winOrLose = "It's a tie!";
                decider = 0;
            }
        }
        else if(userChoice.equals("lizard")){
            if (opponentChoice.equals("paper") || opponentChoice.equals("spock")){
                winOrLose = "Lizard beats" + " " + opponentChoice + "!";
                decider = 1;
            }
            else if (opponentChoice.equals("scissors") || opponentChoice.equals("rock")){
                winOrLose = "Lizard lost to" + " " + opponentChoice + "!";
                decider = 2;
            }
            else if (opponentChoice.equals("lizard")){
                winOrLose = "It's a tie!";
                decider = 0;
            }
        }
        else if(userChoice.equals("spock")){
            if (opponentChoice.equals("rock") || opponentChoice.equals("scissors")){
                winOrLose = "Spock beats" + " " + opponentChoice + "!";
                decider = 1;
            }
            else if (opponentChoice.equals("paper") || opponentChoice.equals("lizard")){
                winOrLose = "Spock lost to" + " " + opponentChoice + "!";
                decider = 2;
            }
            else if (opponentChoice.equals("spock")){
                winOrLose = "It's a tie!";
                decider = 0;
            }
        }

        return winOrLose;
    }

    public boolean haveBothMoves() {
        return (moveCheck[0] != null && moveCheck[1] != null);
    }

    public void changeScore(final TextView score) {
        score.setText(Integer.parseInt(score.getText().toString()) + 1);
    }

    void updateScore() {
        if(decider == 1){
            changeScore(userScore);
        }

        else if(decider == 2){
            changeScore(opponentScore);
        }
    }

    public void calculateWinner(final String userChoice, final String opponentChoice, final TextView showResult) {
        String result = playerChoices(userChoice, opponentChoice);
        showResult.setText(result);
    }

    void updateGame() {
        String results;
        if (haveBothMoves()) {
            //Log.d(TAG, "updateGame: Resolving moves.");
            userMove = moveCheck[0];
            opponentMove = moveCheck[1];
            results = playerChoices(userMove, opponentMove);
            result.setText(results);
            updateScore();
        }
    }

    void setUpServer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Server.get().addListener(new ServerListener() {
                        @Override
                        public void notifyConnection(String move) {
                            //Log.d(TAG, "notifyConnection: Opponent move received.");
                            moveCheck[1] = move;
                            updateGame();
                        }
                    });
                    Server.get().listen();
                }
                catch (IOException e){
                    //Log.e(GameScreen.class.getName(), "Could not connect to server");
                }
            }
        }).start();

    }

    public void sendMove(final String move, final String host) {
        Log.d(TAG, "About to send [" + move + "] to " + host);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(TAG, "sendMove: Opening socket.");
                    Socket target = new Socket(host, Server.APP_PORT);
                    Connection.broadcast(target, move);
                    target.close();
                    Log.d(TAG, "sendMove: Move sent.");
                } catch (final Exception e) {
                    Log.e(TAG, "sendMove: Could not send move.");
                }
            }
        }).start();
    }
}
