
public class RectangleDriver {
    public static void main(String[] args) {
	Rectangle r = new Rectangle(5,10);
	System.out.println("The rectangle's area is "+r.getArea());

	Rectangle r2 = new Rectangle(7,7);
	System.out.println("The rectangle's area is "+r2.getArea());

	Rectangle r3 = new Rectangle(r2);
	System.out.println("The rectangle's area is "+r3.getArea());

	Rectangle r4 = new Rectangle();
	System.out.println("The rectangle's area is "+r4.getArea());
    }
}
//Don't need to import Rectangle class since they are in the same folder
