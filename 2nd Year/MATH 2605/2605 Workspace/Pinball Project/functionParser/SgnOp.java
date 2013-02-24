package functionParser;

		
final class SgnOp extends FunctionOp{
	SgnOp(PolyEvaluator pev){
	super(pev);}
	
	public final void doOp(){
		if(pe.evaluate() > 0.0){
		output.num = 1.0;
		}else if(pe.evaluate() < 0.0){
		output.num = -1.0;}
		else{output.num = 0.0;}
		}
}
