package com.example.knowledgecenter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.R.string;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Pair;

public class Connector extends AsyncTask<String, Void, String> {

	public HttpPost httpClientPost;
	public HttpGet httpGet;
	public HttpClient httpClient;
	public HttpPut httpPut;
	//public static String server = "http://197.96.136.37:8080/SBKnowledgeCentre/";
	public static String server = "http://smacbooks.azurewebsites.net/api/";
	//public static String server = "http://localhost:1593/api/";
	public JSONArray result;
	public static String type;
	public Context context;
	public ProgressDialog pd;
	public Connector(Context _context)
	{
		context = _context;
		httpClient = new DefaultHttpClient();
	}
	
	@SuppressWarnings("unchecked")
	public List<NameValuePair> getParameters(Object _obj) throws IllegalAccessException, IllegalArgumentException
	{
		List<NameValuePair> rtList = new ArrayList<NameValuePair>();
		for(Field f : _obj.getClass().getFields()) 
		{
			if (f.get(_obj) != null)
			rtList.add(new BasicNameValuePair(f.getName(), f.get(_obj).toString()));
		}
	
		return rtList;
	}
	
	public String buildUrl(List<NameValuePair> parameters, String server, String service)
	{
		String rtUrl = server + service;
		for(NameValuePair item: parameters)
		{
			//if(item.keySet().toString() != null || item.second != null )
		//	{
			//	rtUrl = rtUrl +"?"+ item.first.toString() + "=" +item.second.toString();
		//	}
			
		}
		return rtUrl;
	}
	
	public void buildPost(List<NameValuePair> parameters, String service, String type) throws URISyntaxException, ClientProtocolException, IOException
	{
		
			
	
		/**if (type == "GET")
		{
			String paramsString = URLEncodedUtils.format(parameters, "UTF-8");
			httpGet = new HttpGet(server+service+"?"+paramsString);
			//((HttpResponse) httpGet).setEntity(new UrlEncodedFormEntity(parameters));
			type = "GET";
		}
		else
		{**/
			
			httpPut = new HttpPut(server+service);
			httpPut.setEntity(new UrlEncodedFormEntity(parameters));
			type = "PUT";
		//}
		
		//httpClientPost.setURI(new URI(server+service));
		
	}
	
	@Override
    protected void onPreExecute()
    {
		pd = ProgressDialog.show(context, "", "Please wait, creating account...", true);
		   //do initialization of required objects objects here                
    };

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try {
			
				//if(type == "PUT")
				//{
				
				HttpResponse response = httpClient.execute(httpPut);
				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
				StringBuilder builder = new StringBuilder();
				for (String line = null; (line = reader.readLine()) != null;) {
				    builder.append(line).append("\n");
				}
				JSONTokener tokener = new JSONTokener(builder.toString());
				result = new JSONArray(tokener);
				/**}else
				{
					HttpResponse response = httpClient.execute(httpGet);
					BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
					StringBuilder builder = new StringBuilder();
					for (String line = null; (line = reader.readLine()) != null;) {
					    builder.append(line).append("\n");
					}
					JSONTokener tokener = new JSONTokener(builder.toString());
					result = new JSONArray(tokener);
				}**/
			
			
            
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	 @Override
     protected void onPostExecute(String resultss) {
		 	UserDataSource datasource = new UserDataSource(context);
		    datasource.open();
		    datasource.deleteAll();
			//User u = datasource.createUser(UserBO.email, UserBO.prefs)	;
		    
			datasource.insertUser(UserBO.EmailAddress, UserBO.Preferences,UserBO.Password, UserBO.BUID);
			Intent intent = new Intent(context, HomeActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
			pd.dismiss();
      // textView.setText(result);
     }
}
