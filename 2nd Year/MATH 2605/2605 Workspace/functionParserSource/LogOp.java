package functionParser;


final class LogOp extends FunctionOp{
	LogOp(PolyEvaluator pev){
	super(pev);}
	
	public final void doOp(){
		output.num = Math.log(Math.abs(pe.evaluate()));}
}