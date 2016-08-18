/* Written by
   Simonseth Roffe
   Lab Tuesday 4-5:50
*/

public class RightTriangleDriver {

    /**
       Tests out the triangle class.
    */
    public static void main(String[] args) {
	RightTriangle tri = new RightTriangle();
	tri.setLegA(5);
	tri.setLegB(10);
	System.out.println("A right triangle with leg lengths "+tri.getLegA()+" and "+tri.getLegB()+" has hypotenuse "+tri.getHypotenuse()+" and perimeter "+tri.getPerimeter());
    }
}
