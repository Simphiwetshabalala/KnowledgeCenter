package com.example.knowledgecenter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.knowledgecenter.LoginActivity.Authenticate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;
	private static Connector con;

	protected static JSONArray result;
	public static String username;
	public static String password;
	public static List<ArticleHeader> articleHeaderList = new ArrayList<ArticleHeader>();
	public static List<User> user;
	public static JSONArray ListPreferences;
	public static List<NameValuePair> prefDic;
	public static ProgressBar progressBarHome;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		//super.onCreate(savedInstanceState);
		if(savedInstanceState!=null){
            super.onCreate(null);
        } else {
            super.onCreate(savedInstanceState);
        }
		setContentView(R.layout.activity_home);
	    
	    //requestWindowFeature(Window.FEATURE_NO_TITLE);
	
		 android.app.ActionBar actionbar = this.getActionBar();
		
		 
		 //ActionBar actionbar = this.getSupportActionBar();
		 
			
		 //actionbar.setTitle("Registration - General");
		 actionbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.sbBlue)));
		
		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
		
		//Get stored user and and get Articles
	}
	
	public static JSONArray getContent(final List<NameValuePair> parameters)
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
						HttpGet httpGet = new HttpGet(Connector.server + "Content"+"?" + paramsString);
						HttpResponse response = httpClient.execute(httpGet);
						BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
						StringBuilder builder = new StringBuilder();
						for (String line = null; (line = reader.readLine()) != null;) {
						    builder.append(line).append("\n");
						}
						JSONTokener tokener = new JSONTokener(builder.toString());
						result = new JSONArray(tokener);
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
		return result;
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		switch(position)
		{
			case 0:
				FragmentManager fragmentManager = getSupportFragmentManager();
				fragmentManager
						.beginTransaction()
						.replace(R.id.container,
								PlaceholderFragment.newInstance(position + 1)).commit();
			break;
			case 1:
				FragmentManager fragmentManager1 = getSupportFragmentManager();
				fragmentManager1
						.beginTransaction()
						.replace(R.id.container,
								AskKCFragment.newInstance(position + 1)).commit();	
			break;
			case 2:
				FragmentManager fragmentManager8 = getSupportFragmentManager();
				fragmentManager8
						.beginTransaction()
						.replace(R.id.container,
								MyPreferencesFragment.newInstance(position + 1)).commit();
		    break;
			case 3:
				FragmentManager fragmentManager9 = getSupportFragmentManager();
				fragmentManager9
						.beginTransaction()
						.replace(R.id.container,
								ProfileFragment.newInstance(position + 1)).commit();
		break;
				
		}
		/**FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager
				.beginTransaction()
				.replace(R.id.container,
						PlaceholderFragment.newInstance(position + 1)).commit();**/
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section1);
			break;
		/**case 2:
			mTitle = getString(R.string.title_section2);
			break;
		case 3:
			mTitle = getString(R.string.title_section3);
			break;**/
		case 4:
			mTitle = getString(R.string.title_section9);
			break;
		case 5:
			mTitle = getString(R.string.title_section10);
			break;
		case 6:
			mTitle = getString(R.string.title_section6);
			break;
		case 8:
			mTitle = getString(R.string.title_section9);
			break;
		case 9:
			mTitle = getString(R.string.title_section10);
			break;
			
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			//getMenuInflater().inflate(R.menu.home, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends ListFragment  {
		
		
	    private ListView listView;
	    private ArticleHeaderAdapter adapter;
	    private SwipeRefreshLayout pullToRefresh;
	 
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			//getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
			View rootView = inflater.inflate(R.layout.fragment_home, container,
					false);
		    pullToRefresh = (SwipeRefreshLayout)rootView.findViewById(R.id.pullToRefresh);
			
		    pullToRefresh.setOnRefreshListener(new OnRefreshListener() {

	            @Override
	            public void onRefresh() {
	                // TODO Auto-generated method stub

	                refreshContent();

	            }
	        });
		    progressBarHome = (ProgressBar)getActivity().findViewById(R.id.progressBarHome);
			 
			return rootView;
		}
		
		private void refreshContent()
		{ 
			Toast toast = Toast.makeText(getActivity(), "Test", android.widget.Toast.LENGTH_SHORT);
			//toast.show();
			// DownloadWebPageTask task = new DownloadWebPageTask();
			  //  task.execute(new String[] { "http://www.vogella.com" });
			 setRetainInstance(true);
			 
				new DownloadWebPageTask(getActivity()).execute("");
			
			    
			//ssloadContent();
			//((PullToRefreshListView)getListView()).onRefreshComplete();	
			//pullToRefresh.getListView();
			 //listView.onRefreshComplete();
			
		}
		
		 @Override 
		 public void onViewCreated (View view, Bundle savedInstanceState) {
			 progressBarHome = (ProgressBar)getActivity().findViewById(R.id.progressBarHome);
			 //progressBarHome.setVisibility(View.GONE);
			 
			 //getActivity().requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
				
			 
			 listView = getListView();
		        adapter = new ArticleHeaderAdapter(getActivity(), articleHeaderList);
		        listView.setAdapter(adapter);
		        listView.setOnItemClickListener(new OnItemClickListener() {


					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						articleHeaderList.get(position);
						Intent intent = new Intent(getActivity(),ArticleActivity.class);
						String title = articleHeaderList.get(position).title;
						String body = articleHeaderList.get(position).body;
						 intent.putExtra("Header", title);
						 intent.putExtra("Body", body);
						 startActivity(intent);
					}
		        });
		        /**ArticleHeader artle = new ArticleHeader();
		        artle.title = "Testing";
		        articleHeaderList.add(artle);**/
		        
		    
		 }
		 


		@Override
		public void onAttach(Activity activity) {
			
			super.onAttach(activity);
			//progressBarHome = (ProgressBar)getActivity().findViewById(R.id.progressBarHome);
			
			((HomeActivity) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
			
			android.app.ActionBar actionbar = getActivity().getActionBar();
			 actionbar.setTitle("Registration - General");
			 actionbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.sbBlue)));
			 
			 //actionbar.setBackgroundDrawable(blue);
			// buildArticleList(loadContent());
			 new DownloadWebPageTask(getActivity()).execute("");
			 //progressBarHome.setVisibility(View.GONE);
		}
		
		public JSONArray loadContent()
		{
			//articleHeaderList.clear();
			
			 
			    try {
					//con.buildPost(paramters, "Content","GET");
					//con.execute().get();
			    	UserDataSource datasource = new UserDataSource(getActivity());
				    datasource.open();
				    //con = new Connector();
				    user = datasource.getAllUserDetails();
				    JSONArray result;
				    
				    List<NameValuePair> paramters = new ArrayList<NameValuePair>();
				    
				     //paramters.add(new BasicNameValuePair("requesttype","content"));
				     paramters.add(new BasicNameValuePair("username", user.get(0).getEmail()));
				     paramters.add(new BasicNameValuePair("password", user.get(0).getPass()));
				     username = user.get(0).getEmail();
				     password = user.get(0).getPass();
					
					
					result = getContent(paramters);
					//progressBarHome.setVisibility(View.GONE);
					return result;
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    return null;
			    
			   

			
		}
		
		 public void buildArticleList(JSONArray result)
		 {
			 try{
				 articleHeaderList.clear();
				 
					if (articleHeaderList.size() == 0)
					{
						for(int i = 0; i < result.length(); i++)
						{
							JSONObject jsonobject = result.getJSONObject(i);
						    ArticleHeader article = new ArticleHeader();
							article.title = jsonobject.getString("Title");
							article.time = jsonobject.getString("DateCreated");
							//article.prefs = jsonobject.getString("preferences");
							article.body = jsonobject.getString("Body");
							articleHeaderList.add(article);
						}
						
						
						adapter.notifyDataSetChanged();
				        listView.invalidateViews();
				        listView.refreshDrawableState();
					}
			 }catch(Exception ex)
			 {
				 ex.printStackTrace();
			 }
			
				
			 
		 }
		 class DownloadWebPageTask extends AsyncTask<String, Void, String> {
			 Activity mContex;
			 
			 public  DownloadWebPageTask(Activity contex)
			    {
			     
			     this.mContex=contex;
			    }
		        @Override
		        protected String doInBackground(String... urls) {
		        	result = loadContent();
		        	return null;
		        }

		        @Override
		        protected void onPostExecute(String resultss) {
		        	pullToRefresh.setRefreshing(false);
		        	buildArticleList(result);
		        	progressBarHome.setVisibility(View.GONE);
		        	Handler handler = new Handler();
		         // textView.setText(result);
		        }
		      }
		
	}

	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class AskKCFragment extends ListFragment {
		
		
	    private ListView listView;
	    private ArticleHeaderAdapter adapter;
	 
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		Button btnAskKC;
		EditText txtAskKC;
		ProgressDialog nDialog;
		public static AskKCFragment newInstance(int sectionNumber) {
			AskKCFragment fragment = new AskKCFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			
			
			return fragment;
		}

		public AskKCFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_ask_kc, container,
					false);
			
			 //actionbar.setBackgroundDrawable(blue);
			
			return rootView;
		}
		
		 @Override 
		 public void onViewCreated (View view, Bundle savedInstanceState) {
			 ActionBar actionbar = ((ActionBarActivity)getActivity()).getSupportActionBar();
			 //actionbar.setTitle("Registration - General");
			 actionbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.sbBlue)));
			 btnAskKC = (Button)getActivity().findViewById(R.id.btnAskKC);
			 txtAskKC = (EditText)getActivity().findViewById(R.id.txtAskKC);
			 btnAskKC.setOnClickListener(new View.OnClickListener() 
	           {

	             @Override
	              public void onClick(View v) 
	              {
	            	   Email email = new Email();
	            	   email.setBody(txtAskKC.getText().toString() + "From "+user.get(0).getEmail() );
	            	   email.setFrom("maintenance@smacbooks.co.za");
	            	   email.setTo("maintenance@smacbooks.co.za");
	            	   email.setSubject("ASK KC");
	            	   
	            	   List<NameValuePair> list = new ArrayList();
	            	   list.add(new BasicNameValuePair("From", email.getFrom()));
	            	   list.add(new BasicNameValuePair("To", email.getTo()));
	            	   list.add(new BasicNameValuePair("Body", email.getBody()));
	            	   list.add(new BasicNameValuePair("Subject", email.getSubject()));
	            	   nDialog = new ProgressDialog(getActivity()); 
	              		
	              		nDialog.setMessage("Sending Request");
	              		nDialog.show();
						//new Authenticate(this).execute("");
	            	   new AskKCTask(getActivity(), list).execute("");
	              }
	          });
		 }

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((HomeActivity) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
		}
		
		 
		
		 class AskKCTask extends AsyncTask<String, Void, String> {
		 	 Activity mContex;
		 	 List<NameValuePair> parameters;
		 	 public AskKCTask(Activity contex, List<NameValuePair> _parameters)
		 	    {
		 	     	this.mContex=contex;
		 	     	parameters = _parameters;
		 	    }
		        @Override
		        protected String doInBackground(String... urls) {
		        	//result = loadContent();
		        	try{
		        		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		 				nameValuePairs.add(new BasicNameValuePair("count", "5"));
		 				HttpClient httpClient = new DefaultHttpClient();
		 				String paramsString = URLEncodedUtils.format(parameters, "UTF-8");
		 				HttpPost httpPost = new HttpPost(Connector.server + "Email");
		 				httpPost.setEntity(new UrlEncodedFormEntity(parameters));
		 				HttpResponse response = httpClient.execute(httpPost);
		 				
		 				/**BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
		 				StringBuilder builder = new StringBuilder();
		 				for (String line = null; (line = reader.readLine()) != null;) {
		 				    builder.append(line).append("\n");
		 				}
		 				JSONTokener tokener = new JSONTokener(builder.toString());
		 				//ListPreferences = new JSONArray(tokener);**/
		 	        	return null;
		 	     
		        	}catch(Exception ex)
		        	{
		        		ex.printStackTrace();
		        	}
		        	return null;
		        }

		        @Override
		        protected void onPostExecute(String resultss) {
		        	nDialog.cancel();
		        	txtAskKC.setText("");
		        	//Context context = getApplicationContext();
		        	CharSequence text = "Request Sent";
		        	int duration = Toast.LENGTH_SHORT;

		        	Toast toast = Toast.makeText(mContex, text, duration);
		        	toast.show();
		        	//pullToRefresh.setRefreshing(false);
		        	//buildArticleList(result);
		         // textView.setText(result);
		        }
		      }
		 
	}
	
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class ProfileFragment extends ListFragment {
		
		
	    private ListView listView;
	    private ArticleHeaderAdapter adapter;
	    protected EditText txtName;
	    protected EditText txtSurname;
	    protected EditText txtEmail;
	 
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static ProfileFragment newInstance(int sectionNumber) {
			ProfileFragment fragment = new ProfileFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public ProfileFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_profile, container,
					false);
			
			 //actionbar.setBackgroundDrawable(blue);
			
			return rootView;
		}
		
		 @Override 
		 public void onViewCreated (View view, Bundle savedInstanceState) {
			 ActionBar actionbar = ((ActionBarActivity)getActivity()).getSupportActionBar();
			 //actionbar.setTitle("Registration - General");
			 actionbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.sbBlue)));
			 txtName = (EditText)getActivity().findViewById(R.id.txtFirstNameProfile);
			 txtSurname = (EditText)getActivity().findViewById(R.id.txtLastNameProfile);
			 txtEmail = (EditText)getActivity().findViewById(R.id.txtMAilProfile);
			 txtName.setText(user.get(0).getName());
			 txtSurname.setText(user.get(0).getSurname());
			 txtEmail.setText(user.get(0).getEmail());
					 //user
			 Button btnLogout = (Button)getActivity().findViewById(R.id.btnLogout);
			 btnLogout.setOnClickListener(new View.OnClickListener() 
	           {

	               @Override
	              public void onClick(View v) 
	              {
	            	   try
	            	   {
	            		   UserDataSource ud = new UserDataSource(getActivity());
	            		   ud.open();
	            		   //ud.close();
	 	            	   ud.deleteAll();
	 	            	   Intent intent = new Intent(getActivity(), LoginActivity.class);
	 	            	   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
	 	            	   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	 	            	   startActivity(intent);
	 	            	   //getActivity().finish();
	            	   }catch(Exception ex)
	            	   {
	            		   ex.printStackTrace();
	            		   Intent intent = new Intent(getActivity(), LoginActivity.class);
	 	            	   intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY); 
	 	            	   startActivity(intent);
	 	            	   //getActivity().finish();
	            	   }
	            	  
	              }
	          });
		 }

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((HomeActivity) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
		}
	}
	
	
public static class MyPreferencesFragment extends ListFragment {
		
		
	    private ListView listView;
	    private ArticleHeaderAdapter adapter;
	 
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		ImageView btnUpdatePrefers;
		public static MyPreferencesFragment newInstance(int sectionNumber) {
			MyPreferencesFragment fragment = new MyPreferencesFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public MyPreferencesFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_mypreferences, container,
					false);
			
			 //actionbar.setBackgroundDrawable(blue);
			
			return rootView;
		}
		
		 @Override 
		 public void onViewCreated (View view, Bundle savedInstanceState) {
			 //ActionBar actionbar = ((ActionBarActivity)getActivity()).getSupportActionBar();
			 //actionbar.setTitle("Registration - General");
			 //actionbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.sbBlue)));
			 //android.app.ActionBar actionbar = getActivity().getActionBar();
			 //actionbar.setTitle("My Preferences");
			 //actionbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.sbBlue)));
			
			 List<NameValuePair> list = new ArrayList();
			 list.add(new BasicNameValuePair("id", "BU001"));
			 //list.add(new BasicNameValuePair("password", user.get(0).getPass()));
			  btnUpdatePrefers = (ImageView)getActivity().findViewById(R.id.btnUpdatePrefers);
			  ProgressBar progressBarPrefs = (ProgressBar)getActivity().findViewById(R.id.progressBarPrefs);
			 
		     new GetPreferencesTask(getActivity(), list).execute("");
		     btnUpdatePrefers.setVisibility(View.VISIBLE);
		     progressBarPrefs.setVisibility(View.GONE);
			  
			  
			  btnUpdatePrefers.setOnClickListener(new View.OnClickListener() 
	           {

	               @Override
	              public void onClick(View v) 
	              {
	            	   List<NameValuePair> parameters = new ArrayList<NameValuePair>();
	   			    
	  			     //paramters.add(new BasicNameValuePair("requesttype","content"));
	            	   parameters.add(new BasicNameValuePair("username", user.get(0).getEmail()));
	            	   parameters.add(new BasicNameValuePair("password", user.get(0).getPass()));
	            	   
	            	   LinearLayout layoutPrefs = (LinearLayout)getActivity().findViewById(R.id.layoutPrefs);
	     			  int count = layoutPrefs.getChildCount();
	     			  View v1 = null;
	     			  String prefs = "";
	     			  for(int i=0; i<count; i++) {
	     			      v1 = layoutPrefs.getChildAt(i);
	     			      try{
	     			    	 CheckBox prefsChk = (CheckBox)v1;
		     			      if(prefsChk.isChecked())
		     			      {
		     			    	  prefs += getPreferenceID(prefsChk.getText().toString()) + ",";
		     			    	  
		     			      }
	     			      }catch(Exception ex)
	     			      {
	     			    	  ex.printStackTrace();
	     			      }
	     			      
	     			  }
	     			 parameters.add(new BasicNameValuePair("preferences", prefs));
		  			   
	            	  new UpdatePreferenceseTask(getActivity(), parameters).execute("");
	              }
	          }); 
		 }
		 public String getPreferenceID(String preferenceText)
		 {
			 for(Iterator<NameValuePair> i = prefDic.iterator(); i.hasNext(); ) {
				    NameValuePair item = i.next();
				    if (preferenceText.equals(item.getValue()))
				    	return item.getName();
				    
				}
			return null;
			 
		 }

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((HomeActivity) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
		}
		
		 public class GetPreferencesTask extends AsyncTask<String, Void, String> {
			 Activity mContex;
			 List<NameValuePair> preferences;
			 
			 
			 public  GetPreferencesTask(Activity contex, List<NameValuePair> nameValuePairs)
			 
			    {
				 	preferences = nameValuePairs;
			     	this.mContex=contex;
			    }
		        @Override
		        protected String doInBackground(String... urls) {
		        	//result = loadContent();
		        	try{
		        		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
						nameValuePairs.add(new BasicNameValuePair("count", "5"));
						HttpClient httpClient = new DefaultHttpClient();
						String paramsString = URLEncodedUtils.format(preferences, "UTF-8");
						HttpGet httpGet = new HttpGet(Connector.server + "Preferences"+"?" + paramsString);
						HttpResponse response = httpClient.execute(httpGet);
						BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
						StringBuilder builder = new StringBuilder();
						for (String line = null; (line = reader.readLine()) != null;) {
						    builder.append(line).append("\n");
						}
						JSONTokener tokener = new JSONTokener(builder.toString());
						ListPreferences = new JSONArray(tokener);
			        	return null;
			     
		        	}catch(Exception ex)
		        	{
		        		ex.printStackTrace();
		        	}
					return null;
		       }

		        @Override
		        protected void onPostExecute(String resultss) {
		        	LinearLayout layoutPrefs = (LinearLayout)mContex.findViewById(R.id.layoutPrefs);
		        	  try{
		        		  String prefs = user.get(0).getPrefs();
		        		  String prefList[] = prefs.split(",");
		        		  prefDic = new ArrayList();
		        		  for(int i = 0; i < ListPreferences.length()-1; i++) {
				        		JSONObject jsonobject = ListPreferences.getJSONObject(i);
							    //ArticleHeader article = new ArticleHeader();
								//article.title = jsonobject.getString("Title");
				        		prefDic.add(new BasicNameValuePair(jsonobject.getString("PreferenceID"),jsonobject.getString("PreferenceText")));
							    
					                CheckBox cb = new CheckBox(mContex);
					                
					                cb.setText(jsonobject.getString("PreferenceText"));
					                if (prefList.length > 1)
					                {					                	
					                	for(int j = 0; j< prefList.length - 1; j++)
					                	{
					                		if(jsonobject.getString("PreferenceID").contains(prefList[j]))
					                		{
					                			cb.setChecked(true);
					                		}
					                	}
					                }else{
					                	
					                	cb.setChecked(true);
					                }
					                	
					                
					               
					                layoutPrefs.addView(cb);
		        		  }
		        	  }catch(Exception ex)
		        	  {
		        		  ex.printStackTrace();
		        	  }
		        	  ImageView btnUpdatePrefers = (ImageView)mContex.findViewById(R.id.btnUpdatePrefers);
		        	  
		        }
		      }

		 
		 class UpdatePreferenceseTask extends AsyncTask<String, Void, String> {
			 Activity mContex;
			 List<NameValuePair> parameters;
			 public  UpdatePreferenceseTask(Activity contex, List<NameValuePair> _parameters)
			    {
			     	this.mContex=contex;
			     	parameters = _parameters;
			    }
		        @Override
		        protected String doInBackground(String... urls) {
		        	//result = loadContent();
		        	try{
		        		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
						nameValuePairs.add(new BasicNameValuePair("count", "5"));
						HttpClient httpClient = new DefaultHttpClient();
						String paramsString = URLEncodedUtils.format(parameters, "UTF-8");
						HttpPost httpPost = new HttpPost(Connector.server + "UserInformation"+"?" + paramsString);
						HttpResponse response = httpClient.execute(httpPost);
						BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
						StringBuilder builder = new StringBuilder();
						for (String line = null; (line = reader.readLine()) != null;) {
						    builder.append(line).append("\n");
						}
						JSONTokener tokener = new JSONTokener(builder.toString());
						//ListPreferences = new JSONArray(tokener);
			        	return null;
			     
		        	}catch(Exception ex)
		        	{
		        		ex.printStackTrace();
		        	}
		        	return null;
		        }

		        @Override
		        protected void onPostExecute(String resultss) {
		        }
		      }

}
}



