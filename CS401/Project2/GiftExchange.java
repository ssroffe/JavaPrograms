/* Written by
   Simonseth Roffe
   Project 2
   Lab Tuesday 4-5:50
*/

import java.util.*;
import java.io.*;

public class GiftExchange {
    
    /** Performs a gift exchange pairing. Inputs include the number of people
	participating which must be greater than 2 people and the names of
	those people. The outputs are files that are named for the buyer
	containing a string that explains who that person is buying for.
    */
    public static void main(String[] args) throws FileNotFoundException {	
	
	Scanner input = new Scanner(System.in);
	boolean invalid = true;
	int number = 0;

	//Get the number of participants
	do {
	    try {
		System.out.print("How many people are participating? ");
		number = input.nextInt();
		if (number <= 1) {
		    System.out.println("There must be at least 2 people participating!");
		    invalid = true;
		}
		else {
		    invalid = false;
		}
	    }
	    catch (InputMismatchException ime) {
		System.out.print("The number of participants must be an integer greater than or equal to 2!");
		invalid = true;
	    }
	} while (invalid);
	
	//get the list of people's names and store it into an array of strings
	String names[] = new String[number];
	input.nextLine();
	for (int i = 0; i<number; i++) {
	    System.out.print("Person "+(i+1)+" is: ");
	    String person = input.nextLine();
	    names[i] = person;
	}
	
	//make an arrayList of integers to map people to
	ArrayList<Integer> exchange = new ArrayList<Integer>();
	for (int i = 0; i<names.length; i++) {
	    exchange.add(i);
	}

	//Don't want people to get themselves
	boolean getSelf = true;
	do {
	    getSelf = false;
	    //shuffles the array of integers
	    Collections.shuffle(exchange);
	    for (int i = 0; i<number; i++) {
		if (exchange.get(i) == i) {
		    getSelf = true;
		}
	    }
	} while (getSelf);

	System.out.println("Done. File names are the buyer. File contains who buyer is buying for.");
	
	//write files
	try {
	    for (int i = 0; i<number; i++) {
		PrintWriter output = new PrintWriter(names[i]+".txt");
		//Map number in shuffled integer array to original names array.
		output.print("You are buying for "+names[exchange.get(i)]+".");
		output.close();
	    }
	}
	catch (IOException ioe) {
	    System.out.println("There was an IO exception.");
	}
    }
}
