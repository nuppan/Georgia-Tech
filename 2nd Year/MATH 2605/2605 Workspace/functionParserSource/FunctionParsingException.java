package functionParser;
/** This class handles exceptions that can be thrown during a call on the parse()
  * method of the class FunctionParser, and has methods for returning a useful
  * explanation of what went wrong to the user.
  */
public class FunctionParsingException extends Exception implements ParsingProblemType{

private int type = 0;
private String problemToken = new String("?");

public FunctionParsingException(int t){
	type = t;}

public FunctionParsingException(int t, String s){
	type = t;
	problemToken = s;}
	
/** This method returns an explanation of the circumstances that caused an exception to
  * be thrown during a call on parse() in the FunctionParser class. The expanation is
  * returned as a String, which may then be drawn to the screen, our otherwise brought
  * to the user's attention. There are eight possible ways a FunctionParsingException
  * can be thrown, and these are:
  *
  * 0: There were more right brackets than left brackets.
  *
  * 1: There were more left brackets than right brackets.
  *
  * 2: A right bracket came before any corresponding left bracket. For instance
  *    if four brackets are ordered like []][ there are two right and two left, but
  *    no grouping is defined.
  *
  * 3: There were more right parentheses than left parentheses.
  *
  * 4: There were more left parentheses than right parentheses.
  *
  * 5: A right parentheses came before any corresponding left parentheses.  For instance
  *    if four parenteses are ordered like ())( there are two right and two left, but
  *    no grouping is defined.
  *
  * 6: The unrecognized token (problemToken) showed up where a variable name should be.
  *    The actual unrecognized token replaces  "(problemToken)" in the returned String value.
  *
  * 7: The unrecognized token (problemToken) showed up where a function name should be.
  *    The actual unrecognized token replaces  "(problemToken)" in the returned String value.
  *
  * The first sentence in each case's explanation above is 
  * the value of the returned String.
  */	
public String explainException(){
	String  str;
	switch(type){
	
	case 0: str = new String("There were more right brackets than left brackets.");
			break;
	
	case 1: str = new String("There were more left brackets than right brackets.");
			break;
	
	case 2: str = new String("A right bracket came before any corresponding left bracket.");
			break;
			
    case 3: str = new String("There were more right parentheses than left parentheses.");
			break;
			
	case 4: str = new String("There were more left parentheses than right parentheses.");
			break;
			
	case 5: str = new String("A right parentheses came before any corresponding left parentheses.");
			break;
	
	case 6: str = new String("The unrecognized token " + problemToken + 
			"showed up where a variable  should be.");
			break;
			
	case 7: str = new String("The unrecognized token " + problemToken + 
			"showed up where a function name should be.");
			break;
			
	default: str = new String("?");
	}
	return str;}
	
/** This method returns an integer describing the circumstances that caused the 
  * exception to be thrown. The integer is one of 0 through 7, and the correspond
  * circumstances are those described under this number in the ExplainException()
  * method. This can be used if one wants to substitute shorter or longer
  * explanations in a particular application.
  */	
public int typeOfException(){
	
	return type;
	}
	
}