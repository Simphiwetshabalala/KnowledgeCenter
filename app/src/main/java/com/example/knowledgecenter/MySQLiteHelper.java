package com.example.knowledgecenter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
	
	  public static final String TABLE_USER = "user";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_EMAIL = "EmailAddress";
	  public static final String COLUMN_PREF = "PREFS";
	  public static final String COLUMN_PASS = "PASS";
	  public static final String COLUMN_NAME = "Name";
	  public static final String COLUMN_SURNAME = "Surname";
	  public static final String COLUMN_BU = "BU";

	  private static final String DATABASE_NAME = "SB.db";
	  private static final int DATABASE_VERSION = 1;
	  
	// Database creation sql statement
	  private static final String DATABASE_CREATE = "create table "
	      + TABLE_USER + "(" + COLUMN_ID
	      + " integer primary key autoincrement, " + COLUMN_EMAIL
	      + " text not null, " +COLUMN_PASS + " TEXT, " +COLUMN_BU + " TEXT, " +COLUMN_NAME +" TEXT, " +COLUMN_SURNAME +" TEXT, "+ COLUMN_PREF + " TEXT" +");";


	public MySQLiteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DATABASE_NAME, factory, DATABASE_VERSION);
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		 Log.w(MySQLiteHelper.class.getName(),
			        "Upgrading database from version " + oldVersion + " to "
			            + newVersion + ", which will destroy all old data");
			    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
			    onCreate(db);
	}

}
