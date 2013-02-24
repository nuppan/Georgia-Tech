/**
 * @author Westley Schrack
 */
public class Vector {
	private double[] vector = null;
	private int numElements = 0;
	private char freeVar;

	/**
	 * Creates a vector from an array of doubles
	 * 
	 * @param vector
	 *            The array to be used to create the array
	 */
	public Vector(double ... vector) {
		this.vector = vector;
		this.numElements = vector.length;
	}

	/**
	 * Creates a vector from an array of doubles
	 * @param freeVar
	 *            A free variable (ie parametrization)
	 * @param vector
	 *            The array to be used to create the array
	 */
	public Vector(char freeVar, double ... vector) {
		this.freeVar = freeVar;
		this.vector = vector;
		this.numElements = vector.length;
	}

	/**
	 * Creates a new Vector from a Vector
	 * 
	 * @param v
	 *            The vector to be created
	 */
	public Vector(Vector v) {
		this.vector = new double[v.getNumElements()];
		for (int x = 0; x < v.getNumElements(); x++) {
			this.vector[x] = v.getElement(x);

		}
		this.numElements = vector.length;
	}

	/**
	 * Creates a new Vector from a Vector
	 * 
	 * @param v
	 *            The vector to be created
	 * @param freeVar
	 *            A free variable (ie parametrization)
	 */
	public Vector(Vector v, char freeVar) {
		this.freeVar = freeVar;
		this.vector = new double[v.getNumElements()];
		for (int x = 0; x < v.getNumElements(); x++) {
			this.vector[x] = v.getElement(x);
		}
		this.numElements = vector.length;
	}

	/**
	 * Returns the number of elements in the vector
	 * 
	 * @return The number of elements in the vector
	 */
	public int getNumElements() {
		return numElements;
	}

	/**
	 * Returns part of a vector(like substring())
	 * 
	 * @param The
	 *            starting point
	 * @return The subvector starting from the n position
	 */
	public Vector subVector(int n) {
		int y = 0;
		double[] v = new double[numElements - n];
		for (int x = n; x < numElements; x++) {
			v[y] = getElement(x);
			y++;
		}
		return new Vector(v);
	}

	/**
	 * Multiplies a vector by its transpose
	 * 
	 * @return The matrix formed
	 */
	public Matrix multiplyTranspose() {
		double[][] matrix = new double[numElements][numElements];
		for (int x = 0; x < numElements; x++)
			for (int y = 0; y < numElements; y++) {
				matrix[x][y] = getElement(x) * getElement(y);
			}
		return Matrix.dToMatrix(matrix);
	}

	/**
	 * Finds the magnitude of the vector
	 * 
	 * @return The magnitude of the vector
	 */
	public double magnitude() {
		double magnitude = 0;
		for (int x = 0; x < numElements; x++) {
			magnitude += Math.pow(vector[x], 2);
		}
		magnitude = Math.sqrt(magnitude);
		return magnitude;
	}
	public double maxValue()
	{
		double max = 0;
		for(int x = 0; x<numElements; x++)
		{
			if(Math.abs(this.getElement(x))>max)
			{
				max = Math.abs(this.getElement(x));
			}
		}
		return max;
	}
	public Vector subtract(Vector b)
	{
		if(this.getNumElements()==b.getNumElements())
		{
		double[] c =new double[this.getNumElements()];
		for(int x = 0; x<this.getNumElements(); x++)
		{
			c[x] = this.getElement(x)-b.getElement(x);
		}
		return new Vector(c);
		}
		return null;
	}

	/**
	 * Returns the element at position x
	 * 
	 * @param x
	 *            The position of the desired element
	 * @return The element at position x
	 */
	public double getElement(int x) {
		return vector[x];
	}

	/**
	 * Replace the element at location x with value
	 * 
	 * @param x
	 *            The position of the element to be replaced
	 * @param value
	 *            The value to replace with
	 */
	public void setElement(int x, double value) {
		vector[x] = value;
	}

	/**
	 * Finds the unit vector of the vector
	 * 
	 * @return The unit vector
	 */
	public Vector unitVector() {
		double norm = this.magnitude();
		double[] unitVector = new double[getNumElements()];
		for (int x = 0; x < getNumElements(); x++) {
			unitVector[x] = getElement(x) * (1 / norm);
		}
		return new Vector(unitVector);
	}

	/**
	 * Adds two vectors of the same size together
	 * 
	 * @param a
	 *            The vector to add
	 * @return The added vector
	 */
	public Vector addition(Vector a) {
		if (getNumElements() == a.getNumElements()) {
			double[] v = new double[a.getNumElements()];
			for (int x = 0; x < a.getNumElements(); x++) {
				v[x] = getElement(x) + a.getElement(x);
			}
			return new Vector(v);
		}
		return null;
	}

	/**
	 * Multiplies the vector by a scalar
	 * 
	 * @param scalar
	 *            The scalar to multiply by
	 * @return The multiplied matrix
	 */
	public Vector scalarMultiply(double scalar) {
		double[] v = new double[getNumElements()];
		for (int x = 0; x < getNumElements(); x++) {
			v[x] = this.getElement(x) * scalar;
		}
		return new Vector(v);

	}
	
	public static Vector makeB(int n)
	{
		double[] b = new double[n];
		for(int x = 0; x<n; x++)
		{
			b[x] = 1;
		}
		return new Vector(b);
	}

	/**
	 * Creates an e vector NOTE: This is not a zero indexed method, a 1 for
	 * position will place the 1 at the first element instead of the second
	 * 
	 * @param length
	 *            The length of the vector
	 * @param position
	 *            The position that 1 is to be placed
	 * @return
	 */
	public static Vector eVector(int length, int position) {
		if (position <= length) {
			double[] v = new double[length];
			v[position - 1] = 1;
			return new Vector(v);
		}
		return null;
	}

	/**
	 * Computes the dot product of two vectors
	 * 
	 * @param b
	 *            The other vector
	 * @return The dot product
	 */
	public double dotProduct(Vector b) {

		double dotProduct = 0;
		if (this.getNumElements() == b.getNumElements()) {
			for (int x = 0; x < getNumElements(); x++) {
				dotProduct += getElement(x) * b.getElement(x);
			}
		}
		return dotProduct;
	}

	/**
	 * Returns a String representation of the vector
	 * 
	 * @return the String representation of the vector
	 */
	public String toString() {
		String toString = "";
		for (int x = 0; x < getNumElements(); x++)
			toString = toString + this.getElement(x) + "\n";
		return toString;
	}

	/**
	 * Sets the free variable of the vector if needed
	 * 
	 * @param variable
	 *            The free variable
	 */
	public void setFreeVariable(char variable) {
		this.freeVar = variable;
	}

	/**
	 * Returns the free variable of the vector
	 * 
	 * @return The free variable of the vector
	 */
	public char getFreeVariable() {
		return freeVar;
	}

	/**
	 * Returns a String representation of the vector transpose
	 * 
	 * @return the String representation of the vector transpose
	 */
	public String toStringTranspose() {
		String toString = "";
		for (int x = 0; x < getNumElements(); x++)
			toString = toString + this.getElement(x) + " ";
		return toString;
	}
}