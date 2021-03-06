package com.example.knowledgecenter;

public interface Config {

	
	// CONSTANTS
	static final String YOUR_SERVER_URL =  "http://www.smacbooks.co.za/Notifications/register.php";
	// YOUR_SERVER_URL : Server url where you have placed your server files
    // Google project id
    static final String GOOGLE_SENDER_ID = "695993416656";  // Place here your Google project id

    /**
     * Tag used on log messages.
     */
    static final String TAG = "GCM Android Example";

    static final String DISPLAY_MESSAGE_ACTION =
            "com.example.knowledgecenter.DISPLAY_MESSAGE";

    static final String EXTRA_MESSAGE = "message";
		
	
}
