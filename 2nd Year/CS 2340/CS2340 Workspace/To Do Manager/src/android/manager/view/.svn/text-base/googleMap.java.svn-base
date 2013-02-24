package android.manager.view;

import android.os.Bundle;
import android.view.Window;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
	 
/**
 * This class represents an Activity that displays the google maps. 
 * It allows the user to scroll and zoom in and out.
 * @author Joon Ki Hong, Jonathan Betancourt, Griva Patel, Curtis Luckey. Amit Paduvalli 
 */

public class googleMap extends MapActivity {
     
	private MapView mapView;
	  
	/**
	 * This method uses a saved instance object to create the Activity
	 * This method will also remove the title screen from the Activity
	 * and creates a mapview to present the google map element.
	 * 
	 * @param a saved instance state of an activity
	 */
	  public void onCreate(Bundle savedInstanceState) {
	         
	        super.onCreate(savedInstanceState);
	        this.requestWindowFeature(Window.FEATURE_NO_TITLE);// keeps the title from showing
	        setContentView(R.layout.google_layout); 
	         
	        mapView = (MapView) findViewById(R.id.map_view);// uses the google map view package element
	        mapView.setBuiltInZoomControls(true);
	         
	    }
	 
	  /**
	   * This method keeps routes from being displayed. Normally, routes
	   * are used for GPS driving.
	   */
    protected boolean isRouteDisplayed() {
	        return false;
    }
	     
}

