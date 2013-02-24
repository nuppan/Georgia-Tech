/**
 * This is HW10, Rectangle
 * @author Joon Ki Hong
 * @version 4/8/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The Rectangle class extends the Shape abstract class. It represents a rectangle and contains the various
 * overridden abstract methods of the Shapes abstract class. It also contains a compareTo method which is 
 * forced by the implementation of Comparable in the Shapes abstract class.
 */
public class Rectangle extends Shape{
	
	public Rectangle(int width, int height){
		super(width,height);
	}
	public double area(){
		return (width*height);
	}
	public double perimeter(){
		return (2*(width+height));
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
	public int compareTo(Shape shape){
		int result;
		result = super.compareTo(shape);
		return result;
	}
	public String toString(){
		return ("This rectangle " + super.toString());
	}
	
}
