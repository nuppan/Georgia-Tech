package android.manager.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.manager.database.Database;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.view.View.OnClickListener;
/**
 * The Login Activity class represents the visual instance of the login page. It is considered the main activity which is launched
 * upon initialization of the application. The activity presents the user with two text fields to enter a valid username and 
 * password. It also provides two buttons (Login and Sign Up!) that will initialize the TaskList Activity and the
 * Registration Activity respectively. On successful login a user gets marked as the current user.
 * @author Joon Ki Hong, Jonathan Betancourt, Griva Patel, Curtis Luckey. Amit Paduvalli 
 */
public class LoginActivity extends Activity implements OnClickListener{
	
	/**
	 * This method uses a saved instance object to create the Activity
	 * This method will also remove the title screen from the Activity
	 * and handle widgets.
	 * 
	 * @param a saved instance state of an activity
	 */
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // The activity is being created and uses the Bundle if it exists
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); //Removes the default title bar
        setContentView(R.layout.login_layout);//method call to use the login_layout.xml from resources through the R class.
        
        Button loginButton = (Button) findViewById(R.id.login_loginButton);
        Button registerButton = (Button) findViewById(R.id.login_registerButton);
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        
    }
	
	/**
	 * This method handles clicks and displays the right Activity depending
	 * on the click.
	 * 
	 * @param a title for the alert box
	 * @param a message to display on the alert box.
	 */
	protected void alertbox(String title, String mymessage)
	   {
	   new AlertDialog.Builder(this)
	      .setMessage(mymessage)
	      .setTitle(title)
	      .setCancelable(true)
	      .setNeutralButton(android.R.string.cancel,
	         new DialogInterface.OnClickListener() {
	         public void onClick(DialogInterface dialog, int whichButton){}
	         })
	      .show();
	   }
	
	/**
	 * This method handles clicks and displays the right Activity depending
	 * on the click.
	 * 
	 * @param a view that contains the button information
	 */
	public void onClick(View v){
		final Context cont = this;
		String userNameTxt = ((EditText) findViewById(R.id.login_userNameText)).getText().toString();
		String passWordTxt = ((EditText) findViewById(R.id.login_passWordText)).getText().toString();
		
		switch(v.getId()){
		case R.id.login_loginButton:
			if(userNameTxt.length() > 0 && passWordTxt.length() >0){
				Database dbUser = new Database(LoginActivity.this);
				dbUser.open();           
				if(dbUser.login(userNameTxt, passWordTxt)){
					
					Toast.makeText(LoginActivity.this,"Successfully Logged In", Toast.LENGTH_LONG).show();
					startActivity(new Intent(cont, TaskListActivity.class)); // On successful login the TaskListActivity is started
					
				}
				else{
					Toast.makeText(LoginActivity.this,"Invalid Username/Password", Toast.LENGTH_LONG).show();              
				}
				dbUser.close();
			}      
			break;
		case R.id.login_registerButton:
			startActivity(new Intent(cont, RegistrationActivity.class));
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
