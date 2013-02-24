/**
 * This class represents a bit vector.
 * This class represents 32 bits which can be manipulated 
 * through use of the methods this class provides.
 * 
 * (Note the rules are different than in the other java file)
 * The only things you are allowed to use in your code are the following
 *	    - The bitwise operators |, &, ~, ^, <<, >>
 *	    - Increment and Decrement ++, and --.  You may also use add things with 1 or subtract things with 1.
 *	    - Boolean operators ||, &&, and ! only in if statements
 *	    - Conditional statements (if, if-else, switch-case, ? :).
 *	    - Equality comparisons (==, !=, <=, >=, <, >).
 *	    - String concatenation (+) only in the toString method.
 *	    - Iteration may be used for toString.
 *	    - The assignment operator (of course).
 *
 *	Anything not mentioned above you are not allowed to use. This includes the following but is not an exhaustive list:
 *	    - Multiplication & Division
 *	    - Addition & Subtraction with numbers other than 1.
 *          - Iteration in functions other than toString
 *          - The unsigned right shift operator >>> (C does not provide this operator).
 *	    - Modulus (%)
 *	    - Any functions found in Java libraries (especially the Math library)
 *	        - Example Math.pow, Integer.bitCount, etc.
 *	    - Casting (you should not have cast anywhere!)
 *          - Any Java 7 feature / standard library function (we will be grading using Java 6)
 *
 * @author Joon Ki Hong.
 *
 */
public class HW2BitVector
{
	/** 32 bit data initialized to all zeros Here is what you will be using to represent a Bit Vector */
	private int bits;
	/* You may not add any more fields to this class other than the ones given. */
	
	public static void main(String[] args)
	{
	    // Add your test cases here.
	    // Failure to add test cases will result in points taken off.
	    // You must test each function at least once.
		HW2BitVector bitVector = new HW2BitVector();
	
		bitVector.set(31);
		bitVector.set(0);
		bitVector.set(1);
		bitVector.clear(31);
		bitVector.flip(31);
		System.out.println(bitVector.isClear(32));
		System.out.println(bitVector.isClear(-1));
		System.out.println(bitVector.isSet(31));
		
		System.out.println(bitVector);
	}
	
	public HW2BitVector()
	{
		bits = 0;
		// Do not add anything else here do not change the value of bits.
	}
	
	/**
	 * Function to be used with your testing
 	 */
	public int getBits()
	{
		return bits;
	}

	/**
	 * Sets the bit (to 1) pointed to by index.
	 * @param index index of which bit to set.
	 *        0 for the least significant bit (right most bit).
	 *        31 for the most significant bit.
	 */
	public void set(int index)
	{
		bits = ((~(~0<<1))<<index)|bits;
	}

	/**
	 * Clears the bit (sets to 0) pointed to by index.
	 * @param index index of which bit to set.
	 * 	      0 for the least significant bit (right most bit).
	 * 	      31 for the most significant bit.
	 */
	public void clear(int index)
	{
		bits = ~((~(~0<<1))<<index)&bits;
	}

	/**
	 * Flips the bit (sets to the opposite of its current value) pointed to by
	 * index.
	 * @param index index of which bit to set.
	 * 	      0 for the least significant bit (right most bit).
	 * 	      31 for the most significant bit.
	 */
	public void flip(int index)
	{
		bits = ((~(~0<<1))<<index)^bits;
	}
	
	/**
	 * Returns true if the bit pointed to by index is currently set.
	 * @param index index of which bit to check.  
	 * 	      0 for the least significant bit (right most bit).
	 * 	      31 for the most significant bit.
	 * @return true if the bit is set in any other case (including index >= 32) return false.
	 */
	public boolean isSet(int index)
	{
		int maskedBits = (bits&((~(~0<<1))<<index))>>index;
		if (index>31 || index<0){
		}
		else if (maskedBits==1 || maskedBits==-1){
			return true;
		}
		return false;
	}
	
	/**
	 * Returns false if the bit pointed to by index is currently set.
	 * @param index index of which bit to check.  
	 * 	      0 for the least significant bit (right most bit).
	 * 	      31 for the most significant bit.
	 * @return false if the bit is set in any other case (including index >= 32) return true.
	 */
	public boolean isClear(int index)
	{
		int maskedBits = (bits&((~(~0<<1))<<index))>>index;
		if (index>31 || index<0){
			return true;
		}
		else if (maskedBits==1 || maskedBits==-1){
			return false;
		}
		return true;
	}
	
	/**
	 * Returns a string representation of this object.
	 * Return a string with the binary representation of the bit vector.
	 * You may use String concatenation (+) here. 
	 * You may return a 32 bit string representation or just enough bits to represent the answer
	 * i.e if the bits field was 2 then these are acceptable answers are the following
	 * 10 or 00000000000000000000000000000010
	 */
	public String toString()
	{
		String bitString = "";
		for (int i=31;i>=0;i--){
			if (isSet(i)==true && isClear(i)==false){
				bitString = bitString + "1";
			}
			else{
				bitString = bitString + "0";
			}
		}
		return bitString;
	}
}
