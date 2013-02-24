package android.manager.test;

/**
 * This class is an android test case which features ten tests aimed at testing out our back-end
 * database class out thoroughly. They're all mostly blackbox(ish).
 * @author Joon Ki Hong, Jonathan Betancourt, Griva Patel, Curtis Luckey. Amit Paduvalli  
 */
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import android.manager.database.Database;
import android.manager.view.LoginActivity;
import android.manager.view.R;
import android.test.ActivityInstrumentationTestCase2;

public class Tests extends ActivityInstrumentationTestCase2<LoginActivity> {
	
	//The following definitions are data that're repeatedly used in our tests
	private LoginActivity temp;
	private Database dbUser;
	private static final String username = "Test123ABC";
	private static final String password = "Test123ABC";
	private static final String description = "Test123ABC";
	private static final String detail = "Test123ABC";
	private static final String category = "Test123ABC";
	private static final String location = "Test123ABC";
	private static final String date = "2012-12-21";
	private static final String time = "12.30";
	
	/**
	 * Constructor
	 */
	public Tests() {
		super("android.manager.view", LoginActivity.class);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * The default setUp() method is overridden to setup a preliminary framework for our tests
	 */
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		
		temp = getActivity();
		dbUser = new Database(temp);
		dbUser.open();
		dbUser.resetSession();
		dbUser.deleteUser(username);//To prevent all tests from failing due to a single error/failure somewhere
		
	}
	
	/**
	 * Tests the hasUser() method of our database class
	 */
	@Test
	public void testHasUser() {
		
		assertFalse("Test user should not exist in the database",dbUser.hasUser(username));
		dbUser.addUser(username,password);
		assertTrue("Existent user not detected",dbUser.hasUser(username));		
		dbUser.deleteUser(username);
		assertFalse("Test user should not exist in the database",dbUser.hasUser(username));
		dbUser.close();
	}
	
	/**
	 * Tests the addUser() method of our database class
	 */
	@Test
	public void testAddUser() {
		
		assertFalse("Test user should not exist in the database",dbUser.hasUser(username));
		assertFalse("Test user1 should not exist in the database",dbUser.hasUser(username+"xyz"));
		dbUser.addUser(username,password);
		assertTrue("Added user not detected",dbUser.hasUser(username));		
		assertFalse("Test user1 should still not exist in the database",dbUser.hasUser(username+"xyz"));
		dbUser.deleteUser(username);
		dbUser.close();
	}
	
	/**
	 * Tests the login() method of our database class
	 */
	@Test
	public void testLogin() {
		
		assertFalse("User doesn't exist but login was allowed",dbUser.login(username,password));
		assertFalse("User1 doesn't exist but login was allowed",dbUser.login(username+"xyz",password));
		dbUser.addUser(username,password);
		assertTrue("Valid username and password but failed login",dbUser.login(username,password));
		assertFalse("Valid username and invalid password but successful login",dbUser.login(username,password+"xyz"));
		assertFalse("Invalid username and valid password but successful login",dbUser.login(username+"xyz",password));
		assertFalse("Invalid username and password but successful login",dbUser.login(username+"xyz",password+"xyz"));
		dbUser.login(username, password);
		assertEquals("Multiple active users",1,dbUser.temporaryUserCounter());		
		dbUser.deleteUser(username);
		assertFalse("User doesn't exist but login was allowed",dbUser.login(username,password));
		dbUser.close();
	}
	
	/**
	 * Tests the activeUser() method of our database class
	 */
	@Test
	public void testActiveUser() {		
		assertFalse("User shouldn't exist",dbUser.hasUser(username));
		assertFalse("User1 shouldn't exist",dbUser.hasUser(username+"xyz"));
		dbUser.addUser(username,password);
		dbUser.addUser(username+"xyz",password);
		assertEquals("No user is logged in, but an active session is being detected","",dbUser.activeUser());
		dbUser.login(username,password);
		assertEquals("The logged in user isn't active",username,dbUser.activeUser());
		dbUser.login(username+"xyz",password);
		assertEquals("The logged in user isn't active",username+"xyz",dbUser.activeUser());
		dbUser.deleteUser(username);
		dbUser.deleteUser(username+"xyz");
		assertEquals("No user is logged in, but an active session is being detected","",dbUser.activeUser());
		dbUser.close();
	}
	
	/**
	 * Tests the resetSession() method of our database class
	 */
	@Test
	public void testResetSession() {		
		assertFalse("User shouldn't exist",dbUser.hasUser(username));		
		dbUser.addUser(username,password);		
		assertEquals("All sessions must be 0, but are not","",dbUser.activeUser());
		dbUser.login(username,password);
		assertEquals("The logged in user isn't active",username,dbUser.activeUser());
		dbUser.resetSession();
		assertEquals("All sessions must be 0, but are not","",dbUser.activeUser());		
		dbUser.deleteUser(username);		
		dbUser.close();
	}
	
	/**
	 * Tests the addTask() method of our database class
	 */
	@Test
	public void testAddTask() {			
		dbUser.addUser(username,password);
		dbUser.login(username, password);
		assertTrue("User shouldn't have ane tasks yet",dbUser.taskCounter(username)==0);
		dbUser.addTask(description, detail, category, location, date, time);		
		assertTrue("User should have one task",dbUser.taskCounter(username)==1);
		dbUser.addTask(description+"xyz", detail, category, location, date, time);
		assertTrue("User should have two tasks",dbUser.taskCounter(username)==2);		
		dbUser.deleteUser(username);		
		dbUser.close();
	}
	
	/**
	 * Tests the descList() method of our database class
	 */
	@Test
	public void testDescList() {			
		dbUser.addUser(username,password);
		dbUser.login(username, password);
		assertTrue("User shouldn't have ane tasks yet",dbUser.taskCounter(username)==0);
		dbUser.addTask(description, detail, category, location, date, time);		
		dbUser.addTask(description+"xyz", detail, category, location, date, time);
		assertTrue("User should have two tasks",dbUser.taskCounter(username)==2);
		String[] descriptions = dbUser.desclist();
		assertTrue("Two short descriptions were expected",descriptions.length==2);
		assertTrue("The output wasn't what was expected",
					descriptions[0].equals(description) && descriptions[1].equals(description+"xyz"));
		dbUser.deleteUser(username);		
		dbUser.close();
	}
	
	/**
	 * Tests the filter() method of our database class
	 */
	@Test
	public void testFilter() {			
		dbUser.addUser(username,password);
		dbUser.login(username, password);
		assertTrue("User shouldn't have any tasks yet",dbUser.taskCounter(username)==0);
		dbUser.addTask(description, detail, category, location, date, time);		
		dbUser.addTask(description+"xyz", detail, category+"xyz", location, "2011-12-21", time);
		dbUser.addTask(description+"rgb", detail, category+"rgb", location, "2013-12-21", time);
		assertTrue("User should have two tasks",dbUser.taskCounter(username)==3);
		ArrayList<String[]> filtered = dbUser.filter("", false, false, "");
		String[] descriptions = filtered.get(0);
		assertTrue("Three tasks expected",descriptions.length==3 && (descriptions[0].equals(description) && 
				    descriptions[1].equals(description+"xyz") && descriptions[2].equals(description+"rgb")));
		dbUser.setComplete(filtered.get(1)[1]);
		filtered = dbUser.filter("", true, false, "");
		descriptions = filtered.get(0);
		assertTrue("Two specific tasks expected",descriptions.length==2 && (descriptions[0].equals(description) && 
			    	descriptions[1].equals(description+"rgb")));
		filtered = dbUser.filter(category+"xyz", true, false, "");
		descriptions = filtered.get(0);
		assertTrue("No tasks should be fulfilling the parameters",descriptions.length==0);
		filtered = dbUser.filter(category+"rgb", true, false, "");
		descriptions = filtered.get(0);
		assertTrue("One specific task should be fulfilling the parameters",descriptions.length==1 && descriptions[0].equals(description+"rgb"));
		filtered = dbUser.filter("", false, true, date);
		descriptions = filtered.get(0);
		assertTrue("One specific task should be fulfilling the parameters",descriptions.length==1 && descriptions[0].equals(description+"rgb"));
		filtered = dbUser.filter("", true, true, "2010-12-21");
		descriptions = filtered.get(0);
		assertTrue("Two specific tasks should be fulfilling the parameters",descriptions.length==2 && (descriptions[0].equals(description) && descriptions[1].equals(description+"rgb")));
		dbUser.deleteUser(username);		
		dbUser.close();
	}
	
	/**
	 * Tests the setComplete() method of our database class
	 */
	@Test
	public void testSetComplete() {			
		dbUser.addUser(username,password);
		dbUser.login(username, password);
		assertTrue("User shouldn't have any tasks yet",dbUser.taskCounter(username)==0);
		dbUser.addTask(description, detail, category, location, date, time);		
		dbUser.addTask(description+"xyz", detail, category+"xyz", location, "2011-12-21", time);
		dbUser.addTask(description+"rgb", detail, category+"rgb", location, "2013-12-21", time);
		assertTrue("User should have two tasks",dbUser.taskCounter(username)==3);
		ArrayList<String[]> filtered = dbUser.filter("", false, false, "");
		String[] descriptions = filtered.get(0);
		assertTrue("Three tasks expected",descriptions.length==3 && (descriptions[0].equals(description) && 
				    descriptions[1].equals(description+"xyz") && descriptions[2].equals(description+"rgb")));
		dbUser.setComplete(filtered.get(1)[1]);
		filtered = dbUser.filter("", true, false, "");
		descriptions = filtered.get(0);
		assertTrue("Two specific tasks expected",descriptions.length==2 && (descriptions[0].equals(description) && 
			    	descriptions[1].equals(description+"rgb")));
		dbUser.setComplete(filtered.get(1)[0]);
		filtered = dbUser.filter("", true, false, "");
		descriptions = filtered.get(0);
		assertTrue("One specific task expected",descriptions.length==1 && descriptions[0].equals(description+"rgb"));
		dbUser.deleteUser(username);		
		dbUser.close();
	}
	
	/**
	 * Tests the deleteUser() method of our database class
	 */
	@Test
	public void testDeleteUser() {			
		dbUser.addUser(username,password);
		dbUser.addUser(username+"xyz",password);
		dbUser.addUser(username+"rgb",password);
		assertTrue("Three users must have been created, but apparently that isn't the case"
					,dbUser.hasUser(username) && dbUser.hasUser(username+"xyz") && dbUser.hasUser(username+"rgb"));
		dbUser.deleteUser(username);
		assertFalse("This user shouldn't exist",dbUser.hasUser(username));
		assertTrue("The other two should still exist", dbUser.hasUser(username+"xyz") && dbUser.hasUser(username+"rgb"));
		dbUser.deleteUser(username+"xyz");
		dbUser.deleteUser(username+"rgb");
		assertFalse("None of the three users should exist" ,dbUser.hasUser(username) && dbUser.hasUser(username+"xyz") && dbUser.hasUser(username+"rgb"));		
		dbUser.close();
	}
	
	
	
	
	
	protected void tearDown() throws Exception {
	    super.tearDown();	    
	  }

}
