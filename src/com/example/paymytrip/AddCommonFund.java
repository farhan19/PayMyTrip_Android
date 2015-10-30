package com.example.paymytrip;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddCommonFund extends Activity{
	
	private TextView CFTourNameTextView;
	private EditText CFNameEditText;
	private EditText CFAmountEditText;
	private Button FundAddBtn;
	private Spinner CFNameSpinner;
	private TextView txtdropdownArrowinSpinner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.common_fund);
		
		CFTourNameTextView = (TextView) findViewById(R.id.CFTourNameTextView);
		CFAmountEditText = (EditText) findViewById(R.id.CFAmountEditText);
		FundAddBtn = (Button) findViewById(R.id.FundAddBtn);
		CFNameSpinner = (Spinner) findViewById(R.id.CFNameSpinner);
		txtdropdownArrowinSpinner = (TextView)findViewById(R.id.dropdownArrowinSpinner);
		
		Bundle extras = getIntent().getExtras();
		final String tourName = extras.getString("tour_name");
		CFTourNameTextView.setText(tourName);
		
		DatabaseHandler db = new DatabaseHandler(this, "TripDB", null, 1);
		ArrayList<String> BuddyList = db.getTourBuddies(tourName);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,BuddyList);
		CFNameSpinner.setAdapter(adapter);
		
		Log.d("FR","SZ: " + BuddyList.size());
		
		FundAddBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = CFNameSpinner.getSelectedItem().toString();
				String amount = CFAmountEditText.getText().toString();
				if(name.isEmpty() || amount.isEmpty()){
					Toast.makeText(AddCommonFund.this, "Input Field", Toast.LENGTH_SHORT).show();
				}
				else{
					Calendar cal = Calendar.getInstance();
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
					String date_time = df.format(cal.getTime());
					
					CommonFund commonfund = new CommonFund();
					commonfund.dateTime=date_time;
					commonfund.tourName=tourName;
					commonfund.amount=amount;
					commonfund.name=name;
					
					DatabaseHandler dbb = new DatabaseHandler(AddCommonFund.this, "TripDB", null, 1);
					if(dbb.AddCommonFund( commonfund)){
						Toast.makeText(AddCommonFund.this, "Data Saved", Toast.LENGTH_SHORT).show();
						finish();
					}
							
				}
			}
		});
		
		txtdropdownArrowinSpinner.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v)
		{
			((Spinner) findViewById(R.id.CFNameSpinner)).performClick();
		}
		});
		
	}

}
