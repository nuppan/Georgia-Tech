/**
 * This is HW10, Shape
 * @author Joon Ki Hong
 * @version 4/8/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The Shape abstract class defines the structure of all Shape objects. It contains width and height protected
 * variables, public abstract methods, and public non-abstract methods.
 */
public abstract class Shape implements Comparable{
	
	protected int width,height;
	
	public Shape(){
		this(0,0);
	}
	public Shape(int width, int height){
		this.width = width;
		this.height = height;
	}
	/**
	 * The area method returns the area of a given Shape inherited object
	 * @return double
	 */
	public abstract double area();
	
	/**
	 * The perimeter method returns the perimeter of a given Shape inherited object
	 * @return double
	 */
	public abstract double perimeter();
	
	//This equals method overrides the equals method in java.lang.Object
	public abstract boolean equals(Object obj);
	
	//This compareTo method is defined in the Comparable interface
	public int compareTo(Object shape){
		if (area() == ((Shape)shape).area()){
			return 0;
		}
		else if (area() > ((Shape)shape).area()){
			return 1;
		}
		else {
			return -1;
		}
	}
	
	//Getters for instance variables
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	//End getters
	
	public String toString(){
		return ("has width " + width + " and height " + height);
	}
	
}
