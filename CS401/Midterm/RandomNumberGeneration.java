/*Written by
  Simonseth Roffe
  Midterm version A
  Random Number Generation Program
*/

import java.util.*;

public class RandomNumberGeneration {
    
    /** This method rolls a 6-sided die once and then
	returns what the face of the roll is.
	@param die The name of the Random object that is being used.
	@return toss The integer value of the face that the die landed on.
    */
    public static int nextDieToss(Random die) {
	//Roll die and make sure it is in the range [1,6]
	int toss = 1+die.nextInt(6);
	return toss;
    }
    
    /** This method rolls a 6-sided die a specified number of times 
	and then returns the average of all of the face values that
	the rolls landed on.
	@param dice The name of the Random object that is being used.
	@param numRolls The number of Rolls that was specified to be done.
	@return average The average face value of all of the rolls.
    */
    public static int nextDieTosses(Random dice, int numRolls) {
	int total = 0;
	int i;
      
	//Get the total of all the rolls
	for (i=0; i < numRolls; i++) {
	    total += nextDieToss(dice);
	}

	//Get average by dividing total by the number of rolls
	int average = total/i;
	return average;
    }
	    
    /** Rolls a 6-sided die once and then says what the face value is.
	Then, it rolls a dice a number of times specified by the user
	and says what the average face value of the roll is. The input
	would be the number of rolls that must be an integer greater than
	0. Output would be telling the user the integer face value of one roll and then
	the integer average value of the specified number of rolls.
    */
    public static void main(String[] args) {
	//Get the Random object
	Random dice = new Random();

	//Get the value of one roll and print it out
	int roll = nextDieToss(dice);
	System.out.println("The face of a single roll landed on "+roll);
	
	//Get the number of rolls and make sure it is an integer greater than 0
	Scanner kb = new Scanner(System.in);
	boolean invalid = true;
	int numRolls = 0;
	do {
	    try {
		System.out.print("How many dice would you like to roll? ");
		numRolls = kb.nextInt();
		if (numRolls <= 0) {
		    System.out.println("The number of rolls must be an integer greater than 0!");
		    invalid = true;
		}
		else {
		    invalid = false;
		}
	    }
	    catch (InputMismatchException ime) {
		System.out.println("The number of rolls must be an integer greater than 0!");
		invalid = true;
		kb.nextLine();
	    }
	} while (invalid);
	
	//Get the average roll for a the number of rolls specified
	int averageRoll = nextDieTosses(dice,numRolls);

	System.out.println("The average face of the "+numRolls+" rolls was a "+averageRoll);
    }
}
