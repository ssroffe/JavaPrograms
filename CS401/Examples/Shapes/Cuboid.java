/* Written by
   Simonseth Roffe
*/

public class Cuboid extends Rectangle implements Shape3D {
    private double depth;

    public Cuboid(double width, double height, double depth) {
	super(width, height);
	setDepth(depth);
    }

    public void setDepth(double d) {
	if (d <= 0) {
	    throw new IllegalArgumentException("Depth must be positive.");
	}
	depth = d;
    }

    public double getDepth() {
	return depth;
    }
    
    public boolean equals(Cuboid c) {
	/*
	//Are the rectangle portions of our cuboids equal?
	boolean rectsEqual = super.equals(c);
	//depths portions equal?
	boolean depthsEqual = depth == c.depth;
	return rectsEqual && depthsEqual;

	//super.equals(c) && (depth == c.depth);
	*/
	if (getHeight() == c.getHeight() && getWidth() == c.getWidth() && depth == c.depth) {
	    return true;
	}
	else {
	    return false;
	}
    }

    public String toString() {
	return "Cuboid: width="+getWidth()+", height="+getHeight()+", depth="+getDepth();
    }

    public double volume() {
	//volume = depth*height*width
	return depth*getHeight()*getWidth();
    }

    public double surfaceArea() {
	//2*width*height + 2*height*depth + 2*depth*width
	return (2*getWidth()*getHeight()) + (2*getHeight()*depth) + (2*depth*getWidth());
    }
}
