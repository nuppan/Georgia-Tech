import Jama.Matrix;

/**
 * 
 * @author Joon Ki Hong
 * MATH2605 Project 2
 */
public class Vector {
	protected double[] x0;
	protected int size;
	
	public Vector(double...components){
		x0 = components;
		size = components.length;
	}
	
	public double dotProduct(Vector x1){
		double result=0;
		if (getNumElements()!=x1.getNumElements()){
			System.out.println("Vector sizes are not the same! Cannot dot product.");
			return (Double) null;
		}
		else{
			for (int index=0;index<getNumElements();index++){
				result += x0[index]*x1.x0[index];
			}
		}
		return result;
	}
	
	public double magnitude(){
		double sum = 0;
		for (int x=0;x<x0.length;x++){
			sum += (x0[x]*x0[x]);
		}
		return Math.sqrt(sum);
	}
	
	public Vector multiplyMatrix(Matrix A){
		if (getNumElements()!=A.getColumnDimension()){
			System.out.println("Cannot multiply this matrix with this vector.");
			return null;
		}
		else{
			double temp[] = new double[x0.length];
			for (int row=0;row<x0.length;row++){
				double rowResult = 0;
				for (int column=0;column<x0.length;column++){
					rowResult+= A.get(row, column)*x0[column];
				}
				temp[row] = rowResult;
			}
			return new Vector(temp);
		}
	}
	public Vector subtractVector(Vector x1){
		double temp[] = new double[x0.length];
		if (getNumElements()!=x1.getNumElements()){
			System.out.println("Cannot subtract this matrix with this vector.");
			return null;
		}
		else{
			for (int x=0;x<x0.length;x++){
				temp[x] = x0[x]-x1.x0[x];
			}
		}
		return new Vector(temp);
	}
	
	public Vector addVector(Vector x1){
		double temp[] = new double[x0.length];
		if (getNumElements()!=x1.getNumElements()){
			System.out.println("Cannot add this matrix with this vector.");
			return null;
		}
		else{
			for (int x=0;x<x0.length;x++){
				temp[x] = x0[x]+x1.x0[x];
			}
		}
		return new Vector(temp);
	}
	
	public Vector multiplyConstant(double constant){
		double temp[] = new double[x0.length];
		for (int x=0;x<x0.length;x++){
			temp[x] = x0[x]*constant;
		}
		return new Vector(temp);
	}

	
	public String toString(){
		String result = "[";
		for (int x=0;x<getNumElements();x++){
			if (x==getNumElements()-1){
				result=result+Double.toString(x0[x]);
			}
			else{
				result=result+Double.toString(x0[x])+",";
			}
		}
		result=result+"]";
		return result;
	}
	
	public int getNumElements(){
		return size;
	}
	
}
