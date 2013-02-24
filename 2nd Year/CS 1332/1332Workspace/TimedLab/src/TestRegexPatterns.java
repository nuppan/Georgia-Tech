import junit.framework.TestCase;


/**
 * Each test is worth 0.2pts for a total of 10pts. We will 
 * round down when entering grading into T-Square.
 * 
 * Yes... if you do nothing and turn it in, you will get 4pts.
 * 
 * @author David Esposito
 *
 */
public class TestRegexPatterns extends TestCase {

	/*
	 * =======================================================================
	 * =======================================================================
	 * TEST NAME
	 * =======================================================================
	 * =======================================================================
	 */
	public void testName0() {
		assertTrue("Failed name case 1",RegexPatterns.testName("Peter Griffin"));
	}

	public void testName1() {
		assertTrue("Failed name case 2",RegexPatterns.testName("Anna-Kate Smith"));
	}

	public void testName2() {
		assertTrue("Failed name case 3",RegexPatterns.testName("Mark ONeill"));
	}

	public void testName3() {
		assertFalse("Failed name case 4",RegexPatterns.testName("Mark O'Neill"));
	}

	public void testName4() {
		assertFalse("Failed name case 5",RegexPatterns.testName("homer Simpson"));
	}

	public void testName5() {
		assertFalse("Failed name case 6",RegexPatterns.testName("homer simpson"));
	}

	public void testName6() {
		assertFalse("Failed name case 7",RegexPatterns.testName("Homer simpson"));
	}

	/*
	 * =======================================================================
	 * =======================================================================
	 * TEST PHONE NUMBER
	 * =======================================================================
	 * =======================================================================
	 */
	public void testPNumber0() {
		assertFalse("Failed phone number case 1",RegexPatterns.testPhoneNumber("4044444444"));
	}

	public void testPNumber1() {
		assertFalse("Failed phone number case 2",RegexPatterns.testPhoneNumber("404-444-4444"));
	}

	public void testPNumber2() {
		assertTrue("Failed phone number case 3",RegexPatterns.testPhoneNumber("(404)444-4444"));
	}

	public void testPNumber3() {
		assertTrue("Failed phone number case 4",RegexPatterns.testPhoneNumber("(678)444-4444"));
	}

	public void testPNumber4() {
		assertTrue("Failed phone number case 5",RegexPatterns.testPhoneNumber("(770)444-4444"));
	}

	public void testPNumber5() {
		assertFalse("Failed phone number case 6",RegexPatterns.testPhoneNumber("(202)444-4444"));
	}

	public void testPNumber6() {
		assertFalse("Failed phone number case 7",RegexPatterns.testPhoneNumber("(404) 444-4444"));
	}

	/*
	 * =======================================================================
	 * =======================================================================
	 * TEST EMAIL
	 * =======================================================================
	 * =======================================================================
	 */

	public void testEmail0() {
		assertTrue("Failed email case 1",RegexPatterns.testEmail("email@email.com"));
	}

	public void testEmail1() {
		assertTrue("Failed email case 2",RegexPatterns.testEmail("email.email@email.com"));
	}

	public void testEmail2() {
		assertTrue("Failed email case 3",RegexPatterns
				.testEmail("email-email_email@email.com"));
	}

	public void testEmail3() {
		assertFalse("Failed email case 4",RegexPatterns.testEmail("email@email@email.com"));
	}

	public void testEmail4() {
		assertFalse("Failed email case 5",RegexPatterns.testEmail("email&email@email.com"));
	}

	public void testEmail5() {
		assertTrue("Failed email case 6",RegexPatterns
				.testEmail("e______________________mail@email.com"));
	}

	public void testEmail6() {
		assertTrue("Failed email case 7",RegexPatterns.testEmail("email@email.org"));
	}

	public void testEmail7() {
		assertTrue("Failed email case 8",RegexPatterns.testEmail("email@email.net"));
	}

	public void testEmail8() {
		assertTrue("Failed email case 9",RegexPatterns.testEmail("email@ema____il.net"));
	}

	public void testEmail9() {
		assertTrue("Failed email case 10",RegexPatterns.testEmail("ema1234il@email.net"));
	}

	public void testEmail10() {
		assertTrue("Failed email case 11",RegexPatterns.testEmail("email@ema1234il.net"));
	}

	public void testEmail11() {
		assertFalse("Failed email case 12",RegexPatterns.testEmail("email@email.it"));
	}

	public void testEmail12() {
		assertFalse("Failed email case 13",RegexPatterns.testEmail("4email@email.com"));
	}

	public void testEmail13() {
		assertFalse("Failed email case 14",RegexPatterns.testEmail("email@4email.com"));
	}

	/*
	 * =======================================================================
	 * =======================================================================
	 * TEST ADDRESS
	 * =======================================================================
	 * =======================================================================
	 */

	public void testAddress0() {
		assertTrue("Failed address case 1",RegexPatterns
				.testAddress("1 Atlantic Ave.\nAtlanta, GA 30313"));
	}

	public void testAddress1() {
		assertTrue("Failed address case 2",RegexPatterns
				.testAddress("12 Atlantic Ave.\nAtlanta, GA 30313"));
	}

	public void testAddress2() {
		assertTrue("Failed address case 3",RegexPatterns
				.testAddress("123 Atlantic Ave.\nAtlanta, GA 30313"));
	}

	public void testAddress3() {
		assertTrue("Failed address case 4",RegexPatterns
				.testAddress("1234 Atlantic Ave.\nAtlanta, GA 30313"));
	}

	public void testAddress4() {
		assertTrue("Failed address case 5",RegexPatterns
				.testAddress("12345 Atlantic Ave.\nAtlanta, GA 30313"));
	}

	public void testAddress5() {
		assertFalse("Failed address case 6",RegexPatterns
				.testAddress("123456 Atlantic Ave.\nAtlanta, GA 30313"));
	}

	public void testAddress6() {
		assertTrue("Failed address case 7",RegexPatterns
				.testAddress("123 A\nAtlanta, GA 30313"));
	}

	public void testAddress7() {
		assertTrue("Failed address case 8",RegexPatterns
				.testAddress("123 lowercase\nAtlanta, GA 30313"));
	}

	public void testAddress8() {
		assertFalse("Failed address case 9",RegexPatterns.testAddress("123\nAtlanta, GA 30313"));
	}

	public void testAddress9() {
		assertFalse("Failed address case 10",RegexPatterns
				.testAddress("123 Atlantic Ave.\natlanta, GA 30313"));
	}

	public void testAddress10() {
		assertFalse("Failed address case 11",RegexPatterns
				.testAddress("123 Atlantic Ave.\nAtlanta GA 30313"));
	}

	public void testAddress11() {
		assertFalse("Failed address case 12",RegexPatterns
				.testAddress("123 Atlantic Ave.\nAtlanta, Ga 30313"));
	}

	public void testAddress12() {
		assertFalse("Failed address case 13",RegexPatterns
				.testAddress("123 Atlantic Ave.\nAtlanta, ga 30313"));
	}

	public void testAddress13() {
		assertFalse("Failed address case 14",RegexPatterns
				.testAddress("123 Atlantic Ave.\nAtlanta, GA 30"));
	}

	public void testAddress14() {
		assertFalse("Failed address case 15",RegexPatterns
				.testAddress("123 Atlantic Ave.\nAtlanta, GA 30313-5124"));
	}

	public void testAddress15() {
		assertTrue("Failed address case 16",RegexPatterns
				.testAddress("100 The Moon\nCity, NA 00000"));
	}

	public void testAddress16() {
		assertTrue("Failed address case 17",RegexPatterns
				.testAddress("100 First-Street Cir.\nSt. Lewis, NA 00000"));
	}


	public void testAddress17() {
		assertTrue("Failed address case 18",RegexPatterns
				.testAddress("100 First-Street Cir.\nNorth-Ridge, NA 00000"));
	}


	public void testAddress18() {
		assertFalse("Failed address case 19",RegexPatterns
				.testAddress("First-Street Cir.\nNorth-Ridge, NA 00000"));
	}

	/*
	 * =======================================================================
	 * =======================================================================
	 * TEST JAVA EXECUTABLE
	 * =======================================================================
	 * =======================================================================
	 */

	public void testJavaExec0() {
		assertTrue("Failed Java method case 1",RegexPatterns
				.testJavaExecutable("public static void main(String[] args)"));
	}

	public void testJavaExec1() {
		assertTrue("Failed Java method case 2",RegexPatterns
				.testJavaExecutable("\n //  public static void main(String[] args) \n "));
	}

	public void testJavaExec2() {
		assertFalse("Failed Java method case 3",RegexPatterns
				.testJavaExecutable("\n  public void main(String[] args)  \n"));
	}
}
