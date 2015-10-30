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

public class MyTourSummaryListAdapter extends BaseAdapter{

	ArrayList<String> buddyNameArray;
	ArrayList<String> spentAmountArray;
	ArrayList<String> summaryArray;
	private Context mContext;
	
	public MyTourSummaryListAdapter(Context context,  ArrayList<String> buddyNameArray, ArrayList<String> spentAmountArray, ArrayList<String> summaryArray) {
		super();
		this.buddyNameArray = buddyNameArray;
		this.spentAmountArray = spentAmountArray;
		this.summaryArray = summaryArray;
		mContext = context;
		
		Log.d("FR","Cons: " + buddyNameArray.size() + " " + spentAmountArray.size() + " " + summaryArray.size());
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){ 	
		
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 convertView = inflater.inflate(R.layout.tour_summary_list_item, null);
 
         // move the cursor to required position 
		 Log.d("FR","Pos: " + position + " " + buddyNameArray.get(position));
         // get the reference of textViews
         TextView buddyName=(TextView) convertView.findViewById(R.id.buddyNameList);
         TextView spentAmount=(TextView) convertView.findViewById(R.id.spentAmount);
         TextView summary=(TextView) convertView.findViewById(R.id.summary);
         
         // Set the Sender number and smsBody to respective TextViews 
         buddyName.setText(buddyNameArray.get(position));
         spentAmount.setText(spentAmountArray.get(position));
         summary.setText(summaryArray.get(position));
         
         
		
		return convertView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return buddyNameArray.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return buddyNameArray.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

}
