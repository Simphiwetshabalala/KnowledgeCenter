package com.example.knowledgecenter;

public class PreferencesModel {
	
	public String Name;
	public int Value;
	
	
	public PreferencesModel(String _Name, int _value) {
		// TODO Auto-generated constructor stub
		Name = _Name;
		Value = _value;
	}

	public void setPreferenceID(String _Name)
	{
		Name = _Name;
	}
	
	public void setValue(int _Value)
	{
		Value = _Value;
	}
	
	public String getName()
	{
		return Name;
	}
	
	public int getValue()
	{
		return Value;
	}

}
