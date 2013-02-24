/**
 * @author Westley Schrack
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ChartUtilities;
import java.util.Random;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;

public class Driver {
	public static void main(String[] args) {
		ArrayList<Vector> LUDecomp = new ArrayList<Vector>();
		ArrayList<Vector> givens = new ArrayList<Vector>();
		ArrayList<Matrix> givensQ = new ArrayList<Matrix>();
		ArrayList<Matrix> givensR = new ArrayList<Matrix>();
		ArrayList<Vector> householder = new ArrayList<Vector>();
		ArrayList<Matrix> householderQ = new ArrayList<Matrix>();
		ArrayList<Matrix> householderR = new ArrayList<Matrix>();
		ArrayList<Matrix> l = new ArrayList<Matrix>();
		ArrayList<Matrix> u = new ArrayList<Matrix>();
		ArrayList<Matrix> hilbertAll = new ArrayList<Matrix>();
		ArrayList<Double> householderQRHerror = new ArrayList<Double>();
		ArrayList<Double> householderHXerror = new ArrayList<Double>();
		ArrayList<Double> givensQRHerror = new ArrayList<Double>();
		ArrayList<Double> givensHXerror = new ArrayList<Double>();
		ArrayList<Double> luHerror = new ArrayList<Double>();
		ArrayList<Double> luHXberror = new ArrayList<Double>();
		ArrayList<Vector> luDecompy = new ArrayList<Vector>();
		for (int x = 2; x <= 20; x++) {
			Matrix hilbert = Matrix.makeHilbert(x);
			hilbertAll.add(hilbert);
			Vector b = Vector.makeB(x);
			MatrixMath math = new MatrixMath(hilbert,b);
			LUDecomp.add(math.LUDecomp());
			l.add(math.getL());
			u.add(math.getU());
			luDecompy.add(math.getLUDecompy());
			luHerror.add(math.getLUHerror());
			luHXberror.add(math.getLUHXerror());
			givens.add(math.givens());
			givensQ.add(math.getGivensQ());
			givensR.add(math.getGivensR());
			householder.add(math.Householder());
			householderQ.add(math.getHouseholderQ());
			householderR.add(math.getHouseholderR());
			householderQRHerror.add(math.getHouseholderQRHerror());
			householderHXerror.add(math.getHouseholderHXerror());
			givensQRHerror.add(math.getGivensQRHerror());
			givensHXerror.add(math.getGivensHXerror());
		}
		
		String fileName = "DriverOutput.txt";
		File output = new File(fileName);
		try {
			Writer out = new BufferedWriter(new FileWriter(output));
			for(int x = 0; x<householder.size(); x++)
			{
				out.write("Hilbert Matrix for n = "+ (x+2)+"\n\n");
				out.write(hilbertAll.get(x)+"\n\n");
				
				out.write("LU Decomposition Method Solving for Ax = b: \n\n");
				out.write("L: \n" + l.get(x)+"\n\n");
				out.write("U: \n" + u.get(x)+"\n\n");
				out.write("x: \n" + LUDecomp.get(x) + "\n\n");
				out.write("||LU-H|| Error: "+ luHerror.get(x)+" for n = " + (x+2)+" \n");
				out.write("||Hx-b|| Error: "+ luHXberror.get(x)+" for n = " + (x+2)+" \n\n");
				
				out.write("Householder Method Solving for Ax = b: \n\n");
				out.write("Q: \n" + householderQ.get(x)+"\n\n");
				out.write("R: \n" + householderR.get(x)+"\n\n");
				out.write("x: \n" + householder.get(x) + "\n\n");
				out.write("||QR-H|| Error: "+ householderQRHerror.get(x)+" for n = " + (x+2)+" \n");
				out.write("||Hx-b|| Error: "+ householderHXerror.get(x)+" for n = " + (x+2)+" \n\n");
				
				out.write("Givens Method Solving for Ax = b: \n\n");
				out.write("Q: \n" + givensQ.get(x)+"\n");
				out.write("R: \n" + givensR.get(x)+"\n");
				out.write("x: \n" + givens.get(x) + "\n\n");
				out.write("||QR-H|| Error: "+ givensQRHerror.get(x)+" for n = " + (x+2)+" \n");
				out.write("||Hx-b|| Error: "+ givensHXerror.get(x)+" for n = " + (x+2)+" \n");
				out.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fileName = "ErrorOutput.txt";
		output = new File(fileName);
		try {
			Writer out = new BufferedWriter(new FileWriter(output));
			for(int x = 0; x<householder.size(); x++)
			{
				out.write("Hilbert Matrix for n = "+ (x+2)+"\n\n");
				
				out.write("LU Decomposition Method Solving for Ax = b: \n\n");
				out.write("||LU-H|| Error: "+ luHerror.get(x)+" for n = " + (x+2)+" \n");
				out.write("||Hx-b|| Error: "+ luHXberror.get(x)+" for n = " + (x+2)+" \n\n");
				
				out.write("Householder Method Solving for Ax = b: \n\n");
				out.write("||QR-H|| Error: "+ householderQRHerror.get(x)+" for n = " + (x+2)+" \n");
				out.write("||Hx-b|| Error: "+ householderHXerror.get(x)+" for n = " + (x+2)+" \n\n");
				
				out.write("Givens Method Solving for Ax = b: \n\n");
				out.write("||QR-H|| Error: "+ givensQRHerror.get(x)+" for n = " + (x+2)+" \n");
				out.write("||Hx-b|| Error: "+ givensHXerror.get(x)+" for n = " + (x+2)+" \n");	
				out.flush();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		//ScatterRenderer scatterHilbert = new ScatterRenderer();
		XYSeries luherrorSeries = new XYSeries("LU Decomposition: ||LU-H|| Error");
		XYSeries luhxberrorSeries = new XYSeries("LU Decomposition: ||Hx-b|| Error");
		XYSeries householderqrhSeries = new XYSeries("Householder: ||QR-H|| Error");
		XYSeries householderhxbSeries = new XYSeries("Householder: ||HX-b|| Error");
		XYSeries givensqrhSeries = new XYSeries("Givens: ||QR-H|| Error");
		XYSeries givenshxbSeries = new XYSeries("Givens: ||HX-b|| Error");
		for(int x = 0; x<householder.size(); x++)
		{
			luherrorSeries.add(x+2, luHerror.get(x));
			luhxberrorSeries.add(x+2,luHXberror.get(x));
			householderqrhSeries.add(x+2, householderQRHerror.get(x));
			householderhxbSeries.add(x+2, householderHXerror.get(x));
			givensqrhSeries.add(x+2, givensQRHerror.get(x));
			givenshxbSeries.add(x+2, givensHXerror.get(x));
		}
		XYSeriesCollection hilbertDataset = new XYSeriesCollection();
		hilbertDataset.addSeries(luherrorSeries);
		hilbertDataset.addSeries(luhxberrorSeries);
		hilbertDataset.addSeries(householderqrhSeries);
		hilbertDataset.addSeries(householderhxbSeries);
		hilbertDataset.addSeries(givensqrhSeries);
		hilbertDataset.addSeries(givenshxbSeries);
		
		JFreeChart hilbertChart = ChartFactory.createScatterPlot("Hilbert Error", "n", "Error", hilbertDataset, PlotOrientation.VERTICAL, true, true, true);
		ValueAxis axis = hilbertChart.getXYPlot().getDomainAxis();
		((NumberAxis) axis).setTickUnit(new NumberTickUnit(1));
		axis.setRange(2,20);
		try {
			ChartUtilities.saveChartAsPNG(new File("hilbertErrorChart.png"), hilbertChart, 1980, 1080);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Random r = new Random();
		ArrayList<Matrix> powerMatrix = new ArrayList<Matrix>();
		for(int x = 1; x<1000; x++)
		{
			double[][] power = new double[2][2];
			for(int y = 0; y<2; y++)
				for(int z=0; z<2; z++)
				{
					double m = r.nextInt(8) -4;
					power[y][z] = m;
				}
			powerMatrix.add(Matrix.dToMatrix(power));
		}
		ArrayList<XYSeries> powerMethod = new ArrayList<XYSeries>();
		ArrayList<XYSeries> invPowerMethod = new ArrayList<XYSeries>();
		ArrayList<ArrayList<Double>> domEigenPower = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> domEigenInv = new ArrayList<ArrayList<Double>>();
		
		for(int x = 0; x<10; x++)
		{
			powerMethod.add(new XYSeries("Iteration "+(x+1)));
			invPowerMethod.add(new XYSeries("Iteration " + (x+1)));
			domEigenPower.add(new ArrayList<Double>());
			domEigenInv.add(new ArrayList<Double>());
		}
	
		PowerMethod pm = new PowerMethod();
		for(int x = 0; x<powerMatrix.size(); x++)
		{
			int powIteration = (pm.powerMethod(powerMatrix.get(x))-1); 
			
			double trace = powerMatrix.get(x).getElement(0, 0)+ powerMatrix.get(x).getElement(1,1);
			domEigenPower.get(powIteration).add(pm.getDominantEigen());
			
			powerMethod.get(powIteration).add(powerMatrix.get(x).determinant2D(), trace);
			
			int invIteration = (pm.invPowerMethod(powerMatrix.get(x))-1);
			domEigenInv.get(invIteration).add(pm.getDominantEigen());
			invPowerMethod.get(invIteration).add(powerMatrix.get(x).determinant2D(), trace);
		}
		
		XYSeriesCollection powerCollection = new XYSeriesCollection();
		XYSeriesCollection invPowerCollection = new XYSeriesCollection();
		for(int x = 0; x<powerMethod.size(); x++)
		{
			invPowerCollection.addSeries(invPowerMethod.get(x));
			powerCollection.addSeries(powerMethod.get(x));
		}
		
		JFreeChart powerMethodChart = ChartFactory.createScatterPlot("Power Method", "Determinant", "Trace", powerCollection, PlotOrientation.VERTICAL, true, true, false);
		axis = powerMethodChart.getXYPlot().getRangeAxis();
		((NumberAxis) axis).setTickUnit(new NumberTickUnit(.5));
		axis.setRange(-8,8);
		JFreeChart invPowerMethodChart = ChartFactory.createScatterPlot("Inverse Power Method", "Determinant", "Trace", invPowerCollection, PlotOrientation.VERTICAL, true, true, false);		
		axis = invPowerMethodChart.getXYPlot().getRangeAxis();
		((NumberAxis) axis).setTickUnit(new NumberTickUnit(.5));
		axis.setRange(-8,8);
		try {
			//ChartUtilities.saveChartAsPNG(new File("powerMethodChart.png"), powerMethodChart, 1980, 1080);
			ChartUtilities.saveChartAsPNG(new File("invPowerMethodChart.png"), invPowerMethodChart, 1980, 1080);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
