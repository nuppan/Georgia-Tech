import Jama.Matrix;

/**
 * 
 * @author Joon Ki Hong
 * MATH2605 Project 2
 */

public class SteepestDescentImpl {
	private Vector x0;
	private Matrix A;
	private double eigenValue;
	private Function fcn;
	
	public SteepestDescentImpl(Matrix A, Vector x0, Vector b, double eigenValue){
		this.A = A;
		this.x0 = x0;
		this.eigenValue = eigenValue;
		fcn = new Function(x0,A,b);
	}
	
	public Vector sdAlgorithm(){
		Vector xPrevious = x0;
		Vector dPrevious = fcn.evald0();
		int count = 0;
		while (!(dPrevious.magnitude()<eigenValue)){
			xPrevious = xPrevious.addVector(dPrevious.multiplyConstant(((Math.pow(dPrevious.magnitude(),2))/(dPrevious.dotProduct(dPrevious.multiplyMatrix(A))))));
			fcn.setx0(xPrevious);
			dPrevious = fcn.evald0();
			count++;
		}
		System.out.println("The calculations took "+Integer.toString(count)+" steps");
		System.out.println(dPrevious);
		return xPrevious;
	}

	
}
