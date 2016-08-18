
public class CuboidDriver {
    public static void main(String[] args) {
	Cuboid c = new Cuboid(5, 6, 7);
	//Cuboid c = new Cuboid();
	//c.setDepth(8);

	c.setDepth(8);
	c.setWidth(15);

	//System.out.println("The cuboid is: "+c);
	c.toString();
    }
}
