package functionParser;


final class ArcTanOp extends FunctionOp{
	ArcTanOp(PolyEvaluator pev){
	super(pev);}
	
	public final void doOp(){
		output.num = Math.atan(pe.evaluate());}
}