import java.util.Scanner;
import java.lang.Math;

public class Kinematics {
    /** Solves a general kinematic problem in physics. Solves for range
	or initial velocity
    */
    public static void main(String[] args){
	Scanner input = new Scanner(System.in);
	System.out.print("What are you trying to solve for? range[0], velocity[1], height[2] ");
	String start = input.nextLine();
	double g = 9.81;
	
	if (start.equalsIgnoreCase("0") || start.equalsIgnoreCase("range")){
	    System.out.print("What is the initial velocity? ");
	    double v = input.nextDouble();
	    
	    System.out.print("What is the angle? ");
	    double theta0 = input.nextDouble();
	    double theta = Math.toRadians(theta0);
	    
	    System.out.print("What is the initial y value? ");
	    double y0 = input.nextDouble();
	    
	    double x = v*Math.cos(theta)*((v*Math.sin(theta)+Math.sqrt(v*v*Math.sin(theta)*Math.sin(theta)+2*g*y0))/g);
	    System.out.println("The range is "+x);
	}
	else if (start.equalsIgnoreCase("1") || start.equalsIgnoreCase("velocity")){
	    System.out.print("What is the range? ");
	    double x = input.nextDouble();
	    
	    System.out.print("What is the angle? ");
	    double theta0 = input.nextDouble();
	    double theta = Math.toRadians(theta0);

	    double v = Math.sqrt((x*g)/(Math.sin(2*theta)));
	    System.out.println("The initial velocity is "+v);
	}

	else if (start.equalsIgnoreCase("2") || start.equalsIgnoreCase("height")){
	    System.out.print("What is the initial velocity? ");
	    double v = input.nextDouble();
	   
	    System.out.print("What is the angle? ");
	    double theta0 = input.nextDouble();
	    double theta = Math.toRadians(theta0);
	    
	    System.out.print("What is the initial y value? ");
	    double y0 = input.nextDouble();
	    
	    double y = y0+((v*v*Math.sin(theta)*Math.sin(theta))/(2*g));
	    System.out.println("The maximum height is "+y);
	}
	else{
	    System.out.println("You need to tell me what you are solving for.");
	    System.exit(0);
	}
    }
}
