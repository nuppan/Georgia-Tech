package functionParser;


final class TanOp extends FunctionOp{
	TanOp(PolyEvaluator pev){
	super(pev);}
	
	public final void doOp(){
		output.num = Math.tan(pe.evaluate());}
}