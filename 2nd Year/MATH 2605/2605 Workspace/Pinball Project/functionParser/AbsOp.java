package functionParser;


final class AbsOp extends FunctionOp{
	AbsOp(PolyEvaluator pev){
	super(pev);}
	
	public final void doOp(){
		output.num = Math.abs(pe.evaluate());}
}