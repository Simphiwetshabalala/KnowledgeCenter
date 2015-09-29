package com.example.knowledgecenter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class PreferencesAdapter extends ArrayAdapter{
	PreferencesModel[] modelItems = null;
	 Context context;
	public PreferencesAdapter(Context context, PreferencesModel[] resource) {
		super(context,R.layout.prefs_row,resource);
		modelItems = resource;
		this.context = context;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	 public View getView(int position, View convertView, ViewGroup parent) {
	 // TODO Auto-generated method stub
	 LayoutInflater inflater = ((Activity)context).getLayoutInflater();
	 convertView = inflater.inflate(R.layout.prefs_row, parent, false); 
	 TextView name = (TextView) convertView.findViewById(R.id.txtPref);
	 CheckBox cb = (CheckBox) convertView.findViewById(R.id.chkPrefsItem);
	 name.setText(modelItems[position].getName());
	 /**if(modelItems[position].getValue() == 1)
	 cb.setChecked(true);
	 else
	 cb.setChecked(false);**/
	 return convertView;
	 }

}
