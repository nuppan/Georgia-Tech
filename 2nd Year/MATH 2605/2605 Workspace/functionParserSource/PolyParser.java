package functionParser;

import java.util.*;

class PolyParser extends Object implements ParsingProblemType{

	
	private int topIndex = 0;
	private int rightIndex = 0;
	private int place = 0;
	private int opArrayIndex = 0;
	boolean neededAdjustment = false;
	private Vector doubVect;
	private Vector ops;
	private Vector tokens;
	private String[] variableList;
	
	
	
	FunctionParser par;
	
	public PolyParser(FunctionParser fp){
	par = fp;
	variableList = fp.variables();
	}	
	
	public void parse(Vector tvec, PolyEvaluator pev, Evaluator ev) throws FunctionParsingException{
		tokens = tvec;
		format();
		ops = new Vector(tvec.size());
		doubVect = new Vector(tvec.size());
		boolean alreadyFound = false;
		
		//NOW PROCESS THE TOKENS
		for(int k=0;k<tokens.size();k++){
		switch((int)((String)(tokens.elementAt(k))).charAt(0)){	
			case 40://FALLSTHROUGH
		    case 41://FALLSTHROUGH
		    case 42://FALLSTHROUGH
		   	case 43://FALLSTHROUGH
		   	case 45://FALLSTHROUGH
		   	case 47://FALLSTHROUGH
		   	case 94://FALLSTHROUGH
				ops.addElement(tokens.elementAt(k));
		   		break;
		    case 32:
		    break;
		    default:
		    int varCount = variableList.length - 1;
		    alreadyFound = false;
		    for(int p=0;p<varCount;p++){
		    if(((String)tokens.elementAt(k)).equals(variableList[p])){
			doubVect.addElement(ev.variableValues[p]);
			alreadyFound = true;}}
			if(!alreadyFound){
			if((int)((String)tokens.elementAt(k)).charAt(0) == 33){
			doubVect.addElement((ev.funcOpArray[((String)tokens.elementAt(k)).length()-1]).output);}
			else if(((String)tokens.elementAt(k)).equalsIgnoreCase("pi")){
			doubVect.addElement(new Doub(Math.PI));}
			else{
			try{
 			doubVect.addElement(new Doub((String)tokens.elementAt(k)));}
 			catch(NumberFormatException e){
 			throw new FunctionParsingException(UNRECOGNIZED_VARIABLE,(String)tokens.elementAt(k));}
 			}}
			}
		
		}//end of while
 		
	    doubVect.trimToSize();
	    //tokens.removeAllElements();
		
		//Here comes the processing:
		
		
		remPar();
		pev.opArray = new Operation[ops.size()-2*countParPairs()];
		opArrayIndex = 0;
		while(ops.size()>0){
			buildChain(pev);
			remPar();
			}
		pev.doubArray = new Doub[doubVect.size()];
		for(int p=0;p<pev.doubArray.length;p++){
			pev.doubArray[p] = ((Doub)doubVect.elementAt(p));}
		/*
		ops.removeAllElements();
		doubVect.removeAllElements();*/
		place = 0;	
		}
		
		//CHECK FOR UNMATCHED PARENTHESES
		private int countParPairs() throws FunctionParsingException{
		int left = 0;
		int right = 0;
		for(int i=0;i<tokens.size();i++){
		if(((String)(tokens.elementAt(i))).equals("(")){left++;}
		if(((String)(tokens.elementAt(i))).equals(")")){right++;}
		if(right>left){throw new FunctionParsingException(RIGHT_PARENTHESES_UNMATCHED);}
		}
		if(left > right){throw new FunctionParsingException(TOO_MANY_LEFT_PARENTHESES);}
		if(right > left){throw new FunctionParsingException(TOO_MANY_RIGHT_PARENTHESES);}
		return left;}
		
		//CHECK FORMAT
		private void format(){
		if(tokens.size() == 1){
		tokens.insertElementAt(new String("+"),0);
		tokens.insertElementAt(new String("0"),0);}
		neededAdjustment = true;
		while(neededAdjustment){
		neededAdjustment = false;
		for(int p =0;p<tokens.size()-2;p++){
		if(((String)tokens.elementAt(p)).equals("(") && 
		((String)tokens.elementAt(p+2)).equals(")")){
		tokens.insertElementAt(new String("+"),p+1);
		tokens.insertElementAt(new String("0"),p+1);
		p+=2;
		neededAdjustment = true;}}}
		//SINGLE VARIABLE EXPRESSIONS NOW O.K.
		//NEXT TAKE CARE OF UNARY MINUS SIGNS
		if(((String)tokens.elementAt(0)).equals("-")){
		tokens.insertElementAt(new String("0"),0);}
		neededAdjustment = true;
		while(neededAdjustment){
		neededAdjustment = false;
		for(int p =0;p<tokens.size()-1;p++){
		if(((String)tokens.elementAt(p)).equals("(") && 
		((String)tokens.elementAt(p+1)).equals("-")){
		tokens.insertElementAt("0",p+1);
		p+=1;
		neededAdjustment = true;}}}
		tokens.trimToSize();
		//AT THIS POINT UNARY MINUS SIGNS ARE TAKEN CARE OF
		}//END OF format()
		
		
			
		//At this point we have our vectors. Now come the operations on them.
		//first, we remove extraneous parentheses. 
		
		private void remPar(){
		neededAdjustment = true;
		while(neededAdjustment){
		neededAdjustment = false;
		for(int m=0;m < ops.size()-1;m++){
			if(ops.elementAt(m).equals("(") && ops.elementAt(m+1).equals(")")){
			ops.removeElementAt(m+1);
			ops.removeElementAt(m);
			neededAdjustment = true;}}}
		
		}
		
		//Now we locate the highest priority operation. First, we go through the
		//parentheses to find the "depth". Each "(" adds 32, and each ")" subtracts 32.
		//here the addition affects only the priorities of the succeding operations;
		//the parentheses themselves have no priority.
		//then each ^  adds 3, each * or / adds 2, and each + or - adds 1. 
		
		private void topPriority(){
			int[] priorities = new int[ops.size()];
			int level = 0;
			for(int m=0;m<ops.size();m++){
			switch((int)((String)(ops.elementAt(m))).charAt(0)){
			case(40):{
			level++;
			priorities[m] = 0;}
			break;
			case(41):{
			level--;
			priorities[m] = 0;}
			break;
			case(94):
			priorities[m] = (32*level + 3);
			break;
			case(42)://FALLSTHROUGH
			case(47):{
			priorities[m] = (32*level + 2);}
			break;
			default:
			priorities[m] = (32*level + 1);
			}//END OF SWITCH
			}
			
			topIndex = 0;
			for(int m=1;m<ops.size();m++){
			if(priorities[m] > priorities[topIndex]){
			topIndex = m;}
			}
			rightIndex = 0;
			for(int m=0;m<=topIndex;m++){
			if(!(ops.elementAt(m).equals("(")) && !(ops.elementAt(m).equals(")")))
			rightIndex++;
			}
			}//END OF topPriority()
			
			
			private void buildChain(PolyEvaluator pev){
			topPriority();
			Operation bb;
			switch((int)((String)(ops.elementAt(topIndex))).charAt(0)){
			case(94):
			bb = new Power();
			break;
			case(42):
			bb = new Multiply();
			break;
			case(47):
			bb = new Divide();
			break;
			case(43):
			bb = new Add();
			break;
			default:
			bb = new Subtract();
			break;
			
			}//END OF SWITCH
			pev.opArray[opArrayIndex] = bb;
			opArrayIndex++;
			
			(pev.opArray[place]).rightOperand = (Doub)doubVect.elementAt(rightIndex);
			(pev.opArray[place]).leftOperand = (Doub)doubVect.elementAt(rightIndex-1);
			doubVect.removeElementAt(rightIndex);
			doubVect.removeElementAt(rightIndex-1);
			doubVect.insertElementAt((pev.opArray[place]).output,rightIndex-1);
			ops.removeElementAt(topIndex);
			place++;
			}
			
			
			
					
		
}//end of  PolyParser class


	
	
	
	
	
	

	


