/* Written by:
   Simonseth Roffe
   sdr45@pitt.edu
   CS401 - Tuesday 4-5:50 Lab
*/

/**
   Determine whether a one-way street in New York City is eastbound or
   westbound.  Even-numbered streets are eastbound and odd-numbered
   streets are westbound.
   
   This program takes an integer (the street number) and displays whether
   it is eastbound or westbound.
 */

import java.util.Scanner;

public class NYCStreetsEW {

    public static void main(String[] args) {

        Scanner kb = new Scanner(System.in);
	String goAgain = "y";

        do {
	    //get street number

	    System.out.print("Please enter the number for a one-way street in NYC: ");

	    int streetNum = kb.nextInt();
	    while (streetNum < 1 || streetNum > 155) {
		//invalid, street must be between 1 and 155
		System.out.println("The street number must be between 1 and 155.");
		System.out.print("Please enter the number for a one-way street in NYC: ");
		streetNum = kb.nextInt();
	    }
        
	    //is street number even or odd?
	    if (streetNum % 2 == 0) {
		//even => east-bound
		System.out.println("It is eastbound.");
		System.out.print("Would you like to ask again? (y/n) ");
		goAgain = kb.next();
	    }
	    else {
		//odd => west-bound
		System.out.println("It is westbound.");
		System.out.print("Would you like to ask again? (y/n) ");
		goAgain = kb.next();
	    }
	} while (goAgain.equalsIgnoreCase("y") || goAgain.equalsIgnoreCase("yes"));
    }
}
