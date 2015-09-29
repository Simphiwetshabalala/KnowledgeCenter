package com.example.knowledgecenter;

public class Email {
	public String From;
	public String To;
	public String Body;
	public String Subject;
	public String getFrom(){return From;}
    public String getTo(){ return To;}
    public String getBody(){return Body;}
    public String getSubject(){ return Subject;}
    
    public void setFrom(String _from){ From = _from;}
    public void setTo(String _to){ To = _to;}
    public void setBody(String _body){Body = _body;}
    public void setSubject(String _subject){Subject = _subject;}
}
