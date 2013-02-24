package functionParser;


final class CosOp extends FunctionOp{
	CosOp(PolyEvaluator pev){
	super(pev);}
	
	public final void doOp(){
		output.num = Math.cos(pe.evaluate());}
}
