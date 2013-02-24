package functionParser;

		
final class HevOp extends FunctionOp{
	HevOp(PolyEvaluator pev){
	super(pev);}
	
	public final void doOp(){
		if(pe.evaluate() < 0.0){
		output.num = 0.0;
		}else{output.num = 1.0;}
		}
}
