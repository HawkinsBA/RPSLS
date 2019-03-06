package com.example.rpsls;

import android.widget.Button;

public class rockPaperScissorsCalculations {

    /*
    when player chooses a button, it sets that chosen
    item (rock, paper, etc.) to true, algorithm compares
    choices to see who wins or if it is a tie.
    */

    public static String playerChoices (String userChoice, String opponentChoice){
        String winOrLose = "Play!"; //0 = tie, 1 = user wins/opponent loses, 2 = user loses/opponent wins
        if(userChoice == "rock"){
            if (opponentChoice == "scissors" || opponentChoice == "lizard"){
                winOrLose = "Rock beats" + " " + opponentChoice + "!";
            }
            else if (opponentChoice == "spock" || opponentChoice == "paper"){
                winOrLose = "Rock lost to" + " " + opponentChoice + "!";
            }
            else if (opponentChoice == "rock"){
                winOrLose = "It's a tie!";
            }
        }
        else if(userChoice == "paper"){
            if (opponentChoice == "rock" || opponentChoice == "spock"){
                winOrLose = "Paper beats" + " " + opponentChoice + "!";
            }
            else if (opponentChoice == "scissors" || opponentChoice == "lizard"){
                winOrLose = "Paper lost to" + " " + opponentChoice + "!";
            }
            else if (opponentChoice == "paper"){
                winOrLose = "It's a tie!";
            }
        }
        else if(userChoice == "scissors"){
            if (opponentChoice == "paper" || opponentChoice == "lizard"){
                winOrLose = "Scissors beats" + " " + opponentChoice + "!";
            }
            else if (opponentChoice == "spock" || opponentChoice == "rock"){
                winOrLose = "Scissors lost to" + " " + opponentChoice + "!";
            }
            else if (opponentChoice == "scissors"){
                winOrLose = "It's a tie!";
            }
        }
        //public void LizardMove()
        else if(userChoice == "lizard"){
            if (opponentChoice == "paper" || opponentChoice == "spock"){
                winOrLose = "Lizard beats" + " " + opponentChoice + "!";
            }
            else if (opponentChoice == "scissors" || opponentChoice == "rock"){
                winOrLose = "Lizard lost to" + " " + opponentChoice + "!";
            }
            else if (opponentChoice == "lizard"){
                winOrLose = "It's a tie!";
            }
        }
        else if(userChoice == "spock"){
            if (opponentChoice == "rock" || opponentChoice == "scissors"){
                winOrLose = "Spock beats" + " " + opponentChoice + "!";
            }
            else if (opponentChoice == "paper" || opponentChoice == "lizard"){
                winOrLose = "Spock lost to" + " " + opponentChoice + "!";
            }
            else if (opponentChoice == "spock"){
                winOrLose = "It's a tie!";
            }
        }
        return winOrLose;
    }


}
