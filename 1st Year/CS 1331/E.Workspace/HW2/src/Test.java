import java.util.*;
import java.text.*;
public class Test{
	
	public static void main(String[] args){
		
		final double TICKET = 10.50;
		NumberFormat nfmt = NumberFormat.getCurrencyInstance();
		Scanner scan = new Scanner(System.in);
		System.out.println("How many tickets do you want?");
		int xinput = scan.nextInt();
		double result = xinput*TICKET;
		System.out.println("That will be "+nfmt.format(result));
		
		
	}
	
	
}
