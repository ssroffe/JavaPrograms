/* Written by
   Simonseth Roffe
*/

import java.util.Scanner;
import java.util.InputMismatchException;
import java.lang.Math;

public class Energy {
    /** find final velocity
	@return vf final velocity
    */
    public static double finalV() {
	Scanner in = new Scanner(System.in);
	
	System.out.print("What is the initial velocity in m/s? ");
	double v0 = in.nextDouble();
	System.out.print("What is the initial height in m? ");
	double h0 = in.nextDouble();
	System.out.print("what is the final height in m? ");
	double hf = in.nextDouble();
	
	double g = 9.81;

	double vf = Math.sqrt(2*(0.5*Math.pow(v0,2)+(g*h0)-(g*hf)));

	return vf;
    }

    /** find final height
	@return hf final height
    */
    public static double finalH() {
	Scanner in = new Scanner(System.in);
	
	System.out.print("What is the initial velocity in m/s? ");
	double v0 = in.nextDouble();
	System.out.print("What is the initial height in m? ");
	double h0 = in.nextDouble();
	System.out.print("what is the final velocity in m/s? ");
	double vf = in.nextDouble();

	double g = 9.81;
	
	double hf = ((0.5*Math.pow(v0,2))+(g*h0)-(0.5*Math.pow(vf,2)))/g;

	return hf;
    }

    /** Work done by friction
	@return w work done by friction
    */
    public static double wFriction() {
	Scanner in = new Scanner(System.in);
	
	System.out.print("What is the mass in kg? ");
	double m = in.nextDouble();
	System.out.print("What is the initial velocity in m/s? ");
	double v0 = in.nextDouble();
	System.out.print("What is the initial height in m? ");
	double h0 = in.nextDouble();
	System.out.print("what is the final velocity in m/s? ");
	double vf = in.nextDouble();
	System.out.print("What is the final height in m? ");
	double hf = in.nextDouble();

	double g = 9.81;

	double vNoFriction = Math.sqrt(2*(0.5*Math.pow(v0,2)+(g*h0)-(g*hf)));

	double w = 0.5*m*(Math.pow(vf,2)-Math.pow(vNoFriction,2));
	
	return w;
    }	

    public static void main(String[] args) {
	Scanner in = new Scanner(System.in);
	int choice = -1;
	boolean invalid = true;
	do{
	    try { 
		System.out.println("What are you looking for?");
		System.out.print("final velocity[0], final height[1], Work done by friction[2]: ");
		choice = in.nextInt();
		if (choice < 0 || choice > 2) {
		    System.out.println("Please choose one of the choices! 0, 1, or 2.");

		    invalid = true;
		}
		else {
		    invalid = false;
		}
	    }
	    catch (InputMismatchException ime) {
		System.out.println("Please choose one of the choices! 0, 1, or 2.");
		in.nextLine();
		invalid = true;
	    }
	} while (invalid);
	if (choice == 0) {
	    double vf = finalV();
	    System.out.println("The final velocity is "+vf+" m/s.");
	}
	else if (choice == 1) {
	    double hf = finalH();
	    System.out.println("The final height is "+hf+" m.");
	}
	else if (choice == 2) {
	    double w = wFriction();
	    System.out.println("The work done by friction is "+w+" J.");
	}
    }
}
