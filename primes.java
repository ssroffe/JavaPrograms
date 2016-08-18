/* Written by
   Simonseth Roffe
*/

import java.util.*;

public class primes {
    /** Calculates all of the primes between 2 and a defined number
	prints out the primes and the number of primes.
    */

    public static void main(String[] args) {
	
	Scanner in = new Scanner(System.in);
	
	System.out.println("I will calculate the number of primes between 2 and a defined limit");
	boolean invalid = true;
	int limit = 0;

	do {
	    try {
		System.out.print("What integer do you want to calculate to? ");
		limit = in.nextInt();
		if (limit < 0) {
		    System.out.println("The integer must be greater than 0");
		    invalid = true;
		}
		else {
		    invalid = false;
		}
	    }
	    catch (InputMismatchException ime) {
		System.out.println("The input must be an integer greater or equal to 0");
		invalid = true;
		in.nextLine();
	    }
	} while (invalid);

	if (limit < 2) {
	    System.out.println("The number of primes from 0 to " + limit + " is 0.");
	}
	else {
	    ArrayList<Integer> numbers = new ArrayList<Integer>();

	    for (Integer i = 2; i <= limit; i++) {
		numbers.add(i);
	    }

	    for (int i = 0; i < numbers.size(); i++ ) {
		for (Integer j=2; (numbers.get(i)*j) <= limit; j++) {
		    Integer n = j*numbers.get(i);
		    numbers.remove(n);
		}
	    }

	    System.out.println("The primes from 0 to " + limit + " are:");

	    for (int i = 1; i < numbers.size(); i++) {
		System.out.print(numbers.get(i-1)+"\t");
		if ( i % 10 == 0 ) {
		    System.out.print("\n");
		}
	    }

	    System.out.println("\n The number of primes are: " + numbers.size());
	}
    }
}
