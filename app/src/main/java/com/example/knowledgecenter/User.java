package com.example.knowledgecenter;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class User {
	private long id;
	private String email;
	private String prefs;
	private String pass;
	private String Name;
	private String Surname;
	public String BU;
	
	
	public void setId(long _id)
	{
		id = _id;
	}
	
	public long getId(){
		return id;
	}
	
	public void setEmail(String _email){
		email = _email;
	}
	
	public String getEmail(){
		return email;
	}
	
	public void setPrefs(String Prefs){
		prefs = Prefs;
	}
	
	public String getPrefs(){
		return prefs;
	}
	
	public String getPass()
	{
		
		return pass;
	}
	
	public void setPass(String _pass)
	{
		pass = _pass;
	}
	
	public void setName(String _Name)
	{
		Name = _Name;
	}
	
	public String getName()
	{
		
		return Name;
	}
	
	public void setSurname(String _Surname)
	{
		Surname = _Surname;
	}
	
	public String getSurname()
	{
		return Surname;
	}
	
	public void setBU(String _bu)
	{
		BU = _bu;
	}
	
	public String getBU()
	{
		
		return BU;
	}



}
