package functionParser;

		
final class SinOp extends FunctionOp{
	SinOp(PolyEvaluator pev){
	super(pev);}
	
	public final void doOp(){
		output.num = Math.sin(pe.evaluate());}
}
	
