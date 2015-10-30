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

public class MyTourHistoryListAdapter extends BaseAdapter{

	ArrayList<String> dateArray;
	ArrayList<String> nameArray;
	ArrayList<String> purposeArray;
	ArrayList<String> amountArray;
	ArrayList<String> commentArray;
	private Context mContext;
	
	public MyTourHistoryListAdapter(Context context,  ArrayList<String> dateArray, ArrayList<String> nameArray, ArrayList<String> purposeArray, ArrayList<String> amountArray, ArrayList<String> commentArray) {
		super();
		this.dateArray = dateArray;
		this.nameArray = nameArray;
		this.purposeArray = purposeArray;
		this.amountArray = amountArray;
		this.commentArray = commentArray;
		mContext = context;
		
		Log.d("FR","Cons: " + dateArray.size() + " " + nameArray.size() + " " + purposeArray.size() + " " + amountArray.size() + " " + commentArray.size());
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){ 	
		
		 LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 convertView = inflater.inflate(R.layout.tour_history_list_item, null);
 
         // move the cursor to required position 
		 Log.d("FR","Pos: " + position + " " + dateArray.get(position));
         // get the reference of textViews
         TextView THDateTime =(TextView) convertView.findViewById(R.id.THDateTime);
         TextView THName=(TextView) convertView.findViewById(R.id.THName);
         TextView THPurpose=(TextView) convertView.findViewById(R.id.THPurpose);
         TextView THAmount=(TextView) convertView.findViewById(R.id.THAmount);
         TextView THComment=(TextView) convertView.findViewById(R.id.THComment);
         
         // Set the Sender number and smsBody to respective TextViews 
         THDateTime.setText(dateArray.get(position));
         THName.setText(nameArray.get(position));
         THPurpose.setText(purposeArray.get(position));
         THAmount.setText(amountArray.get(position));
         THComment.setText(commentArray.get(position));
         
         
		
		return convertView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dateArray.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return dateArray.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

}
