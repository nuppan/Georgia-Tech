/**
 * @author Westley Schrack
 */
import java.util.ArrayList;

public class MatrixMath {
	private Matrix a;
	private Vector householderX;
	private Vector givensX;
	private Vector luX;
	private Vector b;
	private Matrix householderQ;
	private Matrix householderR;
	private Matrix givensQ;
	private Matrix givensR;
	private Matrix l; 
	private Matrix u;
	private Vector lUDecompY;
	
/**
 * Constructor for solving Ax = b, takes in A and b
 * @param a A of Ax = b
 * @param b b of Ax = b
 */
	public MatrixMath(Matrix a, Vector b) {
		this.a = a;
		this.b = b;
	}

	/**
	 * Solves Ax = b by using LU decomposition, solving for L first by 
	 * finding the Gaussian matrices then taking their inverse to get L.
	 * The solving Ly = b and Using U to be L(inv)*A = U and then solving 
	 * Ux=b
	 * @return The solution of x
	 */
	public Vector LUDecomp() {
		ArrayList<Matrix> gaussianElim = new ArrayList<Matrix>();
		Matrix lInv = Matrix.identity(a.getNumColumns());
		Matrix l = Matrix.identity(a.getNumColumns());
		Matrix m = new Matrix(a);
		double factor = 0;
		for (int x = 1; x < m.getNumRows(); x++) {
			for (int y = x; y < m.getNumRows(); y++) {
				if (m.getElement(y, x - 1) != 0) {
					Matrix gaussianElimM = Matrix.identity(a.getNumColumns());
					factor = m.getElement(y, x - 1)
							/ m.getElement(x - 1, x - 1);
					gaussianElimM.setElement(y, x - 1, (-1) * factor);
					gaussianElim.add(gaussianElimM);
					lInv.setElement(y,x-1, (-1) * factor);
					l.setElement(y,x-1, factor);
				}
				for (int z = 0; z < m.getNumColumns(); z++) {
					double value = ((m.getElement(x - 1, z) * factor) - m
							.getElement(y, z));
					if (value == 0) {
						value = Math.abs(value);
					}
					m.setElement(y, z, value);
				}
			}
		}
		u = gaussianElim.get(gaussianElim.size()-1);
		for(int x = gaussianElim.size()-2; x>=0; x--)
		{
			u = u.multiply(gaussianElim.get(x));
		}
		u = u.multiply(a);
		this.l = l;
		Vector y = this.solveLU(l, b);
		Vector x = this.solveQR(u, y);
		this.lUDecompY = y;
		luX = x;
		return luX;
	}

	/**
	 * Solves Ax=b using Givens matrices
	 * @return The solution x
	 */
	public Vector givens() {
		ArrayList<Matrix> givens = new ArrayList<Matrix>();
		Matrix currA = new Matrix(a);
		Matrix r;

		for (int j = 0; j < a.getNumColumns() - 1; j++) {
			for (int i = a.getNumRows() - 1; i > j; i--) {
				if (a.getElement(i, j) != 0) {
					Matrix given = this.makeGivensMatrix(a.getNumColumns(), i,
							j, currA);
					currA = given.multiply(currA);
					givens.add(given);
				}
				if(a.getNumColumns() ==2&&givens.size()==0)
				{
					givens.add(a);
				}
			}
		}
		r = new Matrix(currA);
		Matrix q = givens.get(0).transpose();
		for (int i = 1; i < givens.size(); i++) {
			q = q.multiply(givens.get(i).transpose());
		}
		Vector y = q.transpose().vectorMultiply(b);
		givensX = this.solveQR(r, y);
		givensQ = q;
		givensR = r;
		return givensX;
	}

	/**
	 * Solves Ax=b using Householder matrices
	 * @return The solution x
	 */
	public Vector Householder() {
		Matrix householdTemp = a;
		Vector x1 = new Vector(a.getColumn(0));
		int sign = 1;
		if (x1.getElement(0) < 0) {
			sign = -1;
		}
		Vector e = Vector.eVector(x1.getNumElements(), 1);
		Vector v = x1.addition(e.scalarMultiply(sign * x1.magnitude()));
		ArrayList<Matrix> households = new ArrayList<Matrix>();
		for (int y = 0; y < a.getNumColumns() - 1; y++) {
			sign = 1;
			Matrix matrix = v.multiplyTranspose().scalarMultiply(2)
					.scalarDivide(v.magnitude() * v.magnitude());
			Matrix household = Matrix.identity(matrix.getNumColumns())
					.subtract(matrix);
			if (y > 0) {
				household = household.combineIdentity(y);
			}
			households.add(household);
			householdTemp = households.get(households.size() - 1).multiply(
					householdTemp);
			x1 = householdTemp.getColumn(y + 1).subVector(y + 1);

			e = Vector.eVector(x1.getNumElements(), 1);
			if (x1.getElement(0) < 0) {
				sign = -1;
			}
			v = x1.addition(e.scalarMultiply(sign * x1.magnitude()));
		}
		Matrix q = households.get(0);
		Matrix r = a;
		for (int x = 0; x < households.size(); x++) {
			if (x != 0) {
				q = q.multiply(households.get(x));
			}
			r = households.get(x).multiply(r);
		}
		Vector y = q.transpose().vectorMultiply(b);
		householderR = r;
		householderQ = q;
		householderX = solveQR(r,y);
		return householderX;
	}

	/**
	 * Solves Rx = y
	 * @param r The Matrix R
	 * @param y The Vector y
	 * @return The solved Vector x
	 */
	public Vector solveQR(Matrix r, Vector y) {
		double[] xA = new double[y.getNumElements()];
		for (int z = r.getNumRows() - 1; z >= 0; z--) {
			if (z == (r.getNumRows() - 1)) {
				xA[z] = y.getElement(z) / r.getElement(z, z);
			} else {
				double set = 0;
				for (int x = r.getNumColumns() - 1; x > z; x--) {
					
					set = set + r.getElement(z, x) * xA[x];
				}
				set = y.getElement(z) - set;
				xA[z] = set / r.getElement(z, z);
			}
		}
		return new Vector(xA);
	}

	/**
	 * Solves Ly = b
	 * @param l The Matrix L
	 * @param b The vector b
	 * @return The solved vector y
	 */
	public Vector solveLU(Matrix l, Vector b) {
		double[] xA = new double[b.getNumElements()];
		
		xA[0] = b.getElement(0) / l.getElement(0, 0);
		
		for (int z = 1; z < l.getNumColumns(); z++) {
			double set = 0;
				for (int y = 0; y<z; y++) {
					set = set + l.getElement(z, y) * xA[y];
				}
				set = b.getElement(z) - set;
				xA[z] = set / l.getElement(z, z);
			}
		return new Vector(xA);
	}

	/**
	 * Creates a proper givens matrix
	 * @param n The size of the Matrix
	 * @param i The x coordinate of the matrix
	 * @param j The y coordinate of the matrix
	 * @param m The matrix m to get the elements from to make the r
	 * @return The givens matrix
	 */
	public Matrix makeGivensMatrix(int n, int i, int j, Matrix m) {
		if (i < n && j < n && i > 0) {
			Matrix givens = Matrix.identity(n);
			double x = m.getElement(j, j);
			double y = m.getElement(i, j);
			double r = Math.sqrt((Math.pow(x, 2) + Math.pow(y, 2)));
			double s = -y / r;
			double c = x / r;
			givens.setElement(i, j, s);
			givens.setElement(i, i, c);
			givens.setElement(j, i, -s);
			givens.setElement(j, j, c);

			return givens;
		}
		return null;
	}

	/**
	 * Returns the Q found from householder
	 * @return The Q found from householder
	 */
	public Matrix getHouseholderQ() {
		return householderQ;
	}

	/**
	 * Returns the R found from householder
	 * @return The R found from householder
	 */
	public Matrix getHouseholderR() {
		return householderR;
	}

	/**
	 * Returns the R found from givens
	 * @return The R found from givens
	 */
	public Matrix getGivensR() {
		return givensR;
	}

	/**
	 * Returns the Q found from givens
	 * @return The Q found from givens
	 */
	public Matrix getGivensQ() {
		return givensQ;
	}
	
	/**
	 * Returns the L found from LU Decomposition
	 * @return The L found from LU Decomposition
	 */
	public Matrix getL() {
		return l;
	}
	
	/**
	 * Returns the U found from LU Decomposition
	 * @return The U found from LU Decomposition
	 */
	public Matrix getU() {
		return u;
	}
	
	/**
	 * Returns the y found from LU Decomposition
	 * @return The y found from LU Decomposition
	 */
	public Vector getLUDecompy(){
		return lUDecompY;
	}
	
	/**
	 * Returns the Error found from |QR-H| using the
	 * Q and R found from Householder and a as H
	 * @return the Error found from |QR-H| using the
	 * Q and R found from Householder and a as H
	 */
	public double getHouseholderQRHerror()
	{
		double error = householderQ.multiply(householderR).subtract(a).maxValue();
		return error;
	}
	
	/**
	 * Returns the Error found from |Hx-b| using the
	 * x found from Householder and a as H
	 * @return the Error found from |Hx-b| using the
	 * x found from Householder and a as H
	 */
	public double getHouseholderHXerror()
	{
		double error = a.vectorMultiply(householderX).subtract(b).maxValue();
		return error;
	}
	/**
	 * Returns the Error found from |QR-H| using the
	 * Q and R found from Givens and a as H
	 * @return the Error found from |QR-H| using the
	 * Q and R found from Givens and a as H
	 */
	public double getGivensQRHerror()
	{
		double error = givensQ.multiply(givensR).subtract(a).maxValue();
		return error;
	}
	
	/**
	 * Returns the Error found from |Hx-b| using the
	 * x found from Givens and a as H
	 * @return the Error found from |Hx-b| using the
	 * x found from Givens and a as H
	 */
	public double getGivensHXerror()
	{
		double error = a.vectorMultiply(givensX).subtract(b).maxValue();
		return error;
	}
	
	/**
	 * Returns the Error found from |LU-H| using the
	 * L and U found from LU decomposition and a as H
	 * @Return the Error found from |LU-H| using the
	 * L and U found from LU decomposition and a as H
	 */
	public double getLUHerror()
	{
		double error = l.multiply(u).subtract(a).maxValue();
		return error;
	}
	
	/**
	 * Returns the Error found from |Hx-b| using the
	 * x found from LU decomposition and a as H
	 * @return the Error found from |Hx-b| using the
	 * x found from LU decomposition and a as H
	 */
	public double getLUHXerror()
	{
		double error = a.vectorMultiply(luX).subtract(b).maxValue();
		return error;
	}
}
