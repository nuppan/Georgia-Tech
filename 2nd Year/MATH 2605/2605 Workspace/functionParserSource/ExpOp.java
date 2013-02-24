package functionParser;


final class ExpOp extends FunctionOp{
	ExpOp(PolyEvaluator pev){
	super(pev);}
	
	public final void doOp(){
		output.num = Math.exp(pe.evaluate());}
}