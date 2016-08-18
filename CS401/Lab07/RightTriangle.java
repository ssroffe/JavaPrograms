/* Written by
   Simonseth Roffe
   Lab Tuesday 4-5:50
*/

/** Represents a right triangle using the lengths of the two legs, A and B.
    @author Simonseth Roffe
*/
public class RightTriangle {
    
    private double legA = 0;
    private double legB = 0;

    /** Sets the length of leg A.
	@param a the new length of leg A.
	@throws IllegalArgumentException Will be thrown when a 
	negative or zero value is passed in.
    */
    public void setLegA(double a) {
	if (a <= 0 ) {
	    IllegalArgumentException iae = new IllegalArgumentException("Leg A must be greater than 0");
	    throw iae;
	}
	legA = a;
    }

    /** Sets the length of leg B.
	@param b the new length of leg B.
	@throws IllegalArgumentException Will be thrown when a 
	negative or zero value is passed in.
    */
    public void setLegB(double b) {
	if (b <= 0 ) {
	    IllegalArgumentException iae = new IllegalArgumentException("Leg B must be greater than 0");
	    throw iae;
	}
	legB = b;
    }

    /** returns the value of the length of leg A
	@return legA the length of leg A
    */
    public double getLegA() {
	return legA;
    }

    /** returns the value of the length of leg B
	@return legB the length of leg B
    */
    public double getLegB() {
	return legB;
    }

    /** Calculates and returns the hypotenuse of the triangle
	with leg lengths legA and legB.
	@return hypotenuse The hypotenuse of the triangle.
    */
    public double getHypotenuse() {
	double hypotenuse = Math.sqrt((legA * legA) + (legB * legB));
	return hypotenuse;
    }
    
    /** Calculates and returns the perimeter of the triangle
	with side lengths legA, legB, and the hypotenuse.
	@return perimeter the perimeter of the triangle
    */
    public double getPerimeter() {
	double hypotenuse = getHypotenuse();
	double perimeter = legA + legB + hypotenuse;
	return perimeter;
    }
}
