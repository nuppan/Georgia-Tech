/**
 * This is HW10, Similar
 * @author Joon Ki Hong
 * @version 4/8/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The Similar interface was created to be implemented in the RightTriangle class. In this case it forces
 * the implementation of the isSimilar method.
 */
public interface Similar {
	/**
	 * The isSimilar method takes in a Shape object (child classes) and checks to see if the shape is a
	 * triangle. It then checks to see if it contains a similar angle in which case implies similarity.
	 * @param shape Shape object. Can be either a rectangle, circle, or a right triangle.
	 * @return boolean
	 */
	public boolean isSimilar(Shape shape);
	public double perimeter();
	public int compareTo(Object obj);
	public double area();
}
