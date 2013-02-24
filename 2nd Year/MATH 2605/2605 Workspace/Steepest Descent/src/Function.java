import Jama.Matrix;

/**
 * 
 * @author jo0nn
 * MATH2605 Project 2
 */

public class Function {
	private Vector x0;
	private Matrix A;
	private Vector b;
	
	public Function(Vector x0, Matrix A, Vector b){
		this.x0 = x0;
		this.A = A;
		this.b = b;
	}
	
	
	public double evalAtX(){
		Vector Ax = x0.multiplyMatrix(A);
		double xAx = x0.dotProduct(Ax);
		double bx = b.dotProduct(x0);
		return (.5*(xAx))-bx;
	}
	public void setx0(Vector x0){
		this.x0 = x0;
	}
	
	public Vector evald0(){
		Vector gradientAtX = evalGradientAtX();
		return gradientAtX.multiplyConstant(-1.0);
	}
	
	private Vector evalGradientAtX(){
		Vector Ax = x0.multiplyMatrix(A);
		return Ax.subtractVector(b);
	}
	
	
	
	
}