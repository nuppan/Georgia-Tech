package functionParser;



final class IdOp extends FunctionOp{
	IdOp(PolyEvaluator pev){
	super(pev);}

	public final void doOp(){
		output.num = pe.evaluate();}
}
