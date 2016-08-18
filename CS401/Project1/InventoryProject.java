/*Written by
  Simonseth Roffe
  Lab Tuesday 4-5:50
  Programming Project 1
*/

import java.util.*;
import java.text.NumberFormat;
import java.io.*;

public class InventoryProject {
    /**
       Keeps track of an inventory and allows the user to buy, sell,
       or change the price of any of the three products. It consists
       of a main menu where the user chooses a product, and a submenu
       where the user chooses to buy, sell, or change the price or
       any combination of such.
       Input of the User should be whatever is prompted. This includes
       an inventory filename, 0,1,2,3 for menu choices, a number of items to sell, an amount
       to buy and the price per item to buy or changing the price of a
       product.
       The Output is that it prints the new information (the total balance
       and the quantity and price of each item) to the inventory file
       that was opened.
    */
    public static void main(String[] args) throws FileNotFoundException, IOException {
	
	Scanner input = new Scanner(System.in);
	NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
	
	//get file name and make sure it exists
	System.out.print("Please input the file name: ");
	String fileName = input.nextLine();
	System.out.print("\n");
	File file = new File(fileName);

	while (!file.exists()){
	    System.out.println("The file does not exist.");
	    System.out.print("Please input the file name: ");
	    fileName = input.nextLine();
	    file = new File(fileName);
	}

	//read out the file using / as a delimiter
	Scanner inFile = new Scanner(file);
	inFile.useDelimiter("[/]+");

	double balance = inFile.nextDouble();
	inFile.nextLine();

	String name1 = inFile.next();
	int quantity1 = inFile.nextInt();
	double price1 = inFile.nextDouble();
	inFile.nextLine();

	String name2 = inFile.next();
	int quantity2 = inFile.nextInt();
	double price2 = inFile.nextDouble();
	inFile.nextLine();

	String name3 = inFile.next();
	int quantity3 = inFile.nextInt();
	double price3 = inFile.nextDouble();
	inFile.nextLine();
	
	inFile.close();
	
	int mainMenu = 5;

	//main menu screen - make sure choices are one of the 4 choices
	while (mainMenu != 0) {

	    System.out.println("Balance: " + currencyFormat.format(balance));
	    System.out.print("1. " + name1);
	    System.out.println("\t (" + quantity1 + " at " + currencyFormat.format(price1) + ")");
	    System.out.print("2. " + name2);
	    System.out.println("\t (" + quantity2 + " at " + currencyFormat.format(price2) + ")");
	    System.out.print("3. " + name3);
	    System.out.println("\t (" + quantity3 + " at " + currencyFormat.format(price3) + ")");
	    System.out.println("0. Exit");

	    boolean invalidMainMenu;
	    do {
		try {
		    System.out.print("\nPlease enter choice: ");
		    mainMenu = input.nextInt();

		    if (mainMenu < 0 || mainMenu > 3){
			System.out.println("Must enter one of the choices! [0, 1, 2, 3]");
			invalidMainMenu = true;
		    }
		    else {
			invalidMainMenu = false;
		    }
		}
		catch (InputMismatchException ime) {
		    System.out.println("Must enter one of the choices! [0, 1, 2, 3]");
		    input.nextLine();
		    invalidMainMenu = true;
		}
	    } while (invalidMainMenu);

	    //item 1
	    boolean invalidSubMenu;
	    if (mainMenu == 1) {
		int choice = 5;
		while (choice != 0) {
		    System.out.println("Current balance: " + currencyFormat.format(balance));
		    System.out.println("Current quantity: " + quantity1);
		    System.out.println("Current price: " + currencyFormat.format(price1));
		    System.out.println("1. Sell " + name1);
		    System.out.println("2. Buy " + name1);
		    System.out.println("3. Change price");
		    System.out.println("0. Return to main menu");

		    //choice to buy, sell, exit, or change price. Make sure one of those.
		    do {
			try {
			    System.out.print("\nPlease enter choice: ");
			    choice = input.nextInt();

			    if (choice < 0 || choice > 3){
				System.out.println("Must enter one of the choices! [0, 1, 2, 3]");
				invalidSubMenu = true;
			    }
			    else {
				invalidSubMenu = false;
			    }
			}
			catch (InputMismatchException ime) {
			    System.out.println("Must enter one of the choices! [0, 1, 2, 3]");
			    input.nextLine();
			    invalidSubMenu = true;
			}
		    } while (invalidSubMenu);
		    
		    //Selling
		    if (choice == 1) {
			boolean invalidSelling = true;
			int sellingNum = 0;
			do {
			    //get amount to sell and make sure it is valid
			    try {
				System.out.print("Amount to sell (Current quantity: " + quantity1 + "): ");
				sellingNum = input.nextInt();
				
				if (sellingNum < 0) {
				    System.out.println("Must be positive");
				    invalidSelling = true;
				}
				else if (sellingNum > quantity1) {
				    System.out.print("Warning. You are about to sell more than you have. Continue with the sale? [yes/no] ");
				    input.nextLine();
				    String contSale = input.nextLine();
				    while (!contSale.equalsIgnoreCase("yes") && !contSale.equalsIgnoreCase("no")) {
					System.out.println("You need to tell me yes or no.");
					System.out.print("Continue with the sale? [yes/no] ");
					contSale = input.nextLine();
				    }
				    if (contSale.equalsIgnoreCase("yes")) {
					invalidSelling = false;
				    }
				    else if (contSale.equalsIgnoreCase("no")) {
					System.out.println("Sale cancelled. Try again.");
					invalidSelling = true;
				    }
				}
				else {
				    invalidSelling = false;
				}
			    }
			    catch (InputMismatchException ime) {
				System.out.println("Must be a positive number!");
				input.nextLine();
				invalidSelling = true;
			    }
			} while (invalidSelling);
			balance = balance + (price1*sellingNum);
			quantity1 = quantity1 - sellingNum;
		    }
		    //buying
		    else if (choice == 2) {
			boolean invalidBuying = true;
			boolean invalidBuyingNum = true;
			boolean invalidBuyingPrice = true;
			int buyingNum = 0;
			double buyingPrice = 0.0;
			//get amount to buy and price per item
			do {
			    do {
				try {
				    System.out.print("Amount to buy: ");
				    buyingNum = input.nextInt();
				
				    if (buyingNum < 0) {
					System.out.println("Must be a positive number!");
					invalidBuyingNum = true;
				    }
				    else {
					invalidBuyingNum = false;
				    }
				}
				catch (InputMismatchException ime) {
				    System.out.println("Must be a positive number!");
				    input.nextLine();
				    invalidBuyingNum = true;
				}
			    } while (invalidBuyingNum);
			    do {
				try {
				    System.out.print("Price per item: ");
				    buyingPrice = input.nextDouble();
				
				    if (buyingPrice < 0) {
					System.out.println("Must be positive!");
					invalidBuyingPrice = true;
				    }
				    else {
					invalidBuyingPrice = false;
				    }
				}
				catch (InputMismatchException ime) {
				    System.out.println("Must be a positive number!");
				    input.nextLine();
				    invalidBuyingPrice = true;
				}
			    } while (invalidBuyingPrice);
			    //Cant spend more than the current balance
			    if ((buyingPrice * buyingNum) > balance) {
				System.out.println("You are spending more money than you have! Try again.");
				invalidBuying = true;
			    }
			    else {
				invalidBuying = false;
			    }
			} while (invalidBuying);
			balance = balance - (buyingPrice * buyingNum);
			quantity1 = quantity1 + buyingNum;			
		    }
		    //changing price
		    else if (choice ==3) {
			boolean invalidPriceChange = true;
			double newPrice = 0.0;
			do {
			    try {
				System.out.print("New price: ");
				newPrice = input.nextDouble();
				
				if (newPrice < 0) {
				    System.out.println("Must be a positive number!");
				    invalidPriceChange = true;
				}
				else {
				    invalidPriceChange = false;
				}
			    }
			    catch (InputMismatchException ime) {
				System.out.println("Must be a positive number!");
				input.nextLine();
				invalidPriceChange = true;
			    }
			} while (invalidPriceChange);
			price1 = newPrice;
		    }
		}
	    }

	    //item 2
	    if (mainMenu == 2) {
		int choice = 5;
		while (choice != 0) {
		    System.out.println("Current balance: " + currencyFormat.format(balance));
		    System.out.println("Current quantity: " + quantity2);
		    System.out.println("Current price: " + currencyFormat.format(price2));
		    System.out.println("1. Sell " + name2);
		    System.out.println("2. Buy " + name2);
		    System.out.println("3. Change price");
		    System.out.println("0. Return to main menu");

		    do {
			try {
			    System.out.print("\nPlease enter choice: ");
			    choice = input.nextInt();

			    if (choice < 0 || choice > 3){
				System.out.println("Must enter one of the choices! [0, 1, 2, 3]");
				invalidSubMenu = true;
			    }
			    else {
				invalidSubMenu = false;
			    }
			}
			catch (InputMismatchException ime) {
			    System.out.println("Must enter one of the choices! [0, 1, 2, 3]");
			    input.nextLine();
			    invalidSubMenu = true;
			}
		    } while (invalidSubMenu);
		    
		    if (choice == 1) {
			boolean invalidSelling = true;
			int sellingNum = 0;
			do {
			    try {
				System.out.print("Amount to sell (Current quantity: " + quantity2 + "): ");
				sellingNum = input.nextInt();
				
				if (sellingNum < 0) {
				    System.out.println("Must be positive");
				    invalidSelling = true;
				}
				else if (sellingNum > quantity2) {
				    System.out.print("Warning. You are about to sell more than you have. Continue with the sale? [yes/no] ");
				    input.nextLine();
				    String contSale = input.nextLine();
				    while (!contSale.equalsIgnoreCase("yes") && !contSale.equalsIgnoreCase("no")) {
					System.out.println("You need to tell me yes or no.");
					System.out.print("Continue with the sale? [yes/no] ");
					contSale = input.nextLine();
				    }
				    if (contSale.equalsIgnoreCase("yes")) {
					invalidSelling = false;
				    }
				    else if (contSale.equalsIgnoreCase("no")) {
					System.out.println("Sale cancelled. Try again.");
					invalidSelling = true;
				    }
				}
				else {
				    invalidSelling = false;
				}
			    }
			    catch (InputMismatchException ime) {
				System.out.println("Must be a positive number!");
				input.nextLine();
				invalidSelling = true;
			    }
			} while (invalidSelling);
			balance = balance + (price2*sellingNum);
			quantity2 = quantity2 - sellingNum;
		    }
		    else if (choice == 2) {
			boolean invalidBuying = true;
			boolean invalidBuyingNum = true;
			boolean invalidBuyingPrice = true;
			int buyingNum = 0;
			double buyingPrice = 0.0;
			do {
			    do {
				try {
				    System.out.print("Amount to buy: ");
				    buyingNum = input.nextInt();
				
				    if (buyingNum < 0) {
					System.out.println("Must be a positive number!");
					invalidBuyingNum = true;
				    }
				    else {
					invalidBuyingNum = false;
				    }
				}
				catch (InputMismatchException ime) {
				    System.out.println("Must be a positive number!");
				    input.nextLine();
				    invalidBuyingNum = true;
				}
			    } while (invalidBuyingNum);
			    do {
				try {
				    System.out.print("Price per item: ");
				    buyingPrice = input.nextDouble();
				
				    if (buyingPrice < 0) {
					System.out.println("Must be positive!");
					invalidBuyingPrice = true;
				    }
				    else {
					invalidBuyingPrice = false;
				    }
				}
				catch (InputMismatchException ime) {
				    System.out.println("Must be a positive number!");
				    input.nextLine();
				    invalidBuyingPrice = true;
				}
			    } while (invalidBuyingPrice);
			    if ((buyingPrice * buyingNum) > balance) {
				System.out.println("You are spending more money than you have! Try again.");
				invalidBuying = true;
			    }
			    else {
				invalidBuying = false;
			    }
			} while (invalidBuying);
			balance = balance - (buyingPrice * buyingNum);
			quantity2 = quantity2 + buyingNum;			
		    }
		    else if (choice ==3) {
			boolean invalidPriceChange = true;
			double newPrice = 0.0;
			do {
			    try {
				System.out.print("New price: ");
				newPrice = input.nextDouble();
				
				if (newPrice < 0) {
				    System.out.println("Must be a positive number!");
				    invalidPriceChange = true;
				}
				else {
				    invalidPriceChange = false;
				}
			    }
			    catch (InputMismatchException ime) {
				System.out.println("Must be a positive number!");
				input.nextLine();
				invalidPriceChange = true;
			    }
			} while (invalidPriceChange);
			price2 = newPrice;
		    }
		}
	    }

	    //item 3
	    if (mainMenu == 3) {
		int choice = 5;
		while (choice != 0) {
		    System.out.println("Current balance: " + currencyFormat.format(balance));
		    System.out.println("Current quantity: " + quantity3);
		    System.out.println("Current price: " + currencyFormat.format(price3));
		    System.out.println("1. Sell " + name3);
		    System.out.println("2. Buy " + name3);
		    System.out.println("3. Change price");
		    System.out.println("0. Return to main menu");

		    do {
			try {
			    System.out.print("\nPlease enter choice: ");
			    choice = input.nextInt();

			    if (choice < 0 || choice > 3){
				System.out.println("Must enter one of the choices! [0, 1, 2, 3]");
				invalidSubMenu = true;
			    }
			    else {
				invalidSubMenu = false;
			    }
			}
			catch (InputMismatchException ime) {
			    System.out.println("Must enter one of the choices! [0, 1, 2, 3]");
			    input.nextLine();
			    invalidSubMenu = true;
			}
		    } while (invalidSubMenu);
		    
		    if (choice == 1) {
			boolean invalidSelling = true;
			int sellingNum = 0;
			do {
			    try {
				System.out.print("Amount to sell (Current quantity: " + quantity3 + "): ");
				sellingNum = input.nextInt();
				
				if (sellingNum < 0) {
				    System.out.println("Must be positive");
				    invalidSelling = true;
				}
				else if (sellingNum > quantity3) {
				    System.out.print("Warning. You are about to sell more than you have. Continue with the sale? [yes/no] ");
				    input.nextLine();
				    String contSale = input.nextLine();
				    while (!contSale.equalsIgnoreCase("yes") && !contSale.equalsIgnoreCase("no")) {
					System.out.println("You need to tell me yes or no.");
					System.out.print("Continue with the sale? [yes/no] ");
					contSale = input.nextLine();
				    }
				    if (contSale.equalsIgnoreCase("yes")) {
					invalidSelling = false;
				    }
				    else if (contSale.equalsIgnoreCase("no")) {
					System.out.println("Sale cancelled. Try again.");
					invalidSelling = true;
				    }
				}
				else {
				    invalidSelling = false;
				}
			    }
			    catch (InputMismatchException ime) {
				System.out.println("Must be a positive number!");
				input.nextLine();
				invalidSelling = true;
			    }
			} while (invalidSelling);
			balance = balance + (price3*sellingNum);
			quantity3 = quantity3 - sellingNum;
		    }
		    else if (choice == 2) {
			boolean invalidBuying = true;
			boolean invalidBuyingNum = true;
			boolean invalidBuyingPrice = true;
			int buyingNum = 0;
			double buyingPrice = 0.0;
			do {
			    do {
				try {
				    System.out.print("Amount to buy: ");
				    buyingNum = input.nextInt();
				
				    if (buyingNum < 0) {
					System.out.println("Must be a positive number!");
					invalidBuyingNum = true;
				    }
				    else {
					invalidBuyingNum = false;
				    }
				}
				catch (InputMismatchException ime) {
				    System.out.println("Must be a positive number!");
				    input.nextLine();
				    invalidBuyingNum = true;
				}
			    } while (invalidBuyingNum);
			    do {
				try {
				    System.out.print("Price per item: ");
				    buyingPrice = input.nextDouble();
				
				    if (buyingPrice < 0) {
					System.out.println("Must be positive!");
					invalidBuyingPrice = true;
				    }
				    else {
					invalidBuyingPrice = false;
				    }
				}
				catch (InputMismatchException ime) {
				    System.out.println("Must be a positive number!");
				    input.nextLine();
				    invalidBuyingPrice = true;
				}
			    } while (invalidBuyingPrice);
			    if ((buyingPrice * buyingNum) > balance) {
				System.out.println("You are spending more money than you have! Try again.");
				invalidBuying = true;
			    }
			    else {
				invalidBuying = false;
			    }
			} while (invalidBuying);
			balance = balance - (buyingPrice * buyingNum);
			quantity3 = quantity3 + buyingNum;			
		    }
		    else if (choice ==3) {
			boolean invalidPriceChange = true;
			double newPrice = 0.0;
			do {
			    try {
				System.out.print("New price: ");
				newPrice = input.nextDouble();
				
				if (newPrice < 0) {
				    System.out.println("Must be a positive number!");
				    invalidPriceChange = true;
				}
				else {
				    invalidPriceChange = false;
				}
			    }
			    catch (InputMismatchException ime) {
				System.out.println("Must be a positive number!");
				input.nextLine();
				invalidPriceChange = true;
			    }
			} while (invalidPriceChange);
			price3 = newPrice;
		    }
		}
	    }
	}
	//Write out the new quantities onto the same file
	PrintWriter output = new PrintWriter(fileName);
	output.println(balance+"/");
	output.println(name1+"/"+quantity1+"/"+price1+"/");
	output.println(name2+"/"+quantity2+"/"+price2+"/");
	output.println(name3+"/"+quantity3+"/"+price3+"/");
	output.close();
    }
}
