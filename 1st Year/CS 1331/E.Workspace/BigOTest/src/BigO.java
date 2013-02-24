
public class BigO {
	public static void main(String[] args)
	{
		for(int n = 0; n<=60; n++)
		{
			System.out.println("n= " + n);
		int cnt4 = 0;
		for (int i = 1; i<=n; i*=2)
			for(int j=1;j<=i; j++)
				System.out.println(cnt4++);
		}
	}
}
