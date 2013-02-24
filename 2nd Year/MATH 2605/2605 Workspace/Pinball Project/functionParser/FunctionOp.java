package functionParser;

abstract class FunctionOp extends Object{
	public Doub output = new Doub();
	PolyEvaluator pe;
	
	FunctionOp(PolyEvaluator pev){
	pe = pev;
	}//END OF CONSTRUCTOR
	public abstract void doOp();
}
