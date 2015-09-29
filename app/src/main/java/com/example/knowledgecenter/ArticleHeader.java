package com.example.knowledgecenter;

public class ArticleHeader {
	
	public String title;
	public String time;
	public String prefs;
	public String publisher;
	public String body;
	
	public void title(String _title)
	{
		title = _title;
	}
	
	public void time(String _time)
	{
		time = _time;
	}
	
	public void prefs(String _prefs)
	{
		prefs = _prefs;
	}
	
	public void publisher(String _publisher)
	{
		publisher = _publisher;
	}
	
	public void setBody(String _body)
	{
		body = _body;
	}
	public String getTitle()
	{
		return title;
	}
	
	public String getTime()
	{
		return time;
	}
	
	public String getPrefs()
	{
		return prefs;
	}
	
	public String getPublisher()
	{
		return publisher;
	}
	
	public String getBody()
	{
		return body;
	}

}
