/* Written by
   Simonseth Roffe
   Midterm version A
   Right Circular Cone program
*/

import java.util.*;
import java.lang.Math;

public class RightCircularCone {
    /** Calculates the the volume of a right circular cone. Inputs should be the
	radius and the height which should be positive doubles (0 is not considered positive).
        The output is the volume of the cone constructed by (pi)*r^2*(h/3). The output is in the 
	form of a double.
    */
    public static void main(String[] args) {
	
	Scanner input = new Scanner(System.in);       
	System.out.println("Let's calculate the volume of a right circular cone!");

	//get the radius and make sure it is greater than 0 and a double
	boolean invalidRadius = true;
	double radius = 0.0;
	double height = 0.0;
	do {
	    try {
		System.out.print("What is the radius? ");
		radius = input.nextDouble();
		if (radius <= 0) {
		    System.out.println("Radius must be a number greater than 0! Try again.");
		    invalidRadius = true;
		}
		else {
		    invalidRadius = false;
		}
	    }
	    catch (InputMismatchException ime) {
		System.out.println("Radius must be a number greater than 0! Try again.");
		input.nextLine();
		invalidRadius = true;
	    }
	} while (invalidRadius);

	//get the height and make sure it is a greater than 0 double	
	boolean invalidHeight = true;
	do {
	    try {
		System.out.print("What is the height? ");
		height = input.nextDouble();
		if (height <= 0) {
		    System.out.println("Height must be greater than 0! Try again.");
		    invalidHeight = true;
		}
		else {
		    invalidHeight = false;
		}
	    }
	    catch (InputMismatchException ime) {
		System.out.println("Height must be a number greater than 0! Try again.");
		input.nextLine();
		invalidHeight = true;
	    }
	} while (invalidHeight);

	//calculate the volume with the given equation
	double volume = Math.PI * Math.pow(radius,2) * (height/3);
	
	//Say what the volume is.
	System.out.println("The volume of the cone is " + volume);
    }
}
