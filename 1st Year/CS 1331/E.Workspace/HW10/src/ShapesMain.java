/**
 * This is HW10, ShapesMain
 * @author Joon Ki Hong
 * @version 4/8/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The ShapesMain class contains the main method that has at least one instance of each Shape object and
 * runs the varies methods and prints them out for comparison and accuracy.
 */
public class ShapesMain {
	
	public static void main(String[] args){
		Rectangle rectangle1 = new Rectangle(5,5);
		Rectangle rectangle2 = new Rectangle(5,5);
		Rectangle rectangle3 = new Rectangle(4,3);
				
		Circle circle1 = new Circle(5);
		Circle circle2 = new Circle(5);
		Circle circle3 = new Circle(3);
		
	
		Similar triangle1 = new RightTriangle(4,4);
		RightTriangle triangle2 = new RightTriangle(4,4);
		RightTriangle triangle3 = new RightTriangle(1,3);
		RightTriangle triangle4 = new RightTriangle(3,1);
		RightTriangle triangleSimilar = new RightTriangle(6,18);
		
		
		//Rectangles
		System.out.println("Rectangles");
		System.out.println(rectangle1.toString());
		System.out.println("Rectangle 1 Area: "+rectangle1.area()+" Perimeter: " +rectangle1.perimeter());
		System.out.println();
		System.out.println(rectangle2.toString());
		System.out.println("Rectangle 2 Area: "+rectangle2.area()+" Perimeter: " +rectangle2.perimeter());
		System.out.println();
		System.out.println(rectangle3.toString());
		System.out.println("Rectangle 3 Area: "+rectangle3.area()+" Perimeter: " +rectangle3.perimeter());
		System.out.println();
		System.out.println("Rectangle 1 & Rectangle 2 area comparison: "+rectangle1.compareTo(rectangle2));
		System.out.println("Rectangle 1 & Rectangle 3 area comparison: "+rectangle1.compareTo(rectangle3));
		System.out.println("Rectangle 3 & Rectangle 2 area comparison: "+rectangle3.compareTo(rectangle2));
		System.out.println("Rectangle 1 & Rectangle 2 equality: "+rectangle1.equals(rectangle2));
		System.out.println("Rectangle 1 & Rectangle 3 equality: "+rectangle1.equals(rectangle3));
		System.out.println("Rectangle 1 & Triangle 1 equality: "+rectangle1.equals(triangle1));
		System.out.println();
		//Circles
		System.out.println("Circles");
		System.out.println(circle1.toString());
		System.out.println("Circle 1 Area: "+circle1.area()+" Perimeter: "+circle1.perimeter());
		System.out.println();
		System.out.println(circle2.toString());
		System.out.println("Circle 2 Area: "+circle2.area()+" Perimeter: "+circle2.perimeter());
		System.out.println();
		System.out.println(circle3.toString());
		System.out.println("Circle 3 Area: "+circle3.area()+" Perimeter: "+circle3.perimeter());
		System.out.println();
		System.out.println("Circle 1 & Circle 2 area comparison: "+circle1.compareTo(circle2));
		System.out.println("Circle 1 & Circle 3 area comparison: "+circle1.compareTo(circle3));
		System.out.println("Circle 3 & Circle 2 area comparison: "+circle3.compareTo(circle2));
		System.out.println("Circle 1 & Circle 2 equality: "+((Object)circle1).equals(circle2));
		System.out.println("Circle 1 & Circle 3 equality: "+circle1.equals(circle3));
		System.out.println("Circle 1 & Triangle 1 equality: "+circle1.equals(triangle1));
		System.out.println();
		//Triangles
		System.out.println("Triangles");
		System.out.println(triangle1.toString());
		System.out.println("Triangle 1 Area: "+triangle1.area()+" Perimeter: "+triangle1.perimeter());
		System.out.println();
		System.out.println(triangle2.toString());
		System.out.println("Triangle 2 Area: "+triangle2.area()+" Perimeter: "+triangle2.perimeter());
		System.out.println();
		System.out.println(triangle3.toString());
		System.out.println("Triangle 3 Area: "+triangle3.area()+" Perimeter: "+triangle3.perimeter());
		System.out.println();
		System.out.println("Triangle 1 & Triangle 2 area comparison: "+triangle1.compareTo(triangle2));
		System.out.println("Triangle 1 & Triangle 3 area comparison: "+triangle1.compareTo(triangle3));
		System.out.println("Triangle 3 & Triangle 2 area comparison: "+triangle3.compareTo(triangle2));
		System.out.println("Triangle 1 & Triangle 2 equality: "+triangle1.equals(triangle2));
		System.out.println("Triangle 1 & Triangle 3 equality: "+triangle1.equals(triangle3));
		System.out.println("Triangle 1 & Rectangle 1 equality: "+circle1.equals(rectangle1));
		System.out.println("Triangle 1 & Triangle 2 similarity: "+((Similar)triangle1).isSimilar(triangle2));
		System.out.println("Triangle 1 & Triangle 3 similarity: "+triangle1.isSimilar(triangle3));
		//Triangle Similarity cases
		System.out.println("Triangle 4 & Triangle 3 similarity: "+triangle4.isSimilar(triangle3));
		System.out.println("Triangle 3 & TriangleSim similarity: "+triangle3.isSimilar(triangleSimilar));
	}

}
