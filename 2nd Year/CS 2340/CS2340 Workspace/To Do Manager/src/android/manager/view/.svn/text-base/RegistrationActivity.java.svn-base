package android.manager.view;

import android.app.Activity;
import android.manager.database.Database;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.view.View.OnClickListener;

/**
 * The Registration Activity class represents the visual instance of the 
 * registration page. It is an activity launched after clicking Sign up. 
 * The activity presents the user with two fields to type their email address and a 
 * password. Clicking on the button register will save their data and will be used
 * to log them in. There is a back button to go back to the login screen.
 * to the Login Activity.
 * @author Joon Ki Hong, Jonathan Betancourt, Griva Patel, Curtis Luckey. Amit Paduvalli 
 *
 */
public class RegistrationActivity extends Activity implements OnClickListener{
	
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
        setContentView(R.layout.registration_layout);
        
        Button createAccountButton = (Button) findViewById(R.id.registration_createAccountButton);
     
        createAccountButton.setOnClickListener(this);
    
    }
	
	/**
	 * This method handles clicks and displays the right Activity depending
	 * on the click.
	 * 
	 * @param a view that contains the button information
	 */
	public void onClick(View v){

		String userNameTxt = ((EditText) findViewById(R.id.registration_userNameText)).getText().toString();
		String passWordTxt = ((EditText) findViewById(R.id.registration_passWordText)).getText().toString();
		String passWordConfTxt = ((EditText) findViewById(R.id.registration_passWordConfText)).getText().toString();

		switch(v.getId()){
		case R.id.registration_createAccountButton:
			if(userNameTxt.length() > 0 && passWordTxt.length() >0 && passWordConfTxt.length()>0){
				if(passWordTxt.equals(passWordConfTxt)){
					Database dbUser = new Database(RegistrationActivity.this);
					dbUser.open();           
					if(dbUser.hasUser(userNameTxt)){
						Toast.makeText(RegistrationActivity.this,"Please choose another username", Toast.LENGTH_LONG).show();
					}
					else{
						dbUser.addUser(userNameTxt, passWordTxt);  
						Toast.makeText(RegistrationActivity.this,"Account created!", Toast.LENGTH_LONG).show();
					}
					dbUser.close();
				}
				else{
					Toast.makeText(RegistrationActivity.this,"Please check your passwords", Toast.LENGTH_LONG).show();
				}
	        }     
		break;
		}
			
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
