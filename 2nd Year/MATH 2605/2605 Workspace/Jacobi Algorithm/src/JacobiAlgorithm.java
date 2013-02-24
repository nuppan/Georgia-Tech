
import Jama.Matrix;
import Jama.SingularValueDecomposition;

import java.util.ArrayList;
import java.util.Random;


public class JacobiAlgorithm {
	protected Matrix matrix;
	protected SingularValueDecomposition decomp;
	protected ArrayList<Double> offArray;
	protected ArrayList<Double> lnOffArray;
	
	
	public JacobiAlgorithm(){
		Random rand = new Random();
		double matArray[][] = new double[5][5];
		int symElement = rand.nextInt(5);
		for (int y=0;y<5;y++){
			for (int x=0;x<5;x++){
				int randElement = rand.nextInt(5);
				if (x==4 && y==0){
					matArray[x][y] = symElement;
				}
				else if (x==0 && y==4){
					matArray[x][y] = symElement;
				}
				else{
					matArray[x][y] = randElement;
				}
			}
		}
		matrix = new Matrix(matArray);
		decomp = new SingularValueDecomposition(matrix);
		offArray = new ArrayList<Double>();
		lnOffArray = new ArrayList<Double>();
		
	}
	
	private double off(Matrix matrix){
		double off = 0;
		for (int y=0;y<5;y++){
			for (int x=0;x<5;x++){
				if (x!=y){
					off = off + Math.pow(matrix.get(x,y),2);
				}
			}
		}
		return off;
	}
	
	public Matrix aPrime(){
		Matrix aCurrent = decomp.getU().times(matrix).times(decomp.getU().transpose());
		offArray.add(off(aCurrent));
		while (off(aCurrent)>Math.pow(10, -9)){
			SingularValueDecomposition decompTemp = new SingularValueDecomposition(aCurrent);
			aCurrent = decompTemp.getU().times(aCurrent).times(decompTemp.getU().transpose());
			offArray.add(off(aCurrent));
			lnOffArray.add(Math.log(off(aCurrent)));
			System.out.println(off(aCurrent));
		}
		return aCurrent;
	}
}
