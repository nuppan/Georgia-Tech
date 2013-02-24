package android.manager.view;

import java.sql.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.manager.database.Database;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.view.View.OnClickListener;
/**
 * The CreateTaskActivity class represents the visual instance of the task creation page. The activity presents the user with
 * four text fields to enter(all Strings, for now) a short description of the task, a detailed description of the task, the location
 * and the due date/time. All fields need to be attended for the Create button to work, clicking on which one adds the task to the database.
 * It also provides a cancel button which calls finish() to go back to the list activity and not create the task in the database.
 * @author Joon Ki Hong, Jonathan Betancourt, Griva Patel, Curtis Luckey. Amit Paduvalli 
 */
public class CreateTaskActivity extends Activity implements OnClickListener {
	
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
        setContentView(R.layout.createtask_layout);        
        Button createTaskButton = (Button) findViewById(R.id.createtask_createTaskButton);
        Button cancelTaskButton = (Button) findViewById(R.id.createtask_cancelTaskButton); 
        createTaskButton.setOnClickListener(this);
        cancelTaskButton.setOnClickListener(this);
        
    }
	
	/**
	 * This method handles clicks and displays the right Activity depending
	 * on the click. Clicking the create button creates the task in the database
	 * while the cancel button returns the user to the TaskListActivity 
	 * @param a view that contains the button information
	 */
	public void onClick(View v){
		
		final Context cont = this;
		String descTxt = ((EditText) findViewById(R.id.createtask_taskDescText)).getText().toString();
		String detailTxt = ((EditText) findViewById(R.id.createtask_taskDetails)).getText().toString();
		String categoryTxt = ((EditText) findViewById(R.id.createtask_category)).getText().toString();
		String locationTxt = ((EditText) findViewById(R.id.createtask_locationText)).getText().toString();
		String timeTxt = ((TimePicker) findViewById(R.id.createtask_time)).getCurrentHour().toString() + "." +
		((TimePicker) findViewById(R.id.createtask_time)).getCurrentMinute().toString();
		
		//Date formatting 
		String year = ((DatePicker) findViewById(R.id.createtask_date)).getYear()+"";
		
		String month = ((DatePicker) findViewById(R.id.createtask_date)).getMonth()+1+"";
		if ( month.length()<2){
			month = "0"+month;
		}
		String day = ((DatePicker) findViewById(R.id.createtask_date)).getDayOfMonth()+"";
		if (day.length()<2){
			day = "0"+day;
		}
		
		String date = year+"-"+month+"-"+day;
		
		switch(v.getId()){
		case R.id.createtask_createTaskButton:
			if(descTxt.length() > 0 && detailTxt.length() >0 && locationTxt.length()>0){				
				Database dbUser = new Database(CreateTaskActivity.this);
				dbUser.open();				
				dbUser.addTask(descTxt,detailTxt,categoryTxt,locationTxt,date,timeTxt);	//add the information to the database								
				dbUser.close();
				this.finish();
				startActivity(new Intent(cont, TaskListActivity.class));
	        }
			else{
				Toast.makeText(CreateTaskActivity.this,"Please complete all fields", Toast.LENGTH_LONG).show();  
			}
			break;
		case R.id.createtask_cancelTaskButton:
			this.finish();
			startActivity(new Intent(cont, TaskListActivity.class)); //returns to previous page
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
