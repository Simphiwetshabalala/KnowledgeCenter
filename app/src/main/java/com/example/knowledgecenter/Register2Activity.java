package com.example.knowledgecenter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;

import com.example.knowledgecenter.Register2Activity.PlaceholderFragment.prefserencesFragment;
import com.google.android.gcm.GCMRegistrar;


import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.microsoft.windowsazure.messaging.*;
import com.microsoft.windowsazure.notifications.NotificationsManager;
import com.google.android.gcm.GCMRegistrar;


public class Register2Activity extends ActionBarActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;
	public static Connector controller;
	public static UserBO userReg;
	static Controller aController;
	private String SENDER_ID = "<your project number>";
	//private GoogleCloudMessaging gcm;
	private NotificationHub hub;
	private String HubName = "<Enter Your Hub Name>";
	private String HubListenConnectionString = "<Your default listen connection string>";
	
	//static GCMReg reg;
	// Asyntask
		static AsyncTask<Void, Void, Void> mRegisterTask;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register2);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		userReg = new UserBO();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.register2, menu);
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

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			//return PlaceholderFragment.newInstance(position + 1);
			switch(position)	{
			case 0: 
				//Personal FragmentPer
				return PlaceholderFragment.newInstance(position + 1);
				
			case 1: 
				//Personal FragmentPer
				return prefserencesFragment.newInstance(position + 1);
			default:
				return prefserencesFragment.newInstance(position + 1);
			}
			//return null;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
		
		
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		ViewPager viewPager;
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";
		boolean showNext;
		boolean email = true;
		boolean empNo = true;
		boolean pass = true;

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
			 View rootView = inflater.inflate(R.layout.fragment_register2,
					container, false);
			android.app.ActionBar actionbar = getActivity().getActionBar();
			 actionbar.setTitle("Registration");
			 final String txtEmail = ((EditText)rootView.findViewById(R.id.txtEmail)).getText().toString();
			 final String txtFirstName = ((EditText)rootView.findViewById(R.id.txtFirstName)).getText().toString();
			 final String txtLastName = ((EditText)rootView.findViewById(R.id.txtLastName)).getText().toString();
			 final String txtEmployee = ((EditText)rootView.findViewById(R.id.txtEmployeeNumber)).getText().toString();
			 final Controller aController = (Controller) getActivity().getApplication();
				
			 actionbar.setBackgroundDrawable(new ColorDrawable(Color.rgb(2, 26, 123)));
			 ImageView btnNext = (ImageView) rootView.findViewById(R.id.btnNextGeneral);
				btnNext.setOnClickListener(new View.OnClickListener() 
		           {

		               @Override
		              public void onClick(View v) 
		              {
		            	   String txtPassword = ((EditText)getActivity().findViewById(R.id.txtAskKC)).getText().toString();
		            	   String txtPassword1 = ((EditText)getActivity().findViewById(R.id.txtPassword2)).getText().toString();
		            	   if(!txtPassword.equals(txtPassword1))
		            	   {
		            		   aController.showAlertDialog(getActivity(),
			       						"Password Error",
			       						"Passwords do not match", false);
		            		   
		            		    ((EditText)getActivity().findViewById(R.id.txtAskKC)).setText("");
		            		    ((EditText)getActivity().findViewById(R.id.txtPassword2)).setText("");

		            			 pass = false;
		            	   }
		            	   else
		            	   {
		            		   showNext = true;
		            	   }
		            	   UserBO.EmailAddress = ((EditText)getActivity().findViewById(R.id.txtEmail)).getText().toString();
		            	   UserBO.FirstName = ((EditText)getActivity().findViewById(R.id.txtFirstName)).getText().toString();;
		            	   UserBO.LastName = ((EditText)getActivity().findViewById(R.id.txtLastName)).getText().toString();
		            	   UserBO.EmployeeNumber = ((EditText)getActivity().findViewById(R.id.txtEmployeeNumber)).getText().toString();
		            	   UserBO.Password = ((EditText)getActivity().findViewById(R.id.txtPassword2 	)).getText().toString();
		            	   //UserBO.RegistrationID = "saga";
		            	   UserBO.UserType = "hdsgs";
		            	   if((!UserBO.EmployeeNumber.startsWith("A") || !UserBO.EmployeeNumber.startsWith("C")) && UserBO.EmployeeNumber.length()!=7 )
		            	   {
		            		   AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
		            		
		            		    showNext = false;
		            		    aController.showAlertDialog(getActivity(),
			       						"Invalid Employee Number",
			       						"Please Provide Valid Employee Number", false);
		            		    ((EditText)getActivity().findViewById(R.id.txtEmployeeNumber)).setText("");
		            			

		            			 empNo = false;
		            	   }
		            	   
		            	   if(!UserBO.EmailAddress.contains("@standardbank.co.za")) //|| !UserBO.email.contains("@stanbic.com") || !UserBO.email.contains("@standardsbg.com")|| !UserBO.email.contains("@standardbank.com") || !UserBO.email.contains("@standardny.com") || !UserBO.email.contains("@standardbank.mu") || !UserBO.email.contains("@standardbank.co.mz") || !UserBO.email.contains("@standbic.com.gh") || !UserBO.email.contains("@standbicitbc.com") || !UserBO.email.contains("@standardbank.co.mw") || !UserBO.email.contains("@standardbank.com.na") || !UserBO.email.contains("@stanlib.com") || !UserBO.email.contains("@libety.co.za"))
		            	   {
		            		   aController.showAlertDialog(getActivity(),
		       						"Invalid Email",
		       						"Please Provide Standarbank Email", false);
		            		   UserBO.EmailAddress = "";
		            		   ((EditText)getActivity().findViewById(R.id.txtEmail)).setText("");
		            		   email = false;
		            	   }
		            	   
		            	   if(email && empNo && pass)
		            	   {
		            		   viewPager = (ViewPager) getActivity().findViewById(
			                           R.id.pager);
			                   viewPager.setCurrentItem(1);
		            	   }else
		            	   {
		            		   email = true;
		            		   empNo = true;
		            		   pass = true;
		            	   }
		            		   
		            	   
		            	   
		            	   /**userReg.cell = cellphone.getText().toString();
		            	   userReg.firstname = firstName.getText().toString();
		            	   userReg.password = password.getText().toString();
		            	   userReg.email = email.getText().toString();
		            	   userReg.defaultAddress();
		                   //sendJson(UserBO.List<Map<string,string>>firstName, UserBO.password);
		                   **/
		            	   /**rootView = (ViewPager) getActivity().findViewById(
		                           R.id.pager);
		            	   rootView.setCurrentItem(1);**/
		              }
		          }); 
			 return rootView;
		}
		
		@Override
		public void onResume() {
		    super.onResume();
		    // Set title
		    getActivity().getActionBar()
		        .setTitle("Registration - General");
		
		
	}
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class prefserencesFragment extends Fragment {
		ViewPager viewPager;
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static prefserencesFragment newInstance(int sectionNumber) {
			prefserencesFragment fragment = new prefserencesFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}
		

		public prefserencesFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_preferences2,
					container, false);
			
			/**ListView list = (ListView)rootView.findViewById(R.id.lstPrefs);
			PreferencesModel []prefs;
			prefs = new PreferencesModel[5];
			prefs[0] = new PreferencesModel("Cars",0);
			prefs[0] = new PreferencesModel("Books",0);
			PreferencesAdapter ad = new PreferencesAdapter(getActivity(), prefs);
			list.setAdapter(ad);**/
			
			//Get CheckBox selected Items
			/**final boolean banking = ((CheckBox)rootView.findViewById(R.id.chkBanking)).isChecked();
			if(banking)
				UserBO.prefs += "1";
			final boolean risk = ((CheckBox)rootView.findViewById(R.id.chkRisk)).isChecked();
			if(risk)
				UserBO.prefs += "2";
			final boolean saEconomy = ((CheckBox)rootView.findViewById(R.id.chkPropert)).isChecked();
			if(saEconomy)
				UserBO.prefs += "3";
			final boolean industires = ((CheckBox)rootView.findViewById(R.id.chkIndustires)).isChecked();
			if(industires)
				UserBO.prefs += "4";
			final boolean countries = ((CheckBox)rootView.findViewById(R.id.chkCountries)).isChecked();
			if(countries)
				UserBO.prefs += "5";
			final boolean general = ((CheckBox)rootView.findViewById(R.id.chkGeneral)).isChecked();
			if(general)
				UserBO.prefs += "6";
			final boolean legal = ((CheckBox)rootView.findViewById(R.id.ChkLegal)).isChecked();
			if(legal)
				UserBO.prefs += "7";**/
			//final boolean property = ((CheckBox)rootView.findViewById(R.id.chkProperty)).isChecked();
			//if(property)
			//	UserBO.prefs += "8";
			Context context = getActivity();
			
			//Get Global Controller Class object (see application tag in AndroidManifest.xml)
			final Controller aController = (Controller) getActivity().getApplication();
			
			// Check if Internet Connection present
			if (!aController.isConnectingToInternet()) {
				
				// Internet Connection is not present
				aController.showAlertDialog(getActivity(),
						"Internet Connection Error",
						"Please connect to working Internet connection", false);
				
				// stop executing code by return
				//return;
			}

			// Check if GCM configuration is set
			if (Config.YOUR_SERVER_URL == null || Config.GOOGLE_SENDER_ID == null || Config.YOUR_SERVER_URL.length() == 0
					|| Config.GOOGLE_SENDER_ID.length() == 0) {
				
				// GCM sernder id / server url is missing
				aController.showAlertDialog(getActivity(), "Configuration Error!",
						"Please set your Server URL and GCM Sender ID", false);
				
				// stop executing code by return
				 //return;
			}
			
			
			//Intent unregIntent = new Intent ("com.google.android.c2dm.intent.UNREGISTER");
	        //unregIntent.putExtra ("app", PendingIntent.getBroadcast (context, 0, new Intent (), 0));
	        //getActivity().startService(unregIntent);
	       // GCMRegistrar.unregister(getActivity());
			
	        
			android.app.ActionBar actionbar = getActivity().getActionBar();
			 actionbar.setTitle("Registration");
			 ImageView btnprefsPre = (ImageView)rootView.findViewById(R.id.btnPrefPre);
			 btnprefsPre.setOnClickListener(new View.OnClickListener() 
	           {

	             @Override
	              public void onClick(View v) 
	              {
	            	 viewPager = (ViewPager) getActivity().findViewById(
	                           R.id.pager);
	                   viewPager.setCurrentItem(0);
	              }
	           }); 
			 actionbar.setBackgroundDrawable(new ColorDrawable(Color.rgb(2, 26, 123)));
			 ImageView btnNextprefserences = (ImageView)rootView.findViewById(R.id.btnNextPreferences);
			 btnNextprefserences.setOnClickListener(new View.OnClickListener() 
	           {

	               @Override
	              public void onClick(View v) 
	              {

	            	   //userReg.requesttype = "CREATE";
                  	  // try {
                  		   
                  		 determineprefsd();
                  		controller = new Connector(getActivity());
                  		 UserBO.Preferences = UserBO.Preferences.replace("null", "");
                  		//UserBO.Preferences = UserBO.Preferences.toUpperCase();
                  		 UserBO.BUID = "BU001";
                  		//String regId = registerOnGCM(UserBO.FirstName, UserBO.EmailAddress);
                  		//UserBO.RegistrationID = aController.getRegId(getActivity());
						ProgressDialog nDialog = new ProgressDialog(getActivity()); 
						Activity con = getActivity();
						try {
						    
							//String regID = new GCMRegistration(getActivity(), userReg,controller,mHandleMessageReceiver).execute("").get();
							//regID = GCMRegistrar.getRegistrationId(getActivity());
							//userReg.RegistrationID = GCMRegistrar.getRegistrationId(getActivity());
							 registerOnGCM(getActivity());
							//userReg.RegistrationID = com.example.knowledgecenter.Controller.RegistrationID;
							Log.d("INFO", "REGID: " + userReg.RegistrationID);
							
							//nDialog.dismiss();
							
							
							
							//Log.d("Info","Registration ID: "+userReg.RegistrationID.toString());
						
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						/**nDialog.show();
                  		nDialog.setMessage("Registering..");
                        //nDialog.setTitle("Checking Network");
                  		controller.buildPost(parameters,"userinformation","PUT");
						//controller.buildPost(parameters, "CreateNewUser");
	                    controller.execute().get();
	                    
	                    
						//CommentsDataSource datasource
						UserDataSource datasource = new UserDataSource(getActivity());
					    datasource.open();
					    datasource.deleteAll();
						//User u = datasource.createUser(UserBO.email, UserBO.prefs)	;
					    
						datasource.insertUser(UserBO.EmailAddress, UserBO.Preferences,UserBO.Password);
						Intent intent = new Intent(getActivity(), HomeActivity.class);
					    startActivity(intent);**/
	                    //disableAll();
						//legal.setEnabled(false);
	        			/**(((CheckBox) getActivity().findViewById(R.id.chkBanking))).setEnabled(false);
	        			(((CheckBox) getActivity().findViewById(R.id.chkBasel))).setEnabled(false);
	        			(((CheckBox) getActivity().findViewById(R.id.chkCountries))).setEnabled(false);
	        			(((CheckBox) getActivity().findViewById(R.id.chkIndustires))).setEnabled(false);
	        			(((CheckBox) getActivity().findViewById(R.id.ChkLegal))).setEnabled(false);
	        			(((CheckBox) getActivity().findViewById(R.id.chkProperty))).setEnabled(false);
	        			(((CheckBox) getActivity().findViewById(R.id.chkRisk))).setEnabled(false);
	        			(((CheckBox) getActivity().findViewById(R.id.chkSAEconomy))).setEnabled(false);
	        			
	                   // getActivity().unregisterReceiver(mHandleMessageReceiver);
	                    
	                    
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}**/
                  	   
	            	   /**userReg.cell = cellphone.getText().toString();
	            	   userReg.firstname = firstName.getText().toString();
	            	   userReg.password = password.getText().toString();
	            	   userReg.email = email.getText().toString();
	            	   userReg.defaultAddress();
	                   //sendJson(UserBO.List<Map<string,string>>firstName, UserBO.password);
	                   **/
	            	   /**rootView = (ViewPager) getActivity().findViewById(
	                           R.id.pager);
	            	   rootView.setCurrentItem(1);**/
	              } 	   
	          }); 
			 
			 return rootView;
		}
		
		public void disableAll()
		{
			(((CheckBox) getActivity().findViewById(R.id.chkGeneral))).setEnabled(false);
			(((CheckBox) getActivity().findViewById(R.id.chkBanking))).setEnabled(false);
			(((CheckBox) getActivity().findViewById(R.id.chkBasel))).setEnabled(false);
			(((CheckBox) getActivity().findViewById(R.id.chkCountries))).setEnabled(false);
			(((CheckBox) getActivity().findViewById(R.id.chkIndustires))).setEnabled(false);
			(((CheckBox) getActivity().findViewById(R.id.ChkLegal))).setEnabled(false);
			(((CheckBox) getActivity().findViewById(R.id.chkProperty))).setEnabled(false);
			(((CheckBox) getActivity().findViewById(R.id.chkRisk))).setEnabled(false);
			(((CheckBox) getActivity().findViewById(R.id.chkPropert))).setEnabled(false);
			
		}
		public void determineprefsd()
		{
			//Get CheckBox selected Items
		   boolean banking = ((CheckBox)getActivity().findViewById(R.id.chkBanking)).isChecked();
			if(banking)
				UserBO.Preferences = "PreferenceID0011,";
		   boolean risk = ((CheckBox)getActivity().findViewById(R.id.chkRisk)).isChecked();
			if(risk)
				UserBO.Preferences += "PreferenceID0017,";
		  // boolean saEconomy = ((CheckBox)getActivity().findViewById(R.id.chkPropert)).isChecked();
		////	if(saEconomy)
		//		UserBO.prefs += "sa Economy,";
		   boolean industires = ((CheckBox)getActivity().findViewById(R.id.chkIndustires)).isChecked();
			if(industires)
				UserBO.Preferences += "PreferenceID0014, ";
		   boolean countries = ((CheckBox)getActivity().findViewById(R.id.chkCountries)).isChecked();
			if(countries)
				UserBO.Preferences += "PreferenceID0012	,";
			boolean general = ((CheckBox)getActivity().findViewById(R.id.chkGeneral)).isChecked();
			if(general)
				UserBO.Preferences += "PreferenceID0013,";
			boolean legal = ((CheckBox)getActivity().findViewById(R.id.ChkLegal)).isChecked();
			if(legal)
				UserBO.Preferences += "PreferenceID0015";
		}
		
		
		public String registerOnGCM(final Context context) throws InterruptedException, ExecutionException
		{
			
			
			mRegisterTask = new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... params) {
					
					aController = (Controller) context.getApplicationContext();
					// Make sure the device has the proper dependencies.
					GCMRegistrar.checkDevice(context);

					// Make sure the manifest permissions was properly set 
					GCMRegistrar.checkManifest(context);
					
					context.registerReceiver(mHandleMessageReceiver, new IntentFilter(
							Config.DISPLAY_MESSAGE_ACTION));
					GCMRegistrar.register(context, Config.GOOGLE_SENDER_ID);
					
					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					//mRegisterTask = null;
					List<NameValuePair> parameters;
					try {
						userReg.RegistrationID = GCMRegistrar.getRegistrationId(context);
						parameters = controller.getParameters(userReg);
						Log.i("RegistrationID", userReg.RegistrationID);
						controller.buildPost(parameters,"userinformation","PUT");
						//nDialog.show();
						controller.execute();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		//String RegistrationID = GCMRegistrar.getRegistrationId(context);
 catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}

			};
			
			// execute AsyncTask
			mRegisterTask.execute(null, null, null);
			
			
			return GCMRegistrar.getRegistrationId(context);
		}
		// Create a broadcast receiver to get message and show on screen 
		private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				
				String newMessage = intent.getExtras().getString(Config.EXTRA_MESSAGE);
				
				// Waking up mobile if it is sleeping
				aController.acquireWakeLock(getActivity().getApplicationContext());
				
				// Display message on the screen
				//lblMessage.append(newMessage + "\n");			
				
				Toast.makeText(getActivity().getApplicationContext(), "Got Message: " + newMessage, Toast.LENGTH_LONG).show();
				
				// Releasing wake lock
				aController.releaseWakeLock();
			}
		};
		
		
		
		@Override
		public void onResume() {
		    super.onResume();
		    // Set title
		    getActivity().getActionBar()
		        .setTitle("Registration - Preferences");
		}
	}
	
	}
	
	
	 //AsyncTask to register Device in GCM Server
	 

}
