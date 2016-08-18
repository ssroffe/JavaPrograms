/* Written by
   Simonseth Roffe
   Lab Tuesday 4-5:50 pm
*/

import java.util.*;

public class CoinToss {

    /** Game where a player guesses how many heads there will be
	within 7 coin flips. Computer also guesses how many heads
	there will be. The one who is closest to the actual number
	of heads in the 7 flips gets a point. First to 10 points wins.
	If there is a tie, both the player and computer get a point.
	If both the player and the computer get to 10 points at the same time
	the game keeps going until the tie is broken.
	Inputs would be an integer number between 0 and 7. Nothing else would be
	accepted. Outputs are the score, the computer's guess, the number of heads
	and the winner of the round or game.
	
	Written by: Simonseth Roffe
	Test version B
	Coin Toss Program
    */
    public static void main(String[] args) {
	// Get scanner and random objects
	Scanner in = new Scanner(System.in);
	Random rand = new Random();
	
	int playerScore = 0;
	int compScore = 0;
	boolean end = false;
	// Loop for the game itself
	do {
	    boolean invalid = true;
	    int guess = 0;
	    // loop for the player's guess. Make sure it is valid.
	    do {
		try {
		    System.out.print("Player 1, how many heads do you think will show up (out of 7 flips): ");
		    guess = in.nextInt();
		    if (guess < 0 || guess > 7) {
			System.out.println("Your guess must be an integer between 0 and 7!");
			invalid = true;
		    }
		    else {
			invalid = false;
		    }
		}
		catch (InputMismatchException ime) {
		    System.out.println("Your guess must be an integer between 0 and 7!");
		    in.nextLine();
		    invalid = true;
		}
	    } while (invalid);
	    
	    //Get computer's guess
	    int compGuess = rand.nextInt(8);
	    System.out.println("The computer guesses there will be " + compGuess + " heads.");

	    //Flip the coin 7 times. Count how many heads there are
	    int numHeads = 0;
	    for (int i = 0; i < 7; i++) {
		boolean flip = rand.nextBoolean();
		if (flip) {
		    numHeads++;
		}
	    }

	    // Find out who wins the round
	    if (Math.abs(numHeads - guess) < Math.abs(numHeads - compGuess)) {
		System.out.println("There are "+numHeads+" heads. Player 1 wins this round!");
		playerScore++;
	    }
	    else if (Math.abs(numHeads - guess) > Math.abs(numHeads - compGuess)) {
		System.out.println("There are "+numHeads+" heads. The computer wins this round!");
		compScore++;
	    }
	    
	    else if (Math.abs(numHeads - guess) == Math.abs(numHeads - compGuess)) {
		System.out.println("There are "+numHeads+" heads. It is a tie!");
		compScore++;
		playerScore++;
	    }

	    System.out.println("The score is:");
	    System.out.println("Player 1: "+playerScore);
	    System.out.println("Computer: "+compScore);

	    //See if anyone wins
	    if (playerScore >= 10 && playerScore > compScore) {
		System.out.println("Player 1 wins!");
		end = true;
	    }
	    else if (compScore >= 10 && playerScore < compScore) { 
		System.out.println("The computer wins!");
		end = true;
	    }
	    else {
		System.out.println();
		end = false;
	    }
	} while(!end);
    }
}
