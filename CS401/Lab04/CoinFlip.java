/* Written by:
   Simonseth Roffe
   sdr45@pitt.edu
   CS401 - Tuesday 4-5:50 Lab
*/

/** Flips a coin a specified number of times. Tells how many times
    heads showed up.
    Inputs should be a positive integer that is how many times the user
    wants the coin to be flipped.
    Output would tell the user how many times heads showed up.
*/

import java.util.Random;
import java.util.Scanner;

public class CoinFlip {

    public static void main(String[] args) {
	//get number of coins flipped
	Scanner input = new Scanner(System.in);

	System.out.print("How many times would you like to flip the coin? ");
	int flipNumber = input.nextInt();
	while (flipNumber <0){
	    //flipNumber must be positive
	    System.out.println("The number of times flipped must be a positive integer");
	    System.out.print("How many times would you like to flip the coin? ");
	    flipNumber = input.nextInt();	    
	}
	int heads = 0;

	//Flip the coin flips times
	Random flipCoin = new Random();
	for (int i = 0; i<flipNumber; i++) {
	    boolean flip = flipCoin.nextBoolean();
	    if (flip) {
		heads++;
	    }
	}

	System.out.println("Done. Heads showed up "+heads+" times.");
    }
}
