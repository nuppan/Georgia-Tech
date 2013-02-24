package functionParser;


final class ArcCosOp extends FunctionOp{
	ArcCosOp(PolyEvaluator pev){
	super(pev);}
	
	public final void doOp(){
		output.num = Math.acos(pe.evaluate());}
}