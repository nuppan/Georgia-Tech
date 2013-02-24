
public class Brandonacci {
	public static int brandonacci(int n){
		if (n<=1){
			return -(3*n+2);
		}
		else{
			int c1 = brandonacci(n-3);
			int c2 = brandonacci(n-5);
			return 2*c1-2*c2+7;
		}
	}
	public static void main(String[] args){
		System.out.println(brandonacci(5));
	}
}
