package com.example.rpsls;

import android.widget.TextView;

import org.w3c.dom.Text;

public class rockPaperScissorsCalculations {

    /*
    when player chooses a button, it sets that chosen
    item (rock, paper, etc.) to true, algorithm compares
    choices to see who wins or if it is a tie.
    */

    private static PlayGame game;

    public static String playerChoices (String userChoice, String opponentChoice, TextView user){
        String winOrLose = "Play!";//0 = tie, 1 = user wins/opponent loses, 2 = user loses/opponent wins
        int decider;
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



}
