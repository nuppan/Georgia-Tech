package functionParser;

import java.util.*;

/** This class parses a string describing a mathematical function, and produces
  * and encodes a sequence of mathematical operations into an instance of the Evaluator
  * class, also in this package, which may then be used to efficiently make
  * repeated evaluations of the function without reparsing. This class is designed to
  * be used in applets or applications like graphing calculators, into which a 
  * function is entered at runtime, and possibly changed repeatedly, and needs to be
  * quickly evaluated at many arguments.
  */

public class FunctionParser extends Object implements ParsingProblemType{

private StringTokenizer st;
private String[] variableList;


private String delim = "*/+-()^ []";
private int internalVariableCount = 0;
private int bracePairsCount = 0;
private int leftBraceIndex = 0;
private int rightBraceIndex = 0;
private int topIndex = 0;
private	int count = 0;
private int funcOpIndex = 0;

Vector tokenChain;

PolyParser popar;

/** The constructor takes as input a string which is a comma separated list of variable names.
  * The names of variables must begin with a letter, but otherwise can contain
  * numerals. The only other excluded string is "pi" which is reserved for the constant
  * of that name. */
//THE CONSTRUCTOR:
public FunctionParser(String varlist){
st = new StringTokenizer(varlist,", ",false);
variableList = new String[st.countTokens()+1];
for(int p=0;p<variableList.length-1;p++){
variableList[p]= st.nextToken();}
variableList[variableList.length-1] = "pi";
popar = new PolyParser(this);
}//END OF CONSTRUCTOR


public String[] variables(){
	return variableList;}

	
//NOW THE MAIN PUBLIC METHOD parse():

/** This method provides a means of parsing strings describing 
  * mathematical functions so that they may be efficiently evaluated. It
  * takes as input a string, which descibes the function, and an Evaluator, another
  * public class in this package. 
  * It parses the String and codes into
  * the Evaluator an array of mathematical operations, and an array of variables,
  * in such a way that the Evaluator can later repeatedly and efficiently 
  * evaluate the function without further reference to the string originally passed
  * into the FunctionParser. The unique public function of the Evaluator class is
  * in fact "evaluate(double d)". 
  * <P>
  * The main thing to explain about this method then is the syntax and vocabulary
  * that must be used to specify a function in the string passed to the parser.
  * As usual, + - * and / are used to denote addition, subtraction, multiplication
  * and division. The * is necessary for multiplication; juxtaposition does not suffice.
  * Powers are denoted with a ^. That is, x^2 denotes "x squared". The exponent can be
  * any double expressed in decimal form. That is x^2.167 denotes "x to the 2.157th
  * power". Precedence of arithmetic operations follows the usual conventions, but
  * round parentheses (...) can be used to order a specific  precedence explicitly.
  * <P>
  * Certain functions are permitted as well. The arguments of the functions are
  * always denoted by square brackets [...]. For example, cos[x^2 + y] denotes
  * the cosine of x squared plus y. This class is pretty forgiving about names of 
  * functions; it also accepts
  * Cos[x^2 + y]  or COS[x^2 + y] or cosine[x^2 + y] or cosinus[x^2 + y] among
  * other posibilities. Likewise, it accepts both acos[x] and ARCCOS[x] for the
  * arc-cosine of x, aong others. In particular, the parser ignores case in function 
  * names, and just matches the first few charachters against a list to see if
  * it recognizes a string preceding [...] as a valid function name.
  * <P>
  * The recognized functions are: sin, cos, tan, csc, sec, cot, exp, log, abs,
  * asin, acos, atan -- and two simple utility functions: sgn had hev, the sign function
  * and the Heaviside function. The first of these, sgn,
  * evaluates to -1 for a negative argument, +1 for a positive argument, and
  * 0 if the argument is 0. The second of these, hev, evalutate to +1 for an 
  * arrgument that is non-negative, and to 0 otherwise.
  * Finally, abs denotes the absolute value. 
  * <P>
  * If the parser runs into a string that it doesn't recognize as a variable or as
  * a function it throws a FunctionParsingException  exception. It also throws 
  * such an exception if parenthesesor braces are used in an unexpected way, or if 
  * what should be a function name is unrecognized.  The FunctionParsingException
  * can be querried as to the cause of the exception.
  */
public void parse(String enteredSt, Evaluator ev) throws FunctionParsingException{ 
	StringTokenizer st = new StringTokenizer(enteredSt,delim,true);
	tokenChain = new Vector(st.countTokens());
	while(st.hasMoreTokens()){
	tokenChain.addElement(st.nextToken());}
	countBracePairs();
	ev.funcOpArray = new FunctionOp[bracePairsCount+1];
	funcOpIndex=0;
	internalVariableCount = 0;
	//SETUP DONE...
	if(bracePairsCount>0){
	
	for(int i=0;i<bracePairsCount;i++){
		highestPriorityBracePair();
		extractFunctionBlock(ev);
		funcOpIndex++;
		}}
	if(tokenChain.size()>0){
	PolyEvaluator pev = new PolyEvaluator();
	popar.parse(tokenChain,pev,ev);
	ev.funcOpArray[bracePairsCount] = new IdOp(pev);
	}
	
	}//END OF parse()



//THE NEXT METHOD COUNTS BRACE PAIRS AND THROWS AN 
// EXCEPTION IF ANY ARE UNMATCHED, AND SETS bracePairsCount.
//THIS CHECK CAN BE AVOIDED HERE BY SEEING IF currentLevel IS RETURED TO zero AT
//THE END OF highestPriorityBracePair(). 
private void countBracePairs() throws FunctionParsingException{
	int left = 0;
	int right = 0;
	for(int i=0;i<tokenChain.size();i++){
		if(((String)(tokenChain.elementAt(i))).equals("[")){left++;}
		if(((String)(tokenChain.elementAt(i))).equals("]")){right++;}
		if(right>left){throw new FunctionParsingException(RIGHT_BRACKET_UNMATCHED);}
		}
	if(left == right){
	bracePairsCount = left;
	}
	else if(left > right){throw new FunctionParsingException(TOO_MANY_LEFT_BRACKETS);}
	else{throw new FunctionParsingException(TOO_MANY_RIGHT_BRACKETS);}
}

//FIND HIGHEST PRIORITY BRACES PAIR

private void highestPriorityBracePair(){
	count = 0;
	int[] levels = new int[bracePairsCount];
	int[] indexes = new int[bracePairsCount];
	int currentLevel = 0;
	for(int i=0;i<tokenChain.size();i++){
	if(((String)(tokenChain.elementAt(i))).equals("[")){
		currentLevel++;
		levels[count] = currentLevel;
		indexes[count] = i;
		count++;}
	else if(((String)(tokenChain.elementAt(i))).equals("]")){
		currentLevel--;}
	else{;}
	}
	topIndex = levels[0];
	count = 0;
	for(int j=0;j<bracePairsCount;j++){
		if(levels[j]>topIndex){
		topIndex = levels[j];
		count = j;}
		}
	leftBraceIndex = indexes[count];
	//NOW FIND THE CORRESPONDING RIGHT BRACE
	
	int k=topIndex;
	while(!((String)(tokenChain.elementAt(k))).equals("]")){k++;}
	rightBraceIndex = k;
	}//END OF METHOD. 
	
private void extractFunctionBlock(Evaluator ev) throws FunctionParsingException {
	Vector poly = new Vector(rightBraceIndex - leftBraceIndex+2);
	//SIZE ALLOWS FOR FORMAT CORRECTION...
	int i = rightBraceIndex-leftBraceIndex-1;
	while(i>0){
	poly.addElement(tokenChain.elementAt(leftBraceIndex+1));
	tokenChain.removeElementAt(leftBraceIndex+1);
	i--;}
	tokenChain.removeElementAt(leftBraceIndex+1);
	tokenChain.removeElementAt(leftBraceIndex);
	PolyEvaluator pev = new PolyEvaluator();
	popar.parse(poly,pev,ev);
	//NOW IT IS TIME TO DETERMINE WHAT KIND OF FunctionOp TO CALL
	
	if(((String)(tokenChain.elementAt(leftBraceIndex-1))).equalsIgnoreCase("cos")){
	ev.funcOpArray[funcOpIndex] = new CosOp(pev);}
	else if(((String)(tokenChain.elementAt(leftBraceIndex-1))).equalsIgnoreCase("sin")){
	ev.funcOpArray[funcOpIndex] = new SinOp(pev);}
	else if(((String)(tokenChain.elementAt(leftBraceIndex-1))).equalsIgnoreCase("tan")){
	ev.funcOpArray[funcOpIndex] = new TanOp(pev);}
	else if(((String)(tokenChain.elementAt(leftBraceIndex-1))).equalsIgnoreCase("exp")){
	ev.funcOpArray[funcOpIndex] = new ExpOp(pev);}
	else if(((String)(tokenChain.elementAt(leftBraceIndex-1))).equalsIgnoreCase("log")){
	ev.funcOpArray[funcOpIndex] = new LogOp(pev);}
	else if(((String)(tokenChain.elementAt(leftBraceIndex-1))).equalsIgnoreCase("abs")){
	ev.funcOpArray[funcOpIndex] = new AbsOp(pev);}
	else if(((String)(tokenChain.elementAt(leftBraceIndex-1))).equalsIgnoreCase("asin")
	||((String)(tokenChain.elementAt(leftBraceIndex-1))).equalsIgnoreCase("arcsin") ){
	ev.funcOpArray[funcOpIndex] = new ArcSinOp(pev);}
	else if(((String)(tokenChain.elementAt(leftBraceIndex-1))).equalsIgnoreCase("acos")
	||((String)(tokenChain.elementAt(leftBraceIndex-1))).equalsIgnoreCase("arccos")){
	ev.funcOpArray[funcOpIndex] = new ArcCosOp(pev);}
	else if(((String)(tokenChain.elementAt(leftBraceIndex-1))).equalsIgnoreCase("atan")
	||((String)(tokenChain.elementAt(leftBraceIndex-1))).equalsIgnoreCase("arctan")){
	ev.funcOpArray[funcOpIndex] = new ArcTanOp(pev);}
	else if(((String)(tokenChain.elementAt(leftBraceIndex-1))).equalsIgnoreCase("hev")){
	ev.funcOpArray[funcOpIndex] = new HevOp(pev);}
	else if(((String)(tokenChain.elementAt(leftBraceIndex-1))).equalsIgnoreCase("sgn")){
	ev.funcOpArray[funcOpIndex] = new SgnOp(pev);}
	else{throw new FunctionParsingException(UNRECOGNIZED_FUNCTION,(String)(tokenChain.elementAt(leftBraceIndex-1)));}
	//NOW REMOVE THE FUNCTION TAG, AND REPLACE IT WITH A NEW INTERNAL VARIABLE, INCREMENTING
	//internalVariableCount.
	tokenChain.removeElementAt(leftBraceIndex-1);
	internalVariableCount++;
	String internal = new String("!");
	if(internalVariableCount>1){
	for(int k=1;k<internalVariableCount;k++){
	internal += "!";}}
	tokenChain.insertElementAt(internal,leftBraceIndex-1);
	}
	
	
	

	
}		
