package com.example.knowledgecenter;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.knowledgecenter.HomeActivity.PlaceholderFragment.DownloadWebPageTask;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends ActionBarActivity implements OnClickListener {

	protected static StringBuilder result;
	protected static JSONArray responseArray;
	protected List<NameValuePair> parameters;
	protected ProgressDialog nDialog;
	protected String email;
	protected String password;
	protected EditText txtEmail;
	protected EditText txtName;
	protected EditText txtSurname;
	protected EditText txtPassword;
	protected TextView txtErrorText;
	public static int httpStatusCode;
	public static JSONTokener tokener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserDataSource usds = new UserDataSource(this);
        usds.open();
        
        //getDBFile();
        //usds.Drop();
        //usds.deleteAll();
        //usds.deleteDB();
        List<User> userInfo = usds.getAllUserDetails();
         if(userInfo.size() == 0)
         {
        	 getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
             getActionBar().hide();
             setContentView(R.layout.activity_login);
             //setContentView(R.layout.login_acitivty);
             Button btnSignin = (Button) findViewById(R.id.btnSignin);
             btnSignin.setOnClickListener(this);
             TextView txtCreate = (TextView) findViewById(R.id.btnAskKC);
             txtCreate.setOnClickListener(this);
             txtEmail = (EditText)findViewById(R.id.txtEmailLogin);
             txtPassword = (EditText)findViewById(R.id.txtPasswordLogin);
             txtErrorText = (TextView)findViewById(R.id.txtErrorText);
             txtName = (EditText)findViewById(R.id.txtFirstName);
             txtSurname = (EditText)findViewById(R.id.txtLastName);
             
         }else{
        	
        	Intent intent = new Intent(this, HomeActivity.class);
        	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		    startActivity(intent);
        }
       

    }
    
    public void getDBFile(){
    	
    	File f=new File("/data/data/com.example.knowledgecenter/databases/SB.db");
    	FileInputStream fis=null;
    	FileOutputStream fos=null;

    	try
    	{
    	  fis=new FileInputStream(f);
    	  fos=new FileOutputStream("/mnt/sdcard/user.db");
    	  while(true)
    	  {
    	    int i=fis.read();
    	    if(i!=-1)
    	    {fos.write(i);}
    	    else
    	    {break;}
    	  }
    	  fos.flush();
    	  Toast.makeText(this, "DB dump OK", Toast.LENGTH_LONG).show();
    	}
    	catch(Exception e)
    	{
    	  e.printStackTrace();
    	  Toast.makeText(this, "DB dump ERROR", Toast.LENGTH_LONG).show();
    	}
    	finally
    	{
    	  try
    	  {
    	    fos.close();
    	    fis.close();
    	  }
    	  catch(IOException ioe)
    	  {
    		  Log.d("Error", ioe.getMessage());
    		  Toast.makeText(this, "DB dump ERROR", Toast.LENGTH_LONG).show();
    	  }
    	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /**int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }**/
        return super.onOptionsItemSelected(item);
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				if (v.getId() == R.id.btnAskKC){
					//getDBFile();
					Intent intent = new Intent(this, Register2Activity.class);
				    startActivity(intent);
				}
				
				if(v.getId()== R.id.btnSignin)
				{
					parameters = new ArrayList<NameValuePair>();
					parameters.add(new BasicNameValuePair("username", txtEmail.getText().toString()));
					parameters.add(new BasicNameValuePair("password", txtPassword.getText().toString()));
					nDialog = new ProgressDialog(this); 
              		
              		nDialog.setMessage("Logging In..");
              		nDialog.show();
					new Authenticate(this).execute("");
				    
					//Intent intent = new Intent(this, HomeActivity.class);
				    //startActivity(intent);
				}
	
	}
	
	public static String getUserInfo(final List<NameValuePair> parameters)
	{
	 
		try {
			//String url = "https://server.com/stuff"
			Thread thread = new Thread(new Runnable(){
				
			    @Override
			    public void run() {
			        try {
			        	
			        	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
						nameValuePairs.add(new BasicNameValuePair("count", "5"));
						HttpClient httpClient = new DefaultHttpClient();
						String paramsString = URLEncodedUtils.format(parameters, "UTF-8");
						HttpGet httpGet = new HttpGet(Connector.server + "UserInformation"+"?" + paramsString);
						HttpResponse response = httpClient.execute(httpGet);
						
						BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
						StringBuilder builder = new StringBuilder();
						for (String line = null; (line = reader.readLine()) != null;) {
						    builder.append(line).append("\n");
						}
						 tokener = new JSONTokener(builder.toString());
						//JSONArray s = new JSONArray(tokener);
						httpStatusCode = response.getStatusLine().getStatusCode();
						result = builder;
						
						
						//return result;
			            //Your code goes here
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
			    }
			});

			thread.start();
			thread.join();
			
					
	        
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	
	 class Authenticate extends AsyncTask<String, Void, String> {
		 Activity mContex;
		 
		 public  Authenticate(Activity contex)
		    {
		     
		     this.mContex=contex;
		    }
	        @Override
	        protected String doInBackground(String... urls) {
	        	//result = loadContent();
	        	getUserInfo(parameters);
	        	
	        	return null;
	        }

	        @Override
	        protected void onPostExecute(String resultss) {
	        	nDialog.cancel();
	        	if(httpStatusCode == 200 || httpStatusCode == 202)
	        	{
	        		
	        		String name = "";
	        		String surname = "";
	        		UserDataSource user = new UserDataSource(mContex);
	        		
	        		user.open();
	        		//for(int i = 0; i < responseArray.length(); i++)
					{
	        			JSONObject jsonobject;
						try {
							jsonobject = new JSONObject(tokener);
							name = jsonobject.getString("FirstName");
							 surname = jsonobject.getString("LastName");
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					   
					}
	        		try{
	        			if(txtName.getText().toString() !=null)
		        		{
		        			name = txtName.getText().toString();
		        			surname = txtSurname.getText().toString();
		        		}
	        		}catch(Exception ex)
	        		{
	        			ex.printStackTrace();
	        		}
	        		
	        		user.createUser(txtEmail.getText().toString(), "", txtPassword.getText().toString(), name, surname, "BU001");
	        		Intent intent = new Intent(mContex, HomeActivity.class);
	        		//Intent intent = new Intent(context, HomeActivity.class);
	    			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
	    			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        		startActivity(intent);
	        	}
	        	else
	        	{
	        		txtErrorText.setVisibility(View.VISIBLE);
		        	String error = result.toString();
		        	error = error.replaceAll("\"", "");
		        	txtErrorText.setText(error);
	        	}
	        	
	        	
	         // textView.setText(result);
	        }
	      }
}
