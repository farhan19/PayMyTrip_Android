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

public class AddExpense extends Activity{
	
	private TextView ETourNameTextView;
	private EditText ENameEditText;
	private EditText EPurposeEditText;
	private EditText EAmountEditText;
	private EditText ECommentEditText;
	private Button ExpenseAddBtn;
	private Spinner ENameSpinner;
	private TextView txtdropdownArrowinSpinner;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.expense);
		
		ETourNameTextView = (TextView) findViewById(R.id.ETourNameTextView);
		EPurposeEditText = (EditText) findViewById(R.id.EPurposeEditText);
		EAmountEditText = (EditText) findViewById(R.id.EAmountEditText);
		ECommentEditText = (EditText) findViewById(R.id.ECommentEditText);
		ExpenseAddBtn = (Button) findViewById(R.id.ExpenseAddBtn);
		ENameSpinner = (Spinner) findViewById(R.id.ENameSpinner);
		txtdropdownArrowinSpinner = (TextView)findViewById(R.id.dropdownArrowinSpinnerExpng);
		
		Bundle extras = getIntent().getExtras();
		final String tourName = extras.getString("tour_name");
		ETourNameTextView.setText(tourName);
		
		DatabaseHandler db = new DatabaseHandler(this, "TripDB", null, 1);
		ArrayList<String> BuddyList = db.getTourBuddies(tourName);
		BuddyList.add("Common Fund");
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,BuddyList);
		ENameSpinner.setAdapter(adapter);
		
		Log.d("FR","SZ: " + BuddyList.size());
		
		ExpenseAddBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = ENameSpinner.getSelectedItem().toString();
				String purpose = EPurposeEditText.getText().toString();
				String amount = EAmountEditText.getText().toString();
				String comment = ECommentEditText.getText().toString();
				if(name.isEmpty() || purpose.isEmpty() || amount.isEmpty() || comment.isEmpty()){
					Toast.makeText(AddExpense.this, "Input Field", Toast.LENGTH_SHORT).show();
				}
				else{
					Expense expense = new Expense();
					expense.name=name;
					expense.purpose=purpose;
					expense.amount=Integer.parseInt(amount);
					expense.comment=comment;
					expense.tourName=tourName;
					
					Calendar cal = Calendar.getInstance();
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
					String date_time = df.format(cal.getTime());
					
					expense.dateTime=date_time;
					
					DatabaseHandler dbb = new DatabaseHandler(AddExpense.this, "TripDB", null, 1);
					if(dbb.AddExpense( expense)){
						Toast.makeText(AddExpense.this, "Data Saved", Toast.LENGTH_SHORT).show();
						finish();
					}
				}
			}
		});
		
		txtdropdownArrowinSpinner.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				((Spinner) findViewById(R.id.ENameSpinner)).performClick();
			}
			});
		
	}
}
