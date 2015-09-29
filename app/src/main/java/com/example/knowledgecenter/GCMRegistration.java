package com.example.knowledgecenter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;

import com.google.android.gcm.GCMRegistrar;

	public class GCMRegistration extends AsyncTask<String, Void, String> {
		 Activity context;
		 UserBO user;
		 Connector controller;
		 BroadcastReceiver mHandleMessageReceiver;
		 public String RegistrationID;
		 public GCMRegistration(Activity _context, UserBO _user, Connector _controller, BroadcastReceiver _mHandleMessageReceiver)
		    {	
			 	context = _context;
			 	user = _user;
			 	controller = _controller;
			 	mHandleMessageReceiver = _mHandleMessageReceiver;
			 	
		    }
	        public GCMRegistration() {
			// TODO Auto-generated constructor stub
		}
			@Override
	        protected String doInBackground(String... urls) {
	        	GCMRegistrar.checkDevice(context.getApplicationContext());

  			// Make sure the manifest permissions was properly set 
  			GCMRegistrar.checkManifest(context.getApplicationContext());

  			//lblMessage = (TextView) findViewById(R.id.lblMessage);
  			
  			// Register custom Broadcast receiver to show messages on activity
  			context.registerReceiver(mHandleMessageReceiver, new IntentFilter(
  					Config.DISPLAY_MESSAGE_ACTION));
  			
  			RegistrationID = GCMRegistrar.getRegistrationId(context);
  			 
  			 if (RegistrationID.equals("")) {
  					
  					// Register with GCM			
  					GCMRegistrar.register(context, Config.GOOGLE_SENDER_ID);
  					RegistrationID = GCMRegistrar.getRegistrationId(context);
  					
  					
  				}else {
  					

  					GCMRegistrar.setRegisteredOnServer(context, true);
  					RegistrationID = GCMRegistrar.getRegistrationId(context);
  				}
  			 
	        	return RegistrationID;
	        }

	        @Override
	        protected void onPostExecute(String resultss) {
	        	
	        
              
	        	
	        
	      }

	
}
