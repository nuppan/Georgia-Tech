/**
 * @author Westley Schrack
 */
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class Matrix {
	private Vector[] matrix;
	private int numRows;
	private int numColumns;

	/**
	 * Takes in an array of vectors that can be row or column vectors and
	 * creates a Matrix out of them, whether the vectors are row or column is
	 * determined by the boolean columns
	 * 
	 * @param matrix
	 *            The array of vectors to form the matrix
	 * @param columns
	 *            True if the vectors are column, false if the vectors are rows
	 */
	public Matrix(Vector[] matrix, boolean columns) {
		if (columns) {
			this.matrix = matrix;
			numRows = matrix[0].getNumElements();
			numColumns = matrix.length;
		} else {
			Matrix m = new Matrix(matrix, true);
			Matrix p = m.transpose();
			this.matrix = p.getMatrix();
			numRows = p.getNumRows();
			numColumns = p.getNumColumns();
		}
	}

	/**
	 * Takes in a matrix object and creates a new creates a new matrix
	 * 
	 * @param m
	 *            The Matrix to be used
	 */
	public Matrix(Matrix m) {
		this.matrix = m.getMatrix();
		numRows = this.matrix[0].getNumElements();
		numColumns = this.matrix.length;
	}

	/**
	 * Multiplies the current matrix by the matrix given in the parameters
	 * assuming the number of columns in the current matrix equal the number of
	 * rows in the given matrix
	 * 
	 * @param b
	 *            The matrix to be multiplied
	 * @return The multiplied resulting matrix
	 */
	public Matrix multiply(Matrix b) {
		if (this.numColumns == b.getNumRows()) {
			if (b.getNumColumns() == 1) {
				Vector v = this.vectorMultiply(b.getColumn(0));
				Vector[] vArray = new Vector[1];
				vArray[0] = v;
				return new Matrix(vArray, true);
			}
			double[][] c = new double[b.getNumRows()][b.getNumColumns()];
			int x = 0;
			int y = 0;
			double sum = 0;
			for (int i = 0; i < this.getNumColumns(); i++) {
				for (int j = 0; j < b.getNumColumns(); j++) {
					for (int k = 0; k < b.getNumColumns(); k++) {
						sum += (this.getElement(i, k) * b.getElement(k, j));
					}
					if (sum == 0) {
						c[x][y] = 0;
					} else {
						c[x][y] = sum;
					}
					sum = 0;
					x++;
				}
				sum = 0;
				y++;
				x = 0;
			}
			return doubleToMatrix(c);
		}
		return null;
	}

	/**
	 * Subtracts this matrix from the inputed matrix b
	 * 
	 * @param b
	 *            The matrix to be subtracted
	 * @return The resulting subtraction matrix
	 */
	public Matrix subtract(Matrix b) {
		if (this.numColumns == b.getNumColumns()
				&& this.numRows == b.getNumRows()) {
			double[][] c = new double[this.getNumRows()][this.getNumColumns()];
			for (int i = 0; i < this.getNumRows(); i++) {
				for (int j = 0; j < this.getNumColumns(); j++) {
					c[i][j] = this.getElement(i, j) - b.getElement(i, j);
				}
			}
			return doubleToMatrix(c);
		}
		return null;
	}

	/**
	 * Divides each value in the matrix by a scalar value and returns the
	 * resulting matrix
	 * 
	 * @param v
	 *            The scalar value
	 * @return The current matrix divided by the scalar value
	 */
	public Matrix scalarDivide(double v) {
		double[][] c = new double[this.getNumRows()][this.getNumColumns()];
		for (int i = 0; i < this.getNumRows(); i++) {
			for (int j = 0; j < this.getNumColumns(); j++) {
				c[i][j] = this.getElement(i, j) / v;
			}
		}
		return doubleToMatrix(c);
	}

	/**
	 * Multiplies each value in the matrix by a scalar value and returns the
	 * resulting matrix
	 * 
	 * @param v
	 *            The scalar value
	 * @return The current matrix multiplied by the scalar value
	 */
	public Matrix scalarMultiply(double v) {
		double[][] c = new double[this.getNumRows()][this.getNumColumns()];
		for (int i = 0; i < this.getNumRows(); i++) {
			for (int j = 0; j < this.getNumColumns(); j++) {
				c[i][j] = this.getElement(i, j) * v;
			}
		}
		return doubleToMatrix(c);
	}

	/**
	 * Returns the transpose matrix of the current matrix
	 * 
	 * @return The transposed matrix
	 */
	public Matrix transpose() {
		if (numRows == numColumns) {
			double[][] matrix = new double[this.numRows][this.numColumns];
			for (int x = 0; x < numColumns; x++) {
				Vector v = this.getColumn(x);
				for (int y = 0; y < v.getNumElements(); y++) {
					matrix[y][x] = v.getElement(y);
				}
			}
			return doubleToMatrix(matrix);
		}
		return null;
	}

	/**
	 * Creates an nxn identity matrix corresponding to the parameter
	 * 
	 * @param num
	 *            The size of the matrix
	 * @return The identity matrix
	 */
	public static Matrix identity(int num) {
		double[][] matrix = new double[num][num];
		for (int x = 0; x < num; x++) {
			for (int y = 0; y < num; y++) {
				if (x == y) {
					matrix[x][y] = 1;
				}
			}
		}
		return dToMatrix(matrix);
	}

	/**
	 * Takes in a string array of equations and creates a matrix with them, (ask
	 * me more if you need to use this)
	 * 
	 * @param ls
	 *            The string array of systems of equations
	 * @return The matrix of linear systems
	 */
	public static Matrix linearSysToMatrix(String[] ls) {
		ArrayList<String> variable = new ArrayList<String>();
		ArrayList<ArrayList<String>> values = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> valuesNoNum = new ArrayList<ArrayList<String>>();
		for (int x = 0; x < ls.length; x++) {
			ls[x] = ls[x].replace(" ", "");
			String[] token = ls[x].split("(?=\\b[+-])");
			values.add(new ArrayList<String>());
			valuesNoNum.add(new ArrayList<String>());
			for (int y = 0; y < token.length; y++) {
				if (!variable
						.contains(token[y].substring(token[y].length() - 1))) {
					variable.add(token[y].substring(token[y].length() - 1));
				}
				values.get(x).add(token[y].substring(0, token[y].length() - 1));
				valuesNoNum.get(x).add(
						token[y].substring(token[y].length() - 1));
			}
		}
		for (int x = 0; x < valuesNoNum.size(); x++) {
			int size = valuesNoNum.get(x).size();
			for (int y = 0; y < (variable.size() - size); y++) {
				valuesNoNum.get(x).add(" ");
				values.get(x).add(" ");
			}
		}
		Collections.sort(variable);
		double[][] matrix = new double[variable.size()][ls.length];
		for (int x = 0; x < ls.length; x++) {
			for (int y = 0; y < variable.size(); y++) {
				if (!valuesNoNum.get(x).get(y).equals(" ")) {
					int index = variable.lastIndexOf(valuesNoNum.get(x).get(y));
					matrix[index][x] = Double.parseDouble(values.get(x).get(y));
				}
			}
		}
		return dToMatrix(matrix);
	}

	/**
	 * Creates a Hilbert matrix
	 * 
	 * @return The Hilbert matrix
	 */
	public static Matrix makeHilbert(int n) {
		// Hij = 1/(i+j-1) i = 1,...,n j = 1,...,n
		double[][] matrix = new double[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				matrix[i][j] = (1 / (double) (1 + i + j));
			}
		return dToMatrix(matrix);
	}

	/**
	 * Makes a kernel, but doesn't actually solve it (I don't recommend using
	 * this, it works for what I have tried but I am not completely sure it
	 * works for everything)
	 * 
	 * @return The kernel matrix
	 */
	public Matrix kernel() {
		Matrix m = new Matrix(this);
		double factor = 0;
		for (int x = 1; x < m.getNumRows(); x++) {
			for (int y = x; y < m.getNumRows(); y++) {
				if (m.getElement(y, x - 1) != 0) {
					factor = m.getElement(y, x - 1)
							/ m.getElement(x - 1, x - 1);
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
		return m;
	}

	/**
	 * Returns the number corresponding to the length of the largest integer in
	 * the matrix
	 * 
	 * @return The length of the largest integer in the matrix
	 */
	public int maxIntLength() {
		int maxLength = 0;
		for (int x = 0; x < getNumRows(); x++)
			for (int y = 0; y < getNumColumns(); y++) {
				int current = Integer.toString((int) this.getElement(x, y))
						.length();
				if (current > maxLength) {
					maxLength = current;
				}
			}
		return maxLength;
	}

	/**
	 * Returns the number of rows
	 * 
	 * @return The number of rows
	 */
	public int getNumRows() {
		return numRows;
	}

	/**
	 * Returns the number of columns
	 * 
	 * @return The number of columns
	 */
	public int getNumColumns() {
		return numColumns;
	}

	/**
	 * Return the columns of a zero indexed number
	 * 
	 * @param column
	 *            The column desired
	 * @return The column desired
	 */
	public Vector getColumn(int column) {
		return matrix[column];
	}

	/**
	 * Returns the rows of a zero indexed matrix
	 * 
	 * @param row
	 *            The row desired
	 * @return The row desired
	 */
	public Vector getRow(int row) {
		double[] rowV = new double[numColumns];
		for (int x = 0; x < numColumns; x++) {
			rowV[x] = getElement(row, x);
		}
		return new Vector(rowV);
	}

	/**
	 * Inserts an element at the given position by replace its current value
	 * 
	 * @param i
	 *            The row
	 * @param j
	 *            The column
	 * @param x
	 *            The value to be inserted
	 */
	public void insertElement(int i, int j, double x) {
		this.setElement(i, j, x);
	}

	/**
	 * Computes the determinant of only a 2x2 matrix
	 * 
	 * @param m
	 *            A 2x2 matrix
	 * @return The determinant of a 2x2 matrix
	 */
	public double determinant2D() {
		if (this.getNumColumns() == 2 && this.getNumRows() == 2) {
			double det = (this.getElement(0, 0) * this.getElement(1, 1) - this
					.getElement(0, 1) * this.getElement(1, 0));
			return det;
		}
		return 0;
	}

	/**
	 * Returns the matrix in the form of Vector arrays
	 * 
	 * @return The matrix in the form of Vector arrays
	 */
	public Vector[] getMatrix() {
		Vector[] matrix = new Vector[getNumColumns()];
		for (int x = 0; x < getNumColumns(); x++) {
			Vector v = new Vector(this.getColumn(x));
			matrix[x] = v;
		}
		return matrix;
	}

	/**
	 * Returns the matrix in the form of a double 2D array
	 * 
	 * @return The matrix in the form of a double 2D array
	 */
	public double[][] getMatrix2D() {
		double[][] matrix2D = new double[numRows][numColumns];
		for (int x = 0; x < numRows; x++)
			for (int y = 0; y < numColumns; y++) {
				matrix2D[x][y] = getElement(x, y);
			}
		return matrix2D;
	}

	/**
	 * Converts a 2d double array to a matrix
	 * 
	 * @param matrix
	 *            The 2d double array to be converted to an array matrix
	 * @return The matrix of the 2d double array
	 */
	private Matrix doubleToMatrix(double[][] matrix) {
		Vector[] matrixA = new Vector[matrix.length];
		for (int x = 0; x < matrix.length; x++) {
			double[] vector = new double[matrix[0].length];
			for (int y = 0; y < matrix[x].length; y++) {
				vector[y] = matrix[x][y];
			}
			Vector v1 = new Vector(vector);
			matrixA[x] = v1;
		}
		return new Matrix(matrixA, true);
	}

	/**
	 * Multiplies a matrix by a vector and returns a vector if the matrix has
	 * the same number of columns as the vectors number of rows
	 * 
	 * @param v
	 *            The vector the multiply by
	 * @return The vector result
	 */
	public Vector vectorMultiply(Vector v) {
		double[] vM = new double[v.getNumElements()];
		int x = 0;
		double sum = 0;
		for (int i = 0; i < this.getNumColumns(); i++) {
			for (int j = 0; j < v.getNumElements(); j++) {
				sum += (this.getElement(i, j) * v.getElement(j));
			}
			if (sum == 0) {
				vM[x] = 0;
			}
			vM[x] = sum;
			sum = 0;
			x++;
		}
		sum = 0;
		x = 0;
		return new Vector(vM);
	}

	/**
	 * Combines this matrix with an identity matrix(used for householder)
	 * 
	 * @param rcToAdd
	 *            The number of identity 1s to add to the matrix
	 * @return A householder matrix
	 */
	public Matrix combineIdentity(int rcToAdd) {
		double[][] matrix = new double[rcToAdd + this.getNumRows()][rcToAdd
				+ this.getNumColumns()];
		int i = 0;
		int j = 0;
		for (int x = 0; x < (rcToAdd + this.getNumRows()); x++) {
			for (int y = 0; y < (rcToAdd + this.getNumColumns()); y++) {
				if (x == y && x < rcToAdd) {
					matrix[x][y] = 1;
				} else if (y >= rcToAdd && x >= rcToAdd) {
					matrix[x][y] = this.getElement(i, j);
					j++;
				}

			}
			if (x >= rcToAdd) {
				i++;
			}
			j = 0;
		}
		return dToMatrix(matrix);
	}

	/**
	 * Converts a double 2d array to a matrix object
	 * 
	 * @param matrix
	 *            The 2d array to be converted
	 * @return The Matrix formed from the 2D array
	 */
	public static Matrix dToMatrix(double[][] matrix) {
		Vector[] matrixA = new Vector[matrix.length];
		for (int x = 0; x < matrix.length; x++) {
			double[] vector = new double[matrix[0].length];
			for (int y = 0; y < matrix[x].length; y++) {
				vector[y] = matrix[x][y];
			}
			Vector v1 = new Vector(vector);
			matrixA[x] = v1;
		}
		return new Matrix(matrixA, true);
	}

	/**
	 * Returns the element at row,col
	 * 
	 * @param row
	 *            The row needed
	 * @param col
	 *            The column needed
	 * @return
	 */
	public double getElement(int row, int col) {
		return matrix[col].getElement(row);
	}
	
	/**
	 * Finds the max value in a Matrix
	 * @return the max value in a Matrix
	 */
	public double maxValue()
	{
		double max = 0; 
		for(int x = 0; x<this.getNumColumns();x++)
		{
			if(this.getColumn(x).maxValue()>max)
			{
				max = this.getColumn(x).maxValue();
			}
		}
		return max;
	}
	/**
	 * Finds the inverse of a 2D matrix
	 * @return The inverse of a 2D matrix
	 */
	public Matrix inverse2D()
	{
		double[][] invArray = new double[2][2];
		double det = this.determinant2D();
		invArray[0][0] = this.getElement(1,1)/det;
		invArray[0][1] = -this.getElement(0, 1)/det;
		invArray[1][0] = -this.getElement(1,0)/det;
		invArray[1][1] = this.getElement(0, 0)/det;		
		return this.doubleToMatrix(invArray);
	}

	/**
	 * Replaces the current element with the element in the parameters at the
	 * row and col at the parameters
	 * 
	 * @param row
	 *            The row the replacement is on
	 * @param col
	 *            The column the replacement is on
	 * @param value
	 *            The value to be used for replacing
	 */
	public void setElement(int row, int col, double value) {
		matrix[col].setElement(row, value);
	}

	/**
	 * Returns a string of the matrix formatted and evenly spaced to the 5th
	 * decimal place Rounds to 5th decimal place, in order for the string to be
	 * Readable
	 * 
	 * @return the String representation of the matrix
	 */
	public String toString() {
		DecimalFormat twoDForm = new DecimalFormat("#.#######");
		String matrix = "";
		String spacer = "              ";
		if (this.getElement(0, 0) >= 0) {
			matrix += " ";
		}
		double rounded = 0;
		for (int x = 0; x < this.numRows; x++) {
			for (int y = 0; y < this.numColumns; y++) {
				
				double d = this.getElement(x, y);
				rounded = Double.valueOf(twoDForm.format(d));
				if (Math.abs(rounded) == 0) {
					rounded = Math.abs(rounded);
				}
				for (int i = 0; i < Double.toString(rounded).length(); i++) {
					if (spacer.length() >= 1)
						spacer = spacer.substring(1);
					else
						spacer = "";
				}
				if (rounded >= 0)
				{
					matrix = matrix +" ";
					if(y+1<this.getNumColumns()&&this.getElement(x, y+1)<0)
					{
					}
					else
					spacer = spacer.substring(1);
				}
				if(y+1 == this.getNumColumns())
				{
					matrix = matrix + rounded;
				}
				else
				matrix = matrix + rounded + spacer;
				spacer = "              ";
			}

			matrix = matrix + "\n";
			
		}
		return matrix;
	}
}