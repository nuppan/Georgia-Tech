package com.example.myfirstapp;

import java.io.*;
import java.net.*;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.*;
import android.view.View.*;
import android.widget.*;

public class MainActivity extends Activity implements OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Set onclick listener
        Button sendMessageButton = (Button) findViewById(R.id.send_button);
        sendMessageButton.setOnClickListener(this);
        
    }
    
    public void onClick(View v){
    	switch(v.getId()){
		case R.id.send_button:{
			String message = ((TextView)findViewById(R.id.text_view)).getText().toString();
			//send message asynchronously
			new AsyncSend().execute(message);
			break;
		}
	}
		
	}
    
    private class AsyncSend extends AsyncTask<String, Object, String>{

		@Override
		protected String doInBackground(String... messages) {
		     
			        try {
			        	Log.i("AsyncTask","Creating Socket");
						Socket socket = new Socket("10.0.2.2",8080);
						Log.i("AsyncTask","Socket Created");
						//Log server info
						Log.i("AsyncTask",socket.toString());
						//Create output stream
						PrintWriter output = new PrintWriter(socket.getOutputStream());
						//Log and send the output
						Log.i("AsyncTask",messages[0]);
						output.println(messages[0]);
						output.flush();
						//Create input stream
						BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						//Log data from socket
						String result = input.readLine();
						Log.i("AsyncTask",result);
						//close connection
						socket.close();			
						return result;		
					} catch (UnknownHostException e) {	
						e.printStackTrace();
						Toast.makeText(MainActivity.this,"Host Exception", Toast.LENGTH_LONG).show();
						return null;
					} catch (IOException e) {	
						Toast.makeText(MainActivity.this,"IO Exception", Toast.LENGTH_LONG).show();
						e.printStackTrace();
						return null;
					} catch(RuntimeException e){
						Toast.makeText(MainActivity.this,"Runtime Exception", Toast.LENGTH_LONG).show();
						e.printStackTrace();
						return null;
					}		
		}
		
		protected void onPostExecute(String result){
			//Update UI after background task is done
			TextView resultView = (TextView)findViewById(R.id.result_tv);
		    resultView.setText(result);
		}
    	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
