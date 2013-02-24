package functionParser;



abstract class Operation extends Object{
	public Doub leftOperand = new Doub();
	public Doub rightOperand = new Doub();
	public Doub output = new Doub();
	public abstract void doOp();
}
