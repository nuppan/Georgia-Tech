package functionParser;


class Doub extends Object{
	public double num;
	static private Double dod = new Double(1.0);
	Doub(){
	num = 1.0;}
	Doub(double val){
	num = val;}
	Doub(String sss)throws FunctionParsingException{
	try{
	num = (dod.valueOf(sss)).doubleValue();}
	catch(NumberFormatException e){
	throw new NumberFormatException();
	}
	}
	
}
