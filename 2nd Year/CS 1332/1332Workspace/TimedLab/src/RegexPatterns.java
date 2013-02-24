public class RegexPatterns {
	
	/**
	 * Test name.
	 * 
	 * Provide a regex pattern that validates a person's first and last name. 
	 * Each name should be capitalized and should not contain any punctuation 
	 * characters except a "-". You should limit the pattern to only two names.
	 *
	 * @param name the name
	 * @return true, if successful
	 */
	public static boolean testName(String name){
		
		return name.matches("[A-Z][A-Za-z]* [A-Z][A-Za-z]*|[A-Z][A-Za-z]*-[A-Z][A-Za-z]* [A-Z][A-Za-z]*");
	}
	
	/**
	 * Test name.
	 * 
	 * Provide a Regex pattern that matches Atlanta phone numbers. The format 
	 * should be "(###)###-####" and the only valid area codes are 404,678 and
	 * 770.
	 *
	 * @param name the name
	 * @return true, if successful
	 */
	public static boolean testPhoneNumber(String phoneNumber){
		
		return phoneNumber.matches("[(](404|678|770)[)][0-9]{3}-[0-9]{4}");
	}
	
	/**
	 * Test email.
	 * 
	 * Provide a Regex pattern that validates an email. The email should be
	 * broken up into the following sections:
	 * 
	 * 1) any word character, digit, underscore, dash and period combination,
	 *    but must start with a letter
	 * 2) the only @
	 * 3) any word character, digit, underscore and dash combination, but must
	 *    start with a word character
	 * 4) a single period
	 * 5) valid emails must end in com, org or net
	 *
	 * @param email the email
	 * @return true, if successful
	 */
	public static boolean testEmail(String email){
		// TODO
		return email.matches("[A-Za-z][\\w.-]*@[A-Za-z][\\w]*.(com|org|net)");
	}
	
	/**
	 * Test address.
	 * 
	 * Provide a regex pattern to validate a mailing address. The address must
	 * 1) start with a number, 1-5 digits in length
	 * 2) have some word characters and 
	 * spaces defining the street
	 * 3) break to a newline
	 * 4) Include a capitalized word as the city
	 * 5) comma
	 * 6) 2 capital characters as state
	 * 7) a 5 digit zipcode.
	 * 
	 * The state characters can be any 2 capital letters, they do not  need to
	 * actually match a real state.
	 *
	 * @param address the address
	 * @return true, if successful
	 */
	public static boolean testAddress(String address){
		// TODO
		return address.matches("[0-9]{1,5} [\\w. -]*\n[A-Z][A-Za-z .-]*, [A-Z]{2} [0-9]{5}");
	}
	
	
	/**
	 * Test java executable.
	 * 
	 * Provide a regex pattern that will check a string of text (presumably
	 * from a .java file, but that is not important here) to see if it contains
	 * "public static void main(String[] args)". Do not worry about checking for
	 * the string being commented out or enclosed in quotes.
	 *
	 * @param java the Java string
	 * @return true, if successful
	 */
	public static boolean testJavaExecutable(String java){
		return java.matches("[\\w^\\W]*(public static void main\\(String\\[\\] args\\))[\\w^\\W]*");
	}

}
