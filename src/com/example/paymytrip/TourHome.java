package com.example.paymytrip;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TourHome extends Activity {

	private Button AddExpenseBtn;
	private Button CommonFundBtn;
	private TextView TourNameTextView;
	private TextView TourStatus;
	private String tourName;
	private ListView SummaryList;
	private Button HistoryBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tour_home);

		CommonFundBtn = (Button) findViewById(R.id.CommonFundBtn);
		AddExpenseBtn = (Button) findViewById(R.id.AddExpenseBtn);
		TourNameTextView = (TextView) findViewById(R.id.TourNameTextView);
		TourStatus = (TextView) findViewById(R.id.TourStatus);
		SummaryList = (ListView) findViewById(R.id.tour_summary);
		HistoryBtn = (Button) findViewById(R.id.HistoryBtn);

		Bundle extras = getIntent().getExtras();
		tourName = extras.getString("tour_name");
		TourNameTextView.setText(tourName);

		PopulateSummary(tourName);

		SummaryList.setOnItemLongClickListener(new OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                    int pos, long id) {
                
                	Log.d("FR","pos click: " + pos);

                return true;
            }
        }); 

		AddExpenseBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						AddExpense.class);
				intent.putExtra("tour_name", tourName);
				startActivity(intent);
			}
		});

		CommonFundBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						AddCommonFund.class);
				intent.putExtra("tour_name", tourName);
				startActivity(intent);

			}
		});
		
		HistoryBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						TourHistory.class);
				intent.putExtra("tour_name", tourName);
				startActivity(intent);
			}
		});
	}

	private void PopulateSummary(String tourName) {
		// TODO Auto-generated method stub
		DatabaseHandler db = new DatabaseHandler(this, "TripDB", null, 1);
		db.GetToursData();

		ArrayList<Expense> allExpenses = db.GetAllTourExpense(tourName);
		ArrayList<String> buddyList = db.getTourBuddies(tourName);
		ArrayList<String> spentAmount = new ArrayList<String>();
		ArrayList<String> summaryCost = new ArrayList<String>();

		int totalCost = 0;
		int ppCost = 0;
		for (int i = 0; i < allExpenses.size(); i++) {
			Expense ex = allExpenses.get(i);
			totalCost += ex.amount;
		}
		ppCost = totalCost / buddyList.size();
		Log.d("FR", "Tot Cost: " + totalCost);

		int commonfund = db.GetCurrentCommonFundStatus(tourName);

		String summary = "Total Cost=" + totalCost + "\nPer Person=" + ppCost
				+ "\nIn CF=" + commonfund;
		TourStatus.setText(summary);

		// spentAmount.add("Spent Amount");
		// summaryCost.add("Status");
		for (int i = 0; i < buddyList.size(); i++) {
			int exp = db.GetBuddyExpense(tourName, buddyList.get(i));
			spentAmount.add(String.valueOf(exp));
			summaryCost.add(String.valueOf(exp - ppCost));
		}

		MyTourSummaryListAdapter mAdapter = new MyTourSummaryListAdapter(
				TourHome.this, buddyList, spentAmount, summaryCost);
		SummaryList.setAdapter(mAdapter);
	}

	@Override
	protected void onResume() {
		super.onResume();
		PopulateSummary(tourName);
	}
}
