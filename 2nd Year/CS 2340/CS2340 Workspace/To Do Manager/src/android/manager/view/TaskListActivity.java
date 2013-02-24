package android.manager.view;

import java.util.ArrayList;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.manager.database.Database;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

/**
 * This class represents the visual instance of the screen that appears after 
 * successful login. It features a list of tasks created
 * by the current user. It has an Add Item button
 * which starts the CreateTaskActivity.
 * @author Joon Ki Hong, Jonathan Betancourt, Griva Patel, Curtis Luckey. Amit Paduvalli 
 */
public class TaskListActivity extends ListActivity implements OnClickListener{
	/**
	 * This method uses a saved instance object to create the Activity
	 * This method will also remove the title screen from the Activity
	 * and handle widgets.
	 * 
	 * @param a saved instance state of an activity
	 */
	
	/**
	 * The dialog used to prompt the user for filtering options
	 */
	private Dialog filterDialog;
	private ListView list;
	private String[] currList;
	ArrayAdapter<String> adapter;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        // The activity is being created and uses the Bundle if it exists
        setContentView(R.layout.tasklist_layout);
        setTitle("To Do List");
        
        //List View reference
        list = getListView();
        //Populate List View
        Database dbUser = new Database(TaskListActivity.this);
		dbUser.open();		
		currList = dbUser.filter("",false,false,"").get(1);
		String[] descriptions = dbUser.desclist();//adds the short descriptions of the tasks to an array.
		dbUser.close();
		adapter = new ArrayAdapter<String>(this,
		android.R.layout.simple_list_item_1, descriptions);//handles the array and displays the information in it.
		setListAdapter(adapter); 
		registerForContextMenu(list);
		
		//Filter Dialog initialization
		filterDialog = new Dialog(TaskListActivity.this);
	    filterDialog.setContentView(R.layout.tasklistfilter_layout);
		filterDialog.setTitle("Filter Your Tasks");
		filterDialog.setCancelable(true);
		
		//Create Button references and add listeners
		Button createButton = (Button) findViewById(R.id.tasklist_createButton);
        createButton.setOnClickListener(this);       
        Button mapButton = (Button) findViewById(R.id.tasklist_mapButton);
        mapButton.setOnClickListener(this); 
        Button filterButton = (Button) findViewById(R.id.tasklist_filterButton);
        filterButton.setOnClickListener(this);
		Button dialogFilterButton = (Button) filterDialog.findViewById(R.id.tasklist_filterDialogButton);
        dialogFilterButton.setOnClickListener(this);
        Button cancelDialogButton = (Button) filterDialog.findViewById(R.id.tasklist_cancelDialogButton);
        cancelDialogButton.setOnClickListener(this);
        
    }
	
	/**
	 * This method handles clicks and displays the right Activity depending
	 * on the click.
	 * 
	 * @param a view that contains the button information
	 */
	public void onClick(View v){		
		final Context cont = this;
		
		switch(v.getId()){
		//Create Button pressed
		case R.id.tasklist_createButton: //When Add a test is clicked
			startActivity(new Intent(cont, CreateTaskActivity.class));
			break;
		//Filter Button pressed
		case R.id.tasklist_filterButton:
			filterDialog.show();
			break;
			
		//Filter Dialog Button pressed
		case R.id.tasklist_filterDialogButton:
			
				//String specifying what to filter by
			String filterTxt = ((EditText) filterDialog.findViewById(R.id.tasklist_filterEditText)).getText().toString();
				//Boolean value that specifies whether or not to filter by uncompleted items
			boolean chkCompStatus = ((CheckBox) filterDialog.findViewById(R.id.tasklist_filterDlogCheckComp)).isChecked();
			boolean chkDateStatus = ((CheckBox) filterDialog.findViewById(R.id.tasklist_filterDlogDate)).isChecked();
			String date = "";
			if (chkDateStatus==true){
				//Date formatting 
				String year = ((DatePicker) filterDialog.findViewById(R.id.tasklist_dialogFilterDate)).getYear()+"";
				
				String month = ((DatePicker) filterDialog.findViewById(R.id.tasklist_dialogFilterDate)).getMonth()+1+"";
				if ( month.length()<2){
					month = "0"+month;
				}
				String day = ((DatePicker) filterDialog.findViewById(R.id.tasklist_dialogFilterDate)).getDayOfMonth()+"";
				if (day.length()<2){
					day = "0"+day;
				}
				
				date = year+"-"+month+"-"+day;
			}
				//Open database
			Database dbUser = new Database(TaskListActivity.this);
			dbUser.open();

				//Return the list of filtered items 
			ArrayList<String[]> filteredSet = dbUser.filter(filterTxt,chkCompStatus, chkDateStatus, date);
			String[] list = filteredSet.get(0);
			currList = filteredSet.get(1);
			
				//Close the database and set the filtered list
			dbUser.close();
			adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list);
			setListAdapter(adapter);
			
				//Close the dialog
			filterDialog.dismiss();
			break;
		//Cancel filter dialog button pressed
		case R.id.tasklist_cancelDialogButton:
			filterDialog.dismiss();
			break;
			
		//Map view Button pressed
		case R.id.tasklist_mapButton:
			startActivity(new Intent(cont, googleMap.class));
			break;
		}
	}
	

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
		if (v.getId()==getListView().getId()) {
			menu.setHeaderTitle("Menu");
			String[] menuItems = getResources().getStringArray(R.array.contmenu_list);
			for (int i = 0; i<menuItems.length; i++) {
				menu.add(Menu.NONE, i, i, menuItems[i]);
			}
		}
	}
	
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	  AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
	  int menuItemIndex = item.getItemId();
	  //String[] menuItems = getResources().getStringArray(R.array.contmenu_list);
	  //String menuItemName = menuItems[menuItemIndex];
	  String itemId = currList[info.position];
	  
	  Database dbUser = new Database(TaskListActivity.this);
	  dbUser.open();
	  
	  switch(menuItemIndex){
	  	  //Edit was selected
	  case 0:
		  break;
		  //Delete was selected
	  case 1:
		  dbUser.delete(itemId);
		  ArrayList<String[]> filteredSet = dbUser.filter("",false, false,"");
		  String[] list = filteredSet.get(0);
		  currList = filteredSet.get(1);
		  adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list);
		  setListAdapter(adapter);
		  Toast.makeText(TaskListActivity.this,"Task deleted", Toast.LENGTH_LONG).show();
		  break;
		  //Mark as complete was selected
	  case 2:
		  dbUser.setComplete(itemId);
		  break;
	  }		
	  dbUser.close();

	 // TextView text = (TextView)findViewById(R.id.footer);
	  //text.setText(String.format("Selected %s for item %s", menuItemName, listItemName));
	  return true;
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
