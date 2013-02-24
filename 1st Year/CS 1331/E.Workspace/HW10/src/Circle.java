/**
 * This is HW10, Circle
 * @author Joon Ki Hong
 * @version 4/8/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The Circle class extends the Shape abstract class. It represents a circle and contains the various
 * overridden abstract methods of the Shapes abstract class. It also contains a compareTo method which is 
 * forced by the implementation of Comparable in the Shapes abstract class.
 */
public class Circle extends Shape{
	private int radius;
	
	public Circle(int radius){
		super(radius*2,radius*2);
		this.radius = radius;
	}
	public double area(){
		return (Math.PI*Math.pow(radius,2));
	}
	public double perimeter(){
		return (2*Math.PI*radius);
	}
	public boolean equals(Object obj){
		/*The conditional tests to see if the Object given through the parameter is of the same class as this this class.
		 * It then checks to see if the heights are and widths or the same or if the height is equal to the width and
		 * the width to the height*/
		if (obj.getClass().equals(this.getClass()) && ((((Shape)obj).getWidth() == width) && ((Shape)obj).getHeight() == height) || obj.getClass().equals(this.getClass()) && ((((Shape)obj).getWidth() == height) && ((Shape)obj).getHeight() == width)){
			return true;
		}
		else {
			return false;
		}
	}
	public int compareTo(Object shape){
		int result;
		result = super.compareTo(shape);
		return result;
	}
	public String toString(){
		return ("This circle " + super.toString());
	}
}
