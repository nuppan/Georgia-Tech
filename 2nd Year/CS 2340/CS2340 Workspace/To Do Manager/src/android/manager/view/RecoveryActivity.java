package android.manager.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * The Recovery Activity class represents the visual instance of the 
 * password recovery page. It is an activity launched after the Login Activity. 
 * The activity presents the user with a field to type their email address and a button
 * to send a generic code to that email. The return button allows the user to return
 * to the Login Activity.
 * @author Joon Ki Hong, Jonathan Betancourt, Griva Patel, Curtis Luckey. Amit Paduvalli 
 *
 */
public class RecoveryActivity extends Activity{
	/**
	 * This method uses a saved instance object to create the Activity
	 * This method will also remove the title screen from the Activity
	 * and handle widgets.
	 * 
	 * @param a saved instance state of an activity
	 */
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // The activity is being created.
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); //Removes the default title bar
        setContentView(R.layout.recovery_layout);
    }
	
	/**
	 * This method is essential for the Activity to start.
	 * 
	 */
    @Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.
    }
    
    /**
	 * This method is essential for the Activity to resume after starting up.
	 * 
	 */
    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
    }
    
    /**
	 * This method is essential for the Activity to go into the background
	 * but not be killed.
	 * 
	 */
    @Override
    protected void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
    }
    
    /**
   	 * This method is essential for the Activity to initiate onDestroy or
   	 * onRestart after the it is no longer visible to the user.
   	 * 
   	 */
    @Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }
    
    /**
   	 * This method is essential for the Activity to perform any final cleanups
   	 * before an activity is destroyed. 
   	 * 
   	 */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
    }
}
