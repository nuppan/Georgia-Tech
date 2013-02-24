package functionParser;

final class Power extends Operation{
	public final void doOp(){
	output.num = Math.pow(leftOperand.num,rightOperand.num);}
}
