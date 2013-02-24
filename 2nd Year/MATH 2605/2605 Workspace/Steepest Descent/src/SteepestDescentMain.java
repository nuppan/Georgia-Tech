import Jama.Matrix;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
/**
 * 
 * @author Joon Ki Hong
 * MATH2605 Project 2
 */

public class SteepestDescentMain {
	
	
	public static void main(String[] args){
		//Solve Problem 1 part a.
		System.out.println("------------------------------------------------------------------------------");
		double matrix1[][] = {{5,2},{2,1}};
		Matrix A1 = new Matrix(matrix1);
		Vector b1 = new Vector(1,1);
		System.out.println("Integer Coordinates that mathematically satisfy the Level Curve z=0");
		ArrayList<Double> startingPts = new ArrayList<Double>();
		for (double x=-50;x<50;x+=.25){
			for (double y=-50;y<50;y+=.25){
				if ((2*0)==((A1.get(0, 0)*x*x)+(A1.get(0,1)*x*y)+(A1.get(1,0)*x*y)+(A1.get(1,1)*y*y)-(2*b1.x0[0]*x)-(2*b1.x0[1]*y))){
					System.out.print("("+x+","+y+")");
					startingPts.add(x);
					startingPts.add(y);
				}
			}
		}
		System.out.println("");
		for (int x=0;x<startingPts.size()-1;x+=2){
			Vector x01 = new Vector(startingPts.get(x),startingPts.get(x+1));
			SteepestDescentImpl implementation = new SteepestDescentImpl(A1,x01,b1,Math.pow(10, -5));
			Vector result = implementation.sdAlgorithm();
			System.out.println("Matrix:");
			A1.print(2,3);
			System.out.println("Vector x:"+x01);
			System.out.println("Vector b:"+b1);
			System.out.println("Solution:");
			System.out.println(result.toString());
			System.out.println("--------------------------------------------------------"); //Space between calculations
		}
		System.out.println("------------------------------------------------------------------------------");
		//Solve Problem 1 part b.
		double matrix2[][] = {{1.001,-0.999},{-0.999,1.001}};
		Matrix A2 = new Matrix(matrix2);
		Vector b2 = new Vector(1,2);
		System.out.println("Integer Coordinates that mathematically satisfy the Level Curve z=0");
		ArrayList<Double> startingPts2 = new ArrayList<Double>();
		for (double x=-50;x<50;x+=.25){
			for (double y=-50;y<50;y+=.25){
				if ((2*0)==((A2.get(0, 0)*x*x)+(A2.get(0,1)*x*y)+(A2.get(1,0)*x*y)+(A2.get(1,1)*y*y)-(2*b2.x0[0]*x)-(2*b2.x0[1]*y))){
					System.out.print("("+x+","+y+")");
					startingPts2.add(x);
					startingPts2.add(y);
				}
			}
		}
		System.out.println("");
		
		//Generate 10 random 10x10 matrices as well as b vectors and solve Ax=b.
		for (int x=0;x<startingPts2.size()-1;x+=2){
			Vector x02 = new Vector(startingPts2.get(x),startingPts2.get(x+1));
			SteepestDescentImpl implementation2 = new SteepestDescentImpl(A2,x02,b2,Math.pow(10, -5));
			Vector result2 = implementation2.sdAlgorithm();
			System.out.println("Matrix:");
			A2.print(2,3);
			System.out.println("Vector x:"+x02);
			System.out.println("Vector b:"+b2);
			System.out.println("Solution:");
			System.out.println(result2);
			System.out.println("--------------------------------------------------------"); //Space between calculations
		}
		
		for (int loop=0;loop<5;loop++){
			Random rand = new Random();
			double matrix3[][] = new double[10][10];
			for (int row=0;row<10;row++){
				for (int col=0;col<10;col++){
					Double posNeg = rand.nextDouble();
					if (posNeg>.50){
						matrix3[row][col] = rand.nextInt(5);
					}
					else{
						matrix3[row][col] = -1*rand.nextInt(5);
					}
				}
			}
			double vectorArray[] = new double[10];
			for (int x=0;x<10;x++){
				Double posNeg = rand.nextDouble();
				if (posNeg>.50){
					vectorArray[x] = rand.nextInt(20);
				}
				else{
					vectorArray[x] = -1*rand.nextInt(20);
				}
			}
			
			Matrix A3 = new Matrix(matrix3);
			Matrix A3T = A3.transpose();
			Matrix B = A3T.times(A3);
			Vector b3 = new Vector(vectorArray);
			Vector x03 = new Vector(1,1,1,1,1,1,1,1,1,1);
			SteepestDescentImpl implementation3 = new SteepestDescentImpl(B,x03,b3,Math.pow(10, -5));
			Vector result3 = implementation3.sdAlgorithm();
			System.out.println("Matrix:");
			B.print(10, 0);
			System.out.println("Vector x:"+x03);
			System.out.println("Vector b:"+b3);
			System.out.println("Solution:");
			System.out.println(result3.toString());
			System.out.println("");//Space between calculations
		}
	}
}

