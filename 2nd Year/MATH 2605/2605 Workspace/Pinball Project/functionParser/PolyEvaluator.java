package functionParser;


final class PolyEvaluator extends Object{
	Operation opArray[];
	Doub doubArray[];
	public final double evaluate(){
		for(int j=0;j < opArray.length;j++){
		opArray[j].doOp();}
		return opArray[opArray.length-1].output.num;
		}
}
