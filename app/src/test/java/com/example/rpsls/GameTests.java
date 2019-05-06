package com.example.rpsls;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class GameTests {
    @Test
    public void testTieMoveCombos() {
        assertEquals(-1, Game.resolveGame("rock", "rock"));
        assertEquals(-1, Game.resolveGame("paper", "paper"));
        assertEquals(-1, Game.resolveGame("scissors", "scissors"));
        assertEquals(-1, Game.resolveGame("lizard", "lizard"));
        assertEquals(-1, Game.resolveGame("spock", "spock"));
    }

    @Test
    public void testUserWinMoveCombos() {
        assertEquals(0, Game.resolveGame("rock", "scissors"));
        assertEquals(0, Game.resolveGame("rock", "lizard"));
        assertEquals(0, Game.resolveGame("paper", "rock"));
        assertEquals(0, Game.resolveGame("paper", "spock"));
        assertEquals(0, Game.resolveGame("scissors", "paper"));
        assertEquals(0, Game.resolveGame("scissors", "lizard"));
        assertEquals(0, Game.resolveGame("lizard", "paper"));
        assertEquals(0, Game.resolveGame("lizard", "spock"));
        assertEquals(0, Game.resolveGame("spock", "scissors"));
        assertEquals(0, Game.resolveGame("spock", "rock"));
    }

    @Test
    public void testUserLossMoveCombos() {
        assertEquals(1, Game.resolveGame("rock", "papers"));
        assertEquals(1, Game.resolveGame("rock", "spock"));
        assertEquals(1, Game.resolveGame("paper", "lizard"));
        assertEquals(1, Game.resolveGame("paper", "scissors"));
        assertEquals(1, Game.resolveGame("scissors", "rock"));
        assertEquals(1, Game.resolveGame("scissors", "spock"));
        assertEquals(1, Game.resolveGame("lizard", "rock"));
        assertEquals(1, Game.resolveGame("lizard", "scissors"));
        assertEquals(1, Game.resolveGame("spock", "paper"));
        assertEquals(1, Game.resolveGame("spock", "lizard"));
    }
}