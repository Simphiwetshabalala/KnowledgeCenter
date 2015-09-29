package com.example.knowledgecenter;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

public class UserDataSource {
	
	// Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
	      MySQLiteHelper.COLUMN_EMAIL,MySQLiteHelper.COLUMN_PREF, MySQLiteHelper.COLUMN_PASS, MySQLiteHelper.COLUMN_NAME, MySQLiteHelper.COLUMN_SURNAME};
	  
	  public UserDataSource(Context context) 
	  {
		dbHelper = new MySQLiteHelper(context, null, null, 1);
	  }
	  
	  public void Drop()
	  {
		  dbHelper.onUpgrade(database, 0, 1);
	  }
	  
	  public void open() throws SQLException 
	  {
		    database = dbHelper.getWritableDatabase();
	  }
	  
	  public void close() 
	  {
		    dbHelper.close();
	  }
	  
	  public User createUser(String email, String prefs, String pass, String name, String surname, String bu) {
		    ContentValues values = new ContentValues();
		    values.put(MySQLiteHelper.COLUMN_EMAIL, email);
		    values.put(MySQLiteHelper.COLUMN_PREF, prefs);
		    values.put(MySQLiteHelper.COLUMN_PASS, pass);
		    values.put(MySQLiteHelper.COLUMN_NAME, name);
		    values.put(MySQLiteHelper.COLUMN_SURNAME, surname);
		    values.put(MySQLiteHelper.COLUMN_BU, bu);
		    long insertId = database.insert(MySQLiteHelper.TABLE_USER, null,
		        values);
//		    Cursor cursor = database.query(MySQLiteHelper.TABLE_USER,
////		        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
//		        null, null, null);
//		    cursor.moveToFirst();
//		    User newComment = cursorTouser(cursor);
////		    cursor.close();
		    return null;
		  }
	  
	  public void deleteAll()
	  {
		  database.execSQL("delete from "+ MySQLiteHelper.TABLE_USER);
	  }
	  
	  public void deleteDB()
	  {
		  database.deleteDatabase(null);
	  }
	  
	  private User cursorToUser(Cursor cursor) {
		    User user = new User();
		    user.setId(cursor.getLong(0));
		    user.setEmail(cursor.getString(1));
		    user.setPrefs(cursor.getString(2));
		    user.setPass(cursor.getString(3));
		    user.setName(cursor.getString(4));
		    user.setSurname(cursor.getString(5));
		    
		    return user;
		  }
	  
	  public List<User> getAllUserDetails() {
		    List<User> comments = new ArrayList<User>();

		    Cursor cursor = database.query(MySQLiteHelper.TABLE_USER,
		        allColumns, null, null, null, null, null);

		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
		      User user = cursorToUser(cursor);
		      comments.add(user);
		      cursor.moveToNext();
		    }
		    // make sure to close the cursor
		    cursor.close();
		    return comments;
		  }
	  
	  
	  public void insertUser(String email, String Prefs, String pass, String BU ){
		 // "INSERT or replace INTO user (EmailAddress, PREFS, PASS) VALUES('" +email+"','"+Prefs+"','"+pass+"')";  
		  
		  String sql =
		            "INSERT or replace INTO user (EmailAddress, PREFS, PASS, BU) VALUES('" +email+"','"+Prefs+"','"+pass+"','"+BU+"')";  
		  database.execSQL(sql);
	  }

}
