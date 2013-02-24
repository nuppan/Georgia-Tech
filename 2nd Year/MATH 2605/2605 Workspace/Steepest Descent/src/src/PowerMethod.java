/**
 * @author Westley Schrack
 */
import java.text.DecimalFormat;
import java.util.ArrayList;

public class PowerMethod {

	private double dominantEigen = 0;

	/**
	 * Creates a PowerMethod object
	 */
	public PowerMethod() {
	}

	/**
	 * Solves for the eigenvalue using the power method
	 * @param a The matrix a to solve
	 * @return The number of iterations taken to find the convergence
	 * 10 iterations means the number does not converge
	 */
	public int powerMethod(Matrix a) {
		double[] b1 = {1, 1};
		Vector b = new Vector(b1);
		int maxIterations = 10;
		ArrayList<Vector> bk = new ArrayList<Vector>();
		bk.add(b);
		int iterationsTaken = 0;
		DecimalFormat twoDForm = new DecimalFormat("#.#####");
		for (int x = 0; x < maxIterations-1; x++) {
			Vector bk1;
			bk1 = a.vectorMultiply(bk.get(x)).unitVector();
			bk.add(bk1);
			iterationsTaken++;
			double posBKEigen = bk.get(bk.size() - 2)
					.scalarMultiply((1 / bk.get(bk.size() - 2).getElement(1)))
					.getElement(0);
			double posBK1Eigen = bk.get(bk.size() - 1)
					.scalarMultiply((1 / bk.get(bk.size() - 1).getElement(1)))
					.getElement(0);
			try{
			if (!new Double(posBKEigen).equals(Double.NaN)
							&& !new Double(posBK1Eigen).equals(Double.NaN)) {
			
				if (Double.valueOf(twoDForm.format(posBKEigen)).equals(
					Double.valueOf(twoDForm.format(posBK1Eigen)))) {
				this.dominantEigen = a.vectorMultiply(bk1).dotProduct(bk1)
						/ (bk1.dotProduct(bk1));
				return iterationsTaken;
			}
			}
			}
			catch(Exception e)
			{}
					}
		return maxIterations;
	}

	/**
	 * Solves for the eigenvalue using the inverse power method
	 * @param a The matrix a to solve
	 * @return The number of iterations taken to find the convergence
	 * 10 iterations means the number does not converge
	 */
	public int invPowerMethod(Matrix a) {
		int maxIterations = 10;
		int iterationsTaken = 0;
		DecimalFormat twoDForm = new DecimalFormat("#.#####");
		double[] u = { 1, 1 };
		Vector uk = new Vector(u);
		ArrayList<Vector> uS = new ArrayList<Vector>();
		uS.add(uk);
		for (int x = 0; x < maxIterations-1; x++) {
			iterationsTaken++;
			Vector uk1 = solve2DQR(a.inverse2D(), uS.get(x));
			uS.add(uk1);
			double posBKEigen = uS.get(uS.size() - 2)
					.scalarMultiply((1 / uS.get(uS.size() - 2).getElement(1)))
					.getElement(0);
			double posBK1Eigen = uS.get(uS.size() - 1)
					.scalarMultiply((1 / uS.get(uS.size() - 1).getElement(1)))
					.getElement(0);
			try{
			if (!new Double(posBKEigen).equals(Double.NaN)
					&& !new Double(posBK1Eigen).equals(Double.NaN)) {
				
				if (Double.valueOf(twoDForm.format(posBKEigen)).equals(
						Double.valueOf(twoDForm.format(posBK1Eigen)))) {
					this.dominantEigen = a.vectorMultiply(uk1).dotProduct(uk1)
							/ (uk1.dotProduct(uk1));
					return iterationsTaken;
				}
				}
			}
				catch (Exception e)
				{
				}
			}
		return maxIterations;
	}

	/**
	 * Solves QR of a 2D matrix using Givens rotations
	 * @param a The Matrix a of Ax = b
	 * @param u The Vector b of Ax = b
	 * @return
	 */
	public Vector solve2DQR(Matrix a, Vector u) {
		if (a.getNumColumns() == u.getNumElements()) {
			MatrixMath math = new MatrixMath(a,u);
			Vector x = math.Householder();
			return x;
		}
		return null;
	}

	/**
	 * Returns the dominant eigenvalue found from the
	 * most recently used method
	 * @return the dominant eigenvalue found from the
	 * most recently used method
	 */
	public double getDominantEigen() {
		return dominantEigen;
	}
}
