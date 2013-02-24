package functionParser;

/** This class contains as private variables an two arrays: One of Doubles, and one
  * of FunctionOp classes. FunctionOp is a private class in this package, and it
  * does a specified mathematical operation on variables -- one or two of them --
  * passed to it, and returns the corresponding output. When an Evaluator is passed
  * to a FunctionParser together with a string to be evaluated through the parse() method 
  * of FunctionParser, the operations are aranged in order, assembly line fashion,
  * with instructions to read their input from the appropriate places in the variable
  * array, and to write their output to the appropriate place. This produces an 
  * efficient mechanism for repeated evaluation of a function represented by a string.
  */
public class Evaluator extends Object{
	         Doub[] variableValues;
	        FunctionOp[] funcOpArray;
	
	/** This constructor takes an integer which tell it how many variables the
	  * Evaluator is to expect. This is to be used with functions of one or two
	  * variables.  That is, the integer passed in should be one or two. 
	  */
	public Evaluator(int varCount){
		variableValues = new Doub[varCount];
		for(int i=0;i<varCount;i++){
			variableValues[i] = new Doub();
		}
		}
		
	/** This method takes two doubles as input which it interprets as the first and
	  * second variables in the variable list of a FunctionParser, and feeds them in as 
	  * input to the "assembly line" set up by the FunctionParser. It should only be invoked
	  * on an Evalutor that has been passed to a FunctionParser created with a 
	  * two variable variable-string through the parse() method of that class. However,
	  * for the sake of speed, no checking of this is built in.
	  * <P>
	  * The method was declared  final for the sake of speed.
	  */
	public final double evaluate(double xx, double yy){
		(variableValues[0]).num = xx;
		(variableValues[1]).num = yy;
		
		for(int j=0;j<funcOpArray.length;j++){
		funcOpArray[j].doOp();
		}
		return funcOpArray[funcOpArray.length-1].output.num;
	}
	
	/** This method takes one double as input which it interprets as the single
	  * variable in the variable list of a FunctionParser, and feeds it in as 
	  * input to the "assembly line" set up by the FunctionParser. This method should 
	  * only be invoked on an Evalutor that has been passed to a FunctionParser created with a 
	  * one variable variable-string through the parse() method of that class. However,
	  * for the sake of speed, no checking of this is built in.
	  * <P>
	  * The method was declared  final for the sake of speed.
	  */
	public final double evaluate(double tt){
		(variableValues[0]).num = tt;
		
		for(int j=0;j<funcOpArray.length;j++){
		funcOpArray[j].doOp();
		}
		return funcOpArray[funcOpArray.length-1].output.num;
	}
}

	
