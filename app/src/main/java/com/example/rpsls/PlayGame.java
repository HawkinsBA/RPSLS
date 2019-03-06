package com.example.rpsls;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.net.Socket;

public class PlayGame extends GameScreen{

    /**
     * Adapted from the class created by gabriel on 2/18/19.
     */
    /* set up methods to receive and send moves and make sure to
    save opponents move in a variable before revealing the string to
    the user that the client is sending the move to or in the TextView */

    private static GameScreen activity;

    public PlayGame(GameScreen activity){
        this.activity = activity;
    }

    public static void sendMove(final String move, final String host, final int port, final TextView moveSent){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Socket target = new Socket(host, port);
                    Connection.broadcast(target, move);
                    target.close();
                    moveSent.setText(move);
                }
                catch(final Exception e){
                    // Utilities.notifyException(GameScreen.class.getName(), );
                    Log.e(GameScreen.class.getName(), "Could not send move");
                }
            }
        });
    }

    public static void calculateWinner(final String userChoice, final String opponentChoice, final TextView showResult, final TextView winner){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String result = rockPaperScissorsCalculations.playerChoices(userChoice, opponentChoice, winner);
                showResult.setText(result);
            }
        });
    }

    public void changeScore(final TextView score){
        score.setText(Integer.parseInt(score.getText().toString()) + 1);
    }

    public void clearMoves(final TextView userMove, final TextView opponentMove){
        userMove.setText(null);
        opponentMove.setText(null);
    }



}
