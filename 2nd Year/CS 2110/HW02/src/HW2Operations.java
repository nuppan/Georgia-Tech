/**
 * HW2 Part 2 - Coding with bitwise operators
 * 
 * (Note the rules are different than in the other java file).
 * The only operators you are allowed to use in your code are the following
 *	- The bitwise operators |, &, ~, ^, <<, >>
 *	- Increment and Decrement ++, and --.  You may also use add things with 1 or subtract things with 1.
 *	- Boolean operators ||, &&, and ! only in if statements
 *	- Conditional statements (if, if-else, switch-case, ? :).
 *	- Equality comparisons (ONLY ==, !=).
 *	- The assignment operator (although you don't really need it here).
 *
 * Anything not in the above list you are not allowed to use. This includes the following but is not an exhaustive list:
 *	- Multiplication & Division
 *	- Addition & Subtraction with numbers other than 1.
 *	- Modulus (%)
 *	- Iteration (for, while, do-while loops, recursion)
 *      - The unsigned right shift operator >>> (C does not provide this operator).
 *	- Any functions found in Java libraries (especially the Math library)
 *		- Example Math.pow, Integer.bitCount, etc.
 *	- Greater than and less than comparisons (>, <, >=, <=)
 *	- Casting (you should not have cast anywhere!)
 *      - Any Java 7 feature / standard library function (we will be grading using Java 6) 
 *
 * Note for signExtend we will allow you to add/subtract any values
 *
 * As a note all of these functions accept ints. Here is the reason
 * 1) If you pass in a number (which is of type int by default) into a function accepting a byte
 *    the Java compiler will complain even if the number would fit into that type.
 *    
 * Now keep in mind the return value is also an int.  Please read the comments on how many bits
 * to return and make sure the other bits are not set or else you will not get any points for that
 * test case. i.e if I say to return 6 bits and you return 0xFFFFFFFF you will lose points.
 *
 * Definitions of types
 * nibble (nybble) - 4 bits
 * byte - 8 bits
 * short - 16 bits
 * int - 32 bits
 * 
 * @author Joon Ki Hong.
 * 
 */
public class HW2Operations
{
	
	public static void main(String[] args)
	{
		// Test your code here.
		// You should devise your own test cases here in addition to the ones in the comments.
		// Come up with one more test case than the ones given in the comments.
		
		System.out.println("set byte methods:");
		
		HW2Operations.setByte(0x3456, 0x18, 0);
	
		HW2Operations.setByte(0x1237,0xF,1);
		
		System.out.println("get byte methods:");

		HW2Operations.getByte(0x5678,0);
		
		HW2Operations.getByte(0xFF25, 1);
		
		System.out.println("set range methods:");
		
		HW2Operations.setRange(0x5678, 2, 5);
		
		HW2Operations.setRange(0xFF25, 1, 8);
		
		System.out.println("pack methods:");
		
		HW2Operations.pack(0x1, 0x2, 0x3, 0x4);
		
		HW2Operations.pack(0xB, 0xE, 0xE, 0xF);
		
		System.out.println("bitwise absolute methods:");
		
		HW2Operations.bitwiseAbs(0x000000D2, 9);
		
		HW2Operations.bitwiseAbs(0x000007E4, 11);
		
		HW2Operations.bitwiseAbs(0x00000003, 2);
		
		System.out.println("sign extend methods:");
		
		HW2Operations.signExtend(0x000000D2, 9, 32);
		
		HW2Operations.signExtend(0x000007E4, 11, 32);
		
	}
	
	/**
	 * Sets a byte in a short
	 * 
	 * Shorts are made of two bytes, numbered like so: 11111111 00000000
	 *
	 * For a graphical representation of this:
	 *            1                 
	 *  5 4 3 2 1 0 9 8 7 6 5 4 3 2 1 0
	 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
	 * |     Byte1     |     Byte0     |
	 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
	 * 
	 * 
	 * Examples:
	 *      setByte(0x3456, 0x18, 0) //=> 0x3418
	 *      setByte(0x1237, 0xF, 1)  //=> 0x0F37
	 * 
	 * @param num short that will be modified
	 * @param b byte to insert into the integer
	 * @param which determines which byte gets modified - 0 for least significant byte
	 *            
	 * @return the modified short
	 */
	public static int setByte(int num, int b, int which)
	{
		int modShort;
		if (which==0){
			modShort = (num&65280)|b;
		}
		else{
			int shiftedByte = b<<8;
			modShort = (num&255)|shiftedByte;
		}
		System.out.printf("0x%x\n",modShort);
		return modShort;
	}
	
	/**
	 * Gets a byte from a short
	 * 
	 * Other examples: i.e. 
	 * getByte(0x5678, 0) //=> 0x78
	 * getByte(0xFF25, 1) //=> 0xFF
	 * 
	 * @param num a short
	 * @param which determines which byte gets returned - 0 for least significant byte
	 *            
	 * @return a byte corresponding 
	 */
	public static int getByte(int num, int which)
	{
		int modShort;
		if (which==0){
			modShort = num&255;
		}
		else{
			modShort = (num&65280)>>8;
		}
		System.out.printf("0x%x\n",modShort);
		return modShort;
	}
	
	/**
	 * Sets (to 1) a range of bits in a short
	 * 
	 * 0<=start<=end<=15 i.e they will be valid
	 * 
	 * Other examples: i.e. 
	 * setRange(0x5678, 2, 5) //=> 0x567C
	 * setRange(0xFF25, 1, 8) //=> 0xFFFF
	 * 
	 * @param num a short
	 * @param start determines least significant bit to set - 0 for least significant byte
	 * @param end determines most significat bit to set - 0 for least significant byte
	 *            
	 * @return the modified short 
	 */
	public static int setRange(int num, int start, int end)
	{
		int bitMask =  (~(~0<<end)>>start)<<start;
		int modShort = num|bitMask;
		System.out.printf("0x%x\n",modShort);
		return modShort;
	}

	/**
	 * Packs 4 nibbles (4 bits) into a short (16 bits)
	 * 
	 * The nibbles should be placed consecutively in the short in the order
	 * specified in the parameters.
	 * 
	 * i.e. pack(0x1, 0x2, 0x3, 0x4) //=> 0x1234 
	 *      pack(0xB, 0xE, 0xE, 0xF) //=> 0xBEEF
	 * 
	 * @param c1 most significant nibble (will always be a 4 bit number)
	 * @param c2 2nd nibble (will always be a 4 bit number)
	 * @param c3 3rd nibble (will always be a 4 bit number)
	 * @param c4 4th nibble (will always be a 4 bit number)
	 *            
	 * @return a short formatted like so c1c2c3c4
	 */
	public static int pack(int c1, int c2, int c3, int c4)
	{
		int modShort = (((c1<<12)|(c2<<8))|(c3<<4))|(c4);
		System.out.printf("0x%x\n", modShort);
		return modShort;
	}
	
	/**
	 * Returns a positive number with the same magnitude as the original
	 * 
	 * Both the parameter and return value are ints, but they both only 
	 * use the least significant n bits (the rest should be 0)
	 * 
	 * Examples:
	 *  bitwiseAbs(0x000000D2, 9) results in 0x000000D2
	 *  bitwiseAbs(0x000007E4, 11) results in 0x0000001C
	 * 
	 * @param num an n bit 2's complement number
	 * @param n the bit length
	 * @return a 2's complement number, the the absolute value of num.
	 */
	public static int bitwiseAbs(int num, int n)
	{
		int absNum;
		if ((num>>(n-1))==1){
			absNum = ((~num)+1)&~(~0<<n);
		}
		else{
			absNum = num;
		}
		System.out.printf("0x%x\n", absNum);
		return absNum;
	}
	
	/**
	 * Sign extends an 'x' bit value to a 'y' bit value
	 * 
	 * Both the parameter and return value are ints, but the parameter only uses the least significant
	 * x bits, and the return value only uses the least significant y bits (the rest are zeros)
	 * Relaxed rules for this one you may addition and subtraction with values other than 1.
	 * 
	 * Examples:
	 *  signExtend(0x000000D2, 9, 32) results in 0x000000D2
	 *  signExtend(0x000007E4, 11, 32) results in 0xFFFFFFE4
	 * 
	 * @param num an x bit 2's complement number
	 * @param x the original bit length
	 * @param y the new bit length
	 * @warning You will be guaranteed y > x.  If this precondition does not hold results are undefined.
	 * @return a 'y' bit 2's complement number, the sign extended value of num.
	 */
	public static int signExtend(int num, int x, int y)
	{
		int signExtNum;
		if ((num>>(x-1)==0)){
			signExtNum = num;
		}
		else{
			signExtNum = num|(((~0<<x+(32-y))>>(32-y)));
		}
		System.out.printf("0x%x\n", signExtNum);
		return signExtNum;
	}
}
