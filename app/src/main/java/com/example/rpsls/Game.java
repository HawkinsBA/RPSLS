package com.example.rpsls;

public class Game {

    public static int resolveGame(String userChoice, String opponentChoice) {
        if (userChoice.equals(opponentChoice)) return -1;
        switch (userChoice) {
            case "rock":
                return (opponentChoice.equals("scissors") || opponentChoice.equals("lizard") ? 0 : 1);
            case "paper":
                return (opponentChoice.equals("rock") || opponentChoice.equals("spock") ? 0 : 1);
            case "scissors":
                return (opponentChoice.equals("paper") || opponentChoice.equals("lizard") ? 0 : 1);
            case "lizard":
                return (opponentChoice.equals("paper") || opponentChoice.equals("spock") ? 0 : 1);
            case "spock":
                return (opponentChoice.equals("rock") || opponentChoice.equals("scissors") ? 0 : 1);
        }
        return -1;
    }
}