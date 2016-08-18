/* Written by
   Simonseth Roffe
   Lab Tuesday 4-5:50 pm
*/

public class CropDriver {
    /** Tests the Crop class by making three crop
	objects and testing various methods within
	the crop class.
    */
    public static void main(String[] args) {

	Crop corn = new Crop("Corn",5000,0.25);
	Crop wheat = new Crop();
	wheat.setName("Wheat");
	wheat.setYield(8000);
	wheat.setPrice(0.003);
	Crop corn2 = new Crop(corn);
	corn2.setYield(5500);
	
	//test toString method
	System.out.println(corn.toString());
	System.out.println(wheat.toString());
	System.out.println(corn2.toString());

	//test equals method
	if (corn.equals(wheat)) {
	    System.out.println("The first and second crops are equal.");
	}
	
	if (corn.equals(corn2)) {
	    System.out.println("The first and third crops are equal.");
	}
    }
}
