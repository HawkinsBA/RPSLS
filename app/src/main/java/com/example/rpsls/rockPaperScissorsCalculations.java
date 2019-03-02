package com.example.rpsls;

import android.widget.Button;

public class rockPaperScissorsCalculations {

    /*
    when player chooses a button, it sets that chosen
    item (rock, paper, etc.) to true, algorithm compares
    choices to see who wins or if it is a tie.
    */

    public static int playerChoices (String userChoice, String opponentChoice){
        int winOrLose = 0; //0 = tie, 1 = user wins/opponent loses, 2 = user loses/opponent wins
        if(userChoice == "rock"){
            if (opponentChoice == "scissors" || opponentChoice == "lizard"){
                winOrLose = 1;
            }
            else if (opponentChoice == "spock" || opponentChoice == "paper"){
                winOrLose = 2;
            }
            else if (opponentChoice == "rock"){
                winOrLose = 0;
            }
        }
        else if(userChoice == "paper"){
            if (opponentChoice == "rock" || opponentChoice == "spock"){
                winOrLose = 1;
            }
            else if (opponentChoice == "scissors" || opponentChoice == "lizard"){
                winOrLose = 2;
            }
            else if (opponentChoice == "paper"){
                winOrLose = 0;
            }
        }
        else if(userChoice == "scissors"){
            if (opponentChoice == "paper" || opponentChoice == "lizard"){
                winOrLose = 1;
            }
            else if (opponentChoice == "spock" || opponentChoice == "rock"){
                winOrLose = 2;
            }
            else if (opponentChoice == "scissors"){
                winOrLose = 0;
            }
        }
        else if(userChoice == "lizard"){
            if (opponentChoice == "paper" || opponentChoice == "spock"){
                winOrLose = 1;
            }
            else if (opponentChoice == "scissors" || opponentChoice == "rock"){
                winOrLose = 2;
            }
            else if (opponentChoice == "lizard"){
                winOrLose = 0;
            }
        }
        else if(userChoice == "spock"){
            if (opponentChoice == "rock" || opponentChoice == "scissors"){
                winOrLose = 1;
            }
            else if (opponentChoice == "paper" || opponentChoice == "lizard"){
                winOrLose = 2;
            }
            else if (opponentChoice == "spock"){
                winOrLose = 0;
            }
        }
        return winOrLose;
    }


}
