package com.example.paymytrip;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends Activity {

	private Button CreateTourBtn;
	private Button GetDataBtn;
	private ListView allTours;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		CreateTourBtn = (Button) findViewById(R.id.CreateTourBtn);
		GetDataBtn = (Button) findViewById(R.id.GetDataBtn);
		allTours = (ListView) findViewById(R.id.tour_list);
		
		
		//db.ClearTable("TOURS");
		PopulateData();
		
		allTours.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				TextView tv = (TextView) arg1.findViewById(R.id.tourName);
				Log.d("FR","data: " + tv.getText().toString());
				
				Intent intent = new Intent(getApplicationContext(), TourHome.class);
				intent.putExtra("tour_name",tv.getText().toString());
				startActivity(intent);
			}
			
		});
		
		CreateTourBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), CreateTour.class);
				startActivity(intent);
			}
		});
		
		GetDataBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DatabaseHandler db = new DatabaseHandler(MainActivity.this, "TripDB", null, 1);
				db.ClearTable("TOURS");
				Tours t = new Tours();
				t.tour_name="MOGA";
				t.date="2012/02/1991";
				t.noOfPerson=4;
				t.names="BOSS#KAKA#MAMA#CHACHA";
				
				db.AddDataTours(t);
				
				Intent intent = new Intent(getApplicationContext(), TourHome.class);
				intent.putExtra("tour_name",t.tour_name);
				startActivity(intent);
			}
		});
		
		
		
		
		
	}

	private void PopulateData() {
		// TODO Auto-generated method stub
		DatabaseHandler db = new DatabaseHandler(this, "TripDB", null, 1);
		ArrayList<Tours> tourData = db.GetToursData();
		Log.d("FR", "SZ: " + tourData.size());
		ArrayList<String> allTourName = new ArrayList<String>();
		ArrayList<String> allTourDate = new ArrayList<String>();
		for(int i=0; i < tourData.size(); i ++){
			Tours tour = tourData.get(i);
			allTourName.add(tour.tour_name);
			allTourDate.add(tour.date);
		}
		
		MyListAdapter mAdapter = new MyListAdapter(this, R.layout.list_item, allTourName, allTourDate);
		allTours.setAdapter(mAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		PopulateData();
	}

}
