/* Written by
   Simonseth Roffe
*/

import java.util.*; 
public class Factorial {

    public static void main(String[] args) {
	Scanner in = new Scanner(System.in);
	int m = 0;
	boolean invalid = true;
	do {
	    try {
		System.out.print("What factorial would you like to know? ");
		m = in.nextInt();
		if (m<0) {
		    System.out.println("The input must be an integer greater than or equal to 0!");
		    invalid = true;
		}
		else {
		    invalid = false;
		}
	    }
	    catch (InputMismatchException ime) {
		System.out.println("The input must be an integer greater than or equal to 0!");
		invalid = true;
		in.nextLine();
	    }
	} while (invalid);

	System.out.println(m+"! = "+factorial(m));
    }


    public static int factorial(int n) {
	
	if (n == 1) return 1;
	else if (n == 0) return 0;

	return n * factorial(n-1);
    }
}
