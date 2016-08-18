/* Written by:
   Simonseth Roffe
   sdr45@pitt.edu
   CS401 - Tuesday 4-5:50 Lab
*/

/** This program calculates the floor of a logarithmic function.
    The floor function is the amount of times a number is divided by the
    base until the output is less than the base.
    The input should be the base, b, and the number, x, which should both
    be integers larger than 1.
    The output should be how many divisions it took until the output was
    less than the base.
*/

import java.util.Scanner;

public class FloorLog {
    public static void main(String[] args){
	//get b and x
	Scanner input = new Scanner(System.in);
	
	System.out.print("What is the base of the logarithm? ");
	int b = input.nextInt();
	
	while(b<=1){
	    //b must be greater than 1
	    System.out.println("The base has to be greater than one.");
	    System.out.print("What is the base of the logarithm? ");
	    b = input.nextInt();
	}

	//initial x0 before any division
	System.out.print("What is the x value within the logarithm? ");
	int xInitial = input.nextInt();
	while (xInitial<=1){
	    //x must be greater than 1
	    System.out.println("The x in the logarithm has to be greater than 1.");
	    System.out.print("What is the x value within the logarithm? ");
	    xInitial = input.nextInt();
	}

	//divide while keeping track of number of loops
	int floor = 0;	
	int x = xInitial;
	while (b<=x){
	    x = x/b;
	    floor++;
	}
	System.out.println("The floor of log_"+b+"("+xInitial+") is "+floor+".");
    }
}
