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
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    final static String TAG = "GameActivity";
    private String opponentIP;
    private int decider, userScore, opponentScore;
    ArrayList<String> moveCheck = new ArrayList<>();
    private TextView result, opponentScoreView, userScoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initUI();
        initGameComponents();
        setUpServer();
    }

    private void initUI() {
        result = findViewById(R.id.roundResult);
        opponentScoreView = findViewById(R.id.opponentScore);
        userScoreView = findViewById(R.id.userScore);
        Button rock = findViewById(R.id.rock);
        Button paper = findViewById(R.id.paper);
        Button scissors = findViewById(R.id.scissors);
        Button lizard = findViewById(R.id.lizard);
        Button spock = findViewById(R.id.spock);
        Button quit = findViewById(R.id.quit);


        Intent i = getIntent();
        opponentIP = i.getExtras().get("opponentIP").toString();

        rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMove("rock", opponentIP);
                moveCheck.set(0, "rock");
                updateGame();

            }
        });

        paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMove("paper", opponentIP);
                moveCheck.set(0, "paper");
                updateGame();
            }
        });

        scissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMove("scissors", opponentIP);
                moveCheck.set(0, "scissors");
                updateGame();
            }
        });

        lizard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMove("lizard", opponentIP);
                moveCheck.set(0, "lizard");
                updateGame();
            }
        });

        spock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMove("spock", opponentIP);
                moveCheck.set(0, "spock");
                updateGame();
            }
        });

        final AlertDialog.Builder quitDialog = new AlertDialog.Builder(GameActivity.this);
        quitDialog.setMessage("Are you sure you would like to quit?");
        quitDialog.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent backToMainScreen = new Intent(GameActivity.this, MainActivity.class);
                startActivity(backToMainScreen);
            }
        });

        quitDialog.setPositiveButton("Resume", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        final AlertDialog quitPressed = quitDialog.create();

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitPressed.show();
            }
        });
    }

    private void initGameComponents() {
        moveCheck.set(0, "");
        userScore = 0;
        moveCheck.set(1, "");
        opponentScore = 0;
        userScoreView.setText("Score: " + userScore);
        opponentScoreView.setText("Score: " + opponentScore);
    }

    private boolean haveBothMoves() {
        return (!moveCheck.get(0).equals("") && !moveCheck.get(1).equals(""));
    }

    private void updateGame() {
        if (opponentScore == 5) {
            showLossDialog();
        } else if (userScore == 5) {
            showWinDialog();
        }
        if (haveBothMoves()) {
            updateScore();
            showWinner();
            resetMoves();
        }
    }

    private void showWinDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Utilities.notifyMessage(GameActivity.this, "You win!");
            }
        });
    }

    private void showLossDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Utilities.notifyMessage(GameActivity.this, "You lose. Better luck next time!");
            }
        });
    }

    private void showWinner() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (decider == -1) {
                    result.setText("It's a tie.");
                } else if (decider == 0) {
                    result.setText("You win this round!");
                    userScoreView.setText("Score: " + userScore);
                } else {
                    result.setText("You lost this round!");
                    opponentScoreView.setText("Score: " + opponentScore);
                }
            }
        });
    }

    private void updateScore() {
        String userMove = moveCheck.get(0);
        String opponentMove = moveCheck.get(1);
        decider = Game.resolveGame(userMove, opponentMove);
        if (decider == 0){
            userScore++;
        } else if (decider == 1){
            opponentScore++;
        }
    }

    public void resetMoves() {
        moveCheck.set(0, "");
        moveCheck.set(1, "");
        decider = -1;
    }

    private void setUpServer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Server.get().addListener(new ServerListener() {
                        @Override
                        public void notifyConnection(String move) {
                            Log.d(TAG, "notifyConnection: Opponent move received.");
                            moveCheck.set(1, move);
                            updateGame();
                        }
                    });
                    Server.get().listen();
                }
                catch (IOException e){
                    Log.e(GameActivity.class.getName(), "Could not connect to server");
                }
            }
        }).start();

    }

    public void sendMove(final String move, final String opponentIP) {
        Log.d(TAG, "About to send [" + move + "] to " + opponentIP);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(TAG, "sendMove: Opening socket.");
                    Socket target = new Socket(opponentIP, Server.APP_PORT);
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
