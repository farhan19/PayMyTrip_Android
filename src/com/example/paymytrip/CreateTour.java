package com.example.paymytrip;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateTour extends Activity{
	
	private Button AddTourBtn;
	private Button GetMyDataBtn;
	private EditText TourNameEditText;
	private EditText NoOfPersonEditText;
	private EditText BuddiesEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.create_tour);
		
		AddTourBtn = (Button) findViewById(R.id.AddBtn);
		GetMyDataBtn = (Button) findViewById(R.id.GetMyDataBtn);
		TourNameEditText = (EditText) findViewById(R.id.TourNameEditText);
		NoOfPersonEditText = (EditText) findViewById(R.id.NoOfPersonEditText);
		BuddiesEditText = (EditText) findViewById(R.id.BuddiesEditText);
				
		
		AddTourBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				InputMethodManager imm = (InputMethodManager)getSystemService(
					      Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(BuddiesEditText.getWindowToken(), 0);
					
				// TODO Auto-generated method stub

				String Tour_name = TourNameEditText.getText().toString();
				String NoOfPerson = NoOfPersonEditText.getText().toString();
				String Buddies = BuddiesEditText.getText().toString();
				
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
				String date_time = df.format(cal.getTime());
								
				Log.d("FR","Add Tour: " + Tour_name + " " + NoOfPerson + " " + Buddies + " " + date_time);
				
				if(Tour_name.isEmpty() || NoOfPerson.isEmpty()|| Buddies.isEmpty()){
					Log.d("FR","Emplty");
					Toast.makeText(CreateTour.this, "Input Field", Toast.LENGTH_SHORT).show();
				}else{
					
					Tours tour = new Tours();
					tour.tour_name=Tour_name;
					tour.noOfPerson=Integer.parseInt(NoOfPerson);
					tour.names=Buddies;
					tour.date=date_time;
					
					DatabaseHandler db = new DatabaseHandler(CreateTour.this, "TripDB", null, 1);
					
					if(db.AddDataTours(tour)){
						Toast.makeText(CreateTour.this, "Data Saved", Toast.LENGTH_SHORT).show();
						finish();
					}
					
				}
			}
		});
		
		GetMyDataBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				DatabaseHandler db = new DatabaseHandler(CreateTour.this, "TripDB", null, 1);
				db.GetToursData();
			}
		});
	
	}
}
