/* Written by
   Simonseth Roffe
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

	//get the list of people's names and store it into an arraylist of strings
	ArrayList<String> names = new ArrayList<String>();
	//input.nextLine();
	boolean inputNames = true;
	int count = 1;
	boolean confirm = false;
	do {
	    System.out.print("Person "+(count)+" is (or empty string to exit): ");
	    String person = input.nextLine();
	    count++;
	    if (person.length() == 0) {
		inputNames = false;
	    }
	    else {
		names.add(person);
		inputNames = true;
	    }

	    if (!inputNames) {
		do {
		    System.out.println("\nThe names in the list are:\n");
		    for (int i = 0; i<names.size(); i++) {
			System.out.println(names.get(i));
		    }
		    System.out.print("\nIs this correct? y/n ");
		    String confirmation = input.nextLine();
		    if (confirmation.equalsIgnoreCase("yes") || confirmation.equalsIgnoreCase("y")) {
			confirm = true;
		    }
		    else if (confirmation.equalsIgnoreCase("no") || confirmation.equalsIgnoreCase("n")) {
			confirm = true;
			inputNames = true;
			names.clear();
			count = 1;
		    }
		    else {
			System.out.println("\nPlease input yes [y] or no [n]");
			confirm = false;
		    }
		} while (!confirm);
	    }

	} while (inputNames);
	
	//make an arrayList of integers to map people to
	ArrayList<Integer> exchange = new ArrayList<Integer>();
	for (int i = 0; i<names.size(); i++) {
	    exchange.add(i);
	}

	//Don't want people to get themselves
	boolean getSelf = true;
	do {
	    getSelf = false;
	    //shuffles the array of integers
	    Collections.shuffle(exchange);
	    for (int i = 0; i<names.size(); i++) {
		if (exchange.get(i) == i) {
		    getSelf = true;
		}
	    }
	} while (getSelf);

	System.out.println("Done. File names are the buyer. File contains who buyer is buying for.");
	
	//write files
	try {
	    for (int i = 0; i<names.size(); i++) {
		PrintWriter output = new PrintWriter(names.get(i)+".txt");
		//Map number in shuffled integer array to original names array.
		output.print("You are buying for "+names.get(exchange.get(i))+".");
		output.close();
	    }
	}
	catch (IOException ioe) {
	    System.out.println("There was an IO exception.");
	}
    }
}
