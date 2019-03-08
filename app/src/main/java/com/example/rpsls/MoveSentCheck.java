package com.example.rpsls;

public class MoveSentCheck {


    public static boolean checkIfMovesExist(String [] array){
        boolean i = false;
        if ((!array[0].isEmpty()) && (!array[1].isEmpty())){
            i = true;
        }
        else if ((array[0].isEmpty()) || (array[1].isEmpty())){
            i = false;
        }
        return i;
    }


}
