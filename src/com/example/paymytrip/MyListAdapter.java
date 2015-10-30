package com.example.paymytrip;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyListAdapter extends BaseAdapter{

	ArrayList<String> tourNameArray;
	ArrayList<String> tourDateArray;
	private Context mContext;
	
	public MyListAdapter(Context context, int resource, ArrayList<String> tourName, ArrayList<String> tourDate) {
		super();
		tourNameArray = tourName;
		tourDateArray = tourDate;
		mContext = context;
		
		Log.d("FR","Cons: " + tourName.size() + " " + tourDate.size());
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		 
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 convertView = inflater.inflate(R.layout.list_item, null);
 
         // move the cursor to required position 
        
         // get the reference of textViews
         TextView tourName=(TextView) convertView.findViewById(R.id.tourName);
         TextView tourDate=(TextView) convertView.findViewById(R.id.tourDate);
         
         // Set the Sender number and smsBody to respective TextViews 
         tourName.setText(tourNameArray.get(position));
         tourDate.setText(tourDateArray.get(position));
         
         Log.d("FR","Pos: " + position);
		
		return convertView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return tourNameArray.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return tourNameArray.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

}
