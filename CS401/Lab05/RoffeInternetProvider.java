/* Written by:
   Simonseth Roffe
   sdr45@pitt.edu
   CS401 - Tuesday 4-5:50 Lab
*/

import java.util.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.io.*;

/** Internet provider program for Lab05
 */
public class RoffeInternetProvider
{
    /** Calculates a customers total cost depending on the 
	package (A, B, or C) that he/she subscribed to and how many hours were used and
	displays past history of the user's internet usage (average hours used, average 
	price paid, and total paid amount).
	Inputs would be the history filename, package, A, B, or C (Capitalization doesn't matter),
	and the number of hours used which should be a positive number that doesn't
	have to be a whole number. 
	The output would produce a numerical amountthat is the total monthly cost
	depending on parameters preset by the package letter. The output would be
	a currency amount in a currency format. It then appends the new information
	(hours used and the total cost) to the file that was opened.
    */
    public static void main (String[] args) {
	//Catch the IOexception from PrintWriter
	try {
	    Scanner input = new Scanner(System.in);

	    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
	    DecimalFormat fmt2Dec = new DecimalFormat("0.00");

	    double avgHoursHistory = 0.0;
	    double avgPaidHistory = 0.0;
	    double hoursHistory = 0.0;
	    double paidHistory = 0.0;
	    double totalPaidHistory = 0.0;
	    int count = 0;
	    File file = new File("");

	    //Get history file and make sure it exists
	    System.out.print("File of prior internet usage: ");
	    String fileName = input.nextLine();

	    try {
		file = new File(fileName);

		Scanner inFile = new Scanner(file);
		//Readout the file
		while (inFile.hasNext()) {
		    double histHour = inFile.nextDouble();
		    hoursHistory = hoursHistory + histHour;
		    double paid = inFile.nextDouble();
		    paidHistory = paidHistory + paid;
		    totalPaidHistory = totalPaidHistory + paid;
		    count++;
		}
		inFile.close();
		avgHoursHistory = hoursHistory / count;
		avgPaidHistory = paidHistory / count;
	    }
	    catch (FileNotFoundException fnf) {
		System.out.println("File doesn't exist. Creating it.");
	    }

	    FileWriter fw = new FileWriter(file.getName(), true);
	    PrintWriter output = new PrintWriter(fw);

	    if (count != 0) {
		System.out.println("Average hours used: " + fmt2Dec.format(avgHoursHistory));
		System.out.println("Average amount paid: " + currencyFormat.format(avgPaidHistory));
		System.out.println("Total paid: " + currencyFormat.format(totalPaidHistory));
	    }

	    //Get input on the package letter and make sure it is one of the packages
	    System.out.print("What package have you subscribed to? A, B, or C? ");
	    String subscription = input.nextLine();

	    while (!subscription.equalsIgnoreCase("a") && !subscription.equalsIgnoreCase("b") && !subscription.equalsIgnoreCase("c")) {
		System.out.println("\nThe package name must be one of the provided packages. [A, B, or C]");
		System.out.print("What package have you subscribed to? A, B, or C? ");
		subscription = input.nextLine();
	    }
	    
	    //Get hours used and make sure it is an actual double
	    boolean invalid;
	    double hours = 0.0;
	    do {
		try {
		    System.out.print("How many hours have you used? ");
		    hours = input.nextDouble();
		    if (hours < 0.00){
			System.out.println("The number of hours must be a positive number!");
			invalid = true;
		    }
		    else{
			invalid = false;
		    }
		}
		catch (InputMismatchException ime) {
		    System.out.println("The number of hours must be a positive number!");
		    input.nextLine();
		    invalid = true;
		}
	    } while (invalid);

	    //Package A - $9.95 monthly cost. 10 free hours and $2.00 for every hour after that.
	    if (subscription.equalsIgnoreCase("a")){
		double perMonth = 9.95;
		double totalCost = 0.0;
	       	if (hours <= 10.00){
		    totalCost = perMonth;
		    System.out.println("\nYour total cost is " + currencyFormat.format(totalCost));
		}

		else if (hours > 10) {
		    double notFreeHours = hours - 10;
		    double costHours = notFreeHours * 2.00;
		    totalCost = costHours+perMonth;
		    System.out.println("\nYour total cost is " + currencyFormat.format(totalCost));
		}
		output.print("\n"+hours + " " + totalCost);
		output.close();
	    }

	    //Package B - $13.95 monthly cost. 20 free hours and $1.00 for every hour after that.
	    else if (subscription.equalsIgnoreCase("b")){
		double perMonth = 13.95;
		double totalCost = 0.0;
	       	if (hours <= 20.00){
		    totalCost = perMonth;
		    System.out.println("\nYour total cost is " + currencyFormat.format(totalCost));
		}

		else if (hours > 20) {
		    double notFreeHours = hours - 20;
		    double costHours = notFreeHours * 1.00;
		    totalCost = costHours+perMonth;
		    System.out.println("\nYour total cost is " + currencyFormat.format(totalCost));
		}
		output.print("\n"+hours + " " + totalCost);
		output.close();
	    }

	    //Package C - $19.95 monthly cost. Unlimited hour usage.
	    else if (subscription.equalsIgnoreCase("c")){
		double perMonth = 19.95;
		double totalCost = 0.0;
		totalCost = perMonth;
		System.out.println("\nYour total cost is " + currencyFormat.format(totalCost));
		output.print("\n"+hours + " " + totalCost);
		output.close();
	    }
	}
	catch (IOException ioe) {
	    System.exit(0);
	}
    }
}	    
