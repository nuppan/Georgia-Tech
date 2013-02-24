
public class Rainfall {

	public static void main(String[] args) throws Exception { 
		int[] array = new int[100];
		char c = (char) System.in.read();
		int count = 0;
		int tempSum = 0;
		int sum = 0;
		while (c!='x'){		
			while (c!='\n'){
				tempSum = sum;
				sum+=tempSum;
				sum+=tempSum;
				sum+=tempSum;
				sum+=tempSum;
				sum+=tempSum;
				sum+=tempSum;
				sum+=tempSum;
				sum+=tempSum;
				sum+=tempSum;
				sum = sum + (c-'0');
				c = (char) System.in.read();
				tempSum = 0;
			}
			array[count] = sum;
			count++;
			c = (char) System.in.read();
			sum=0;
		}
		int counter = count-1;
		int min = 999;
		int max = 0;
		int average = 0;
		while (counter>=0){
			sum = array[counter];
			average = average + sum;
			counter--;
			if (sum<min){
				min=sum;
			}
			if (sum>max){
				max=sum;
			}
		}
		average = average/count;
		System.out.println(min);
		System.out.println(max);
		System.out.println(average);
		
	}
}
