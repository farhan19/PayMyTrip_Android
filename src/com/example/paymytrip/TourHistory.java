package com.example.paymytrip;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;



public class TourHistory extends Activity {
	
	private String tourName;
	private TextView THTourName;
	private ListView historyList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tour_history);
		
		THTourName = (TextView) findViewById(R.id.THTourName);
		historyList = (ListView) findViewById(R.id.tour_history);
		
		Bundle extras = getIntent().getExtras();
		tourName = extras.getString("tour_name");
		THTourName.setText(tourName);
		
		DatabaseHandler db = new DatabaseHandler(this, "TripDB", null, 1);
		ArrayList<Expense> allExpense = db.GetAllTourExpense(tourName);
		ArrayList<String> dateList = new ArrayList<String>();
		ArrayList<String> nameList = new ArrayList<String>();
		ArrayList<String> purposeList = new ArrayList<String>();
		ArrayList<String> amountList = new ArrayList<String>();
		ArrayList<String> commentList = new ArrayList<String>();
		
		for(int i=0; i < allExpense.size(); i ++){
			Expense exp = allExpense.get(i);
			dateList.add(exp.dateTime);
			nameList.add(exp.name);
			purposeList.add(exp.purpose);
			amountList.add(String.valueOf(exp.amount));
			commentList.add(exp.comment);
		}
		
		MyTourHistoryListAdapter mAdapter = new MyTourHistoryListAdapter(TourHistory.this, dateList, nameList, purposeList, amountList, commentList);
		historyList.setAdapter(mAdapter);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tour_history, menu);
		return true;
	}

}
