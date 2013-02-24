import java.io.File;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.Scanner;

public class ECProject {
	public static void main(String[] args) {
		/////////////////////////////////////////////////////////////
		//Test Input
		
		args = new String[9];
		args[0] = "cg_trace.txt";
		args[2] = "15";
		args[4] = "4";
		args[6] = "2";
		args[8] = "5";
		/////////////////////////////////////////////////////////////
		
		String fileName = args[0];
		int c = Integer.parseInt(args[2]);
		int b = Integer.parseInt(args[4]);
		int s = Integer.parseInt(args[6]); // degree of associativity
		int p = Integer.parseInt(args[8]);
		
		int setNum = (int)(Math.pow(2, c) / (Math.pow(2, b) * Math.pow(2, s)));
		int associativity = (int)Math.pow(2, s);
		
		int[][][] cache = new int[associativity][setNum][3];
		LinkedList[] LRU = new LinkedList[setNum];
		for(int i = 0; i < LRU.length; i++) {
			LRU[i] = new LinkedList();
		}
		
		int setBitNum = (int)(Math.log(setNum)/Math.log(2));
		int tagNum = 32 - (setBitNum+b);
		
		//////////////////////////////////////////////////////////////
		//  OUTPUT VARIABLE INITIALIZATION ///////////////////////////
		//////////////////////////////////////////////////////////////
		int cacheAccesses = 0;
		int readCount = 0;
		int readMisses = 0;
		int writeCount = 0;
		int writeMisses = 0;
		
		//////////////////////////////////////////////////////////////
		int tag;
		int blockOffset;
		int lineIndex;
		String bitLine;
		
		try {
			Scanner scan = new Scanner(new File(fileName));
			String nextLine = "";
			while(scan.hasNextLine()) {
				nextLine = scan.nextLine();
				System.out.println(nextLine);
				cacheAccesses++; 
				if(nextLine.charAt(0) == 'r') {
					readCount++; 
				} else if (nextLine.charAt(0) == 'w') {
					writeCount++;
				}
				bitLine = new BigInteger(nextLine.substring(2,nextLine.length()), 16).toString(2);
				System.out.println((32-bitLine.length()));
				int temp = (32-bitLine.length());
				
				for(int i = 0; i < temp; i++) {
					bitLine = "0" + bitLine;
				}
				//////////////////////////////////////////////////////
				//Test Segmentation
				
				System.out.println("Address: " + bitLine);
				System.out.println("Tag: " + bitLine.substring(0,tagNum));
				System.out.println("Set: " + bitLine.substring(tagNum,tagNum+setBitNum));
				System.out.println("BlockOffset: " + bitLine.substring(tagNum+setBitNum,tagNum+setBitNum+b));
				//////////////////////////////////////////////////////
				
				
				
			}
		} catch(Exception x) {
			x.printStackTrace();
		}
	}
}