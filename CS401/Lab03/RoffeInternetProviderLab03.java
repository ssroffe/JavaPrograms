/* Written by:
   Simonseth Roffe
   sdr45@pitt.edu
   CS401 - Tuesday 4-5:50 Lab
*/

import java.util.*;
import java.text.NumberFormat;

/** Internet provider program for Lab03
 */
public class RoffeInternetProviderLab03
{
    /** Calculates a customers total cost depending on the 
	package (A, B, or C) that he/she subscribed to and how many hours were used.
	Inputs would be the package, A, B, or C (Capitalization doesn't matter),
	and the number of hours used which should be a positive number that doesn't
	have to be a whole number. The output would produce a numerical amount
	that is the total monthly cost depending on parameters preset by the
	package letter. The output would be a currency amount in a currency format.
    */
    public static void main (String[] args) {

	//Get input on the package letter
	Scanner input = new Scanner(System.in);

	System.out.print("What package have you subscribed to? A, B, or C? ");
	String subscription = input.nextLine();

	//Get hours used and make sure it is an actual double
       	System.out.print("How many hours have you used? ");
	while (!input.hasNextDouble()) {
	    System.out.print("\nThe hours you used must be a positive number.\nPlease input how many hours you used. ");
	    input.next();
	}
	double hours = input.nextDouble();

	NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

	//Package A - $9.95 monthly cost. 10 free hours and $2.00 for every hour after that.
	if (subscription.equalsIgnoreCase("a")){
		double perMonth = 9.95;
	       	if (0.00 <= hours && hours <= 10.00){
		    System.out.println("\nYour total cost is " + currencyFormat.format(perMonth));
		}

		else if (hours > 10) {
		    double notFreeHours = hours - 10;
		    double costHours = notFreeHours * 2.00;
		    double totalCost = costHours+perMonth;
		    System.out.println("\nYour total cost is " + currencyFormat.format(totalCost));
		}
		else {
		    System.out.println("\nThe hours you used must be a postive number.");
		    System.exit(0);
		}
	}

	//Package B - $13.95 monthly cost. 20 free hours and $1.00 for every hour after that.
	else if (subscription.equalsIgnoreCase("b")){
		double perMonth = 13.95;
	       	if (0.00 <= hours && hours <= 20.00){
		    System.out.println("\nYour total cost is " + currencyFormat.format(perMonth));
		}

		else if (hours > 20) {
		    double notFreeHours = hours - 20;
		    double costHours = notFreeHours * 1.00;
		    double totalCost = costHours+perMonth;
		    System.out.println("\nYour total cost is " + currencyFormat.format(totalCost));
		}
		else {
		    System.out.println("\nThe hours you used must be a postive number.");
		    System.exit(0);
		}
	}

	//Package C - $19.95 monthly cost. Unlimited hour usage.
	else if (subscription.equalsIgnoreCase("c")){
		double perMonth = 19.95;
	       	if (0.00 <= hours){
		    System.out.println("\nYour total cost is " + currencyFormat.format(perMonth));
		}
		else {
		    System.out.println("\nThe hours you used must be a postive number.");
		    System.exit(0);
		}
	}

	else {
	    System.out.println("\nThe package name must be one of the provided packages. [A, B, or C]");
	}
    }
}
	    
