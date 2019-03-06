package com.example.rpsls;

public class MoveSentCheck {

    public static boolean checkIfMovesExist(String [] array){
        boolean i = false;
        if ((array[0] != null) && (array[1] != null)){
            i = true;
        }
        else if ((array[0] == null) || (array[1] == null)){
            i = false;
        }
        return i;
    }
}
