

public class Rectangle {

    private double width=0;
    private double height=0;

    //Constructors named after the class:

    /**
       Constructor to assign values to each field (width and height)
       @param width The width
       @param height The height
    */
    public Rectangle(double width, double height) {
	//one option:      
	setWidth(width);
	setHeight(height);
	/*
	//another option:
	this.width = height;
	this.height = height;
	*/
    }

    public Rectangle(Rectangle rectangle) {
	setHeight(rectangle.getHeight());
	setWidth(rectangle.getWidth());
	
	/* Or:
	   double width = rectangle.getWidth();
	   double height = rectangle.getHeight();
	   rectangle.setWidth(width);
	   rectangle.setHeight(height);
	*/
    }

    public Rectangle() {
	width = 0;
	height = 0;
    }

    /**
       Sets the width of the rectangle
       @param w the new width
       @throws IllegalArgumentException Will be thrown when a negative (or zero) value is passed in.
    */
    public void setWidth(double w) {
	if (w<=0) {
	    IllegalArgumentException iae = new IllegalArgumentException("Width must be positive.");
	    throw iae;
	}
	width = w;
    }
    
    public boolean equals(Rectangle rectangle) {
	/*
	if (width == rectangle.width && height == rectangle.height || width == rectangle.height && height == rectangle.width) {
	    return true;
	}
	else {
	    return false;
	}
	*/
	//or
	if (rectangle.getArea() == getArea()) {
	    return true;
	}
	else {
	    return false;
	}
    }
	
    public boolean equals(double width, double height) {
	return (getArea() == width*height);
    }

    public double getWidth() {
	return width;
    }

    public void setHeight(double h) {
	if (h<=0) {
	    IllegalArgumentException iae = new IllegalArgumentException("Height must be positive.");
	    throw iae;
	}
	height = h;
    }

    public double getHeight() {
	return height;
    }

    public double getArea() {
	double area = width * height;
	return area;
    }

    public void printData() {
	System.out.println("The height of the rectangle is "+height+" and the width is "+width+".");
    }

}
