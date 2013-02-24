package functionParser;


final class ArcSinOp extends FunctionOp{
	ArcSinOp(PolyEvaluator pev){
	super(pev);}
	
	public final void doOp(){
		output.num = Math.asin(pe.evaluate());}
}