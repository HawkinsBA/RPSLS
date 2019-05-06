package com.example.rpsls;

import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class GameScreenTests {

    GameScreen g = new GameScreen();
    String [] moves;
    TextView results;
    String userMove, opponentMove;

    @Before
    public void setUp(){
        moves = g.moveCheck;
        moves[0] = "spock";
        moves[1] = "rock";
        results = g.result;
        userMove = g.userMove;
        opponentMove = g.opponentMove;
    }

    @Test
    public void playerChoicesTest(){
        System.out.println(g.playerChoices("rock", "lizard"));
        assertEquals("Rock beats lizard!", g.playerChoices("rock", "lizard"));
    }

    @Test
    public void updateGameTest(){
        System.out.println(g.playerChoices(moves[0], moves[1]));
        g.updateGame();
        assertEquals("Spock beats rock!", results.getText().toString());
    }

    @Test
    public void hasBothMovesTest(){
        assertTrue(g.haveBothMoves());
        moves[0] = null;
        assertFalse(g.haveBothMoves());
    }

}
