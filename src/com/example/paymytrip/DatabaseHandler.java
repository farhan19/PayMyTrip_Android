package com.example.paymytrip;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper{

	public DatabaseHandler(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql = "CREATE TABLE IF NOT EXISTS TOURS ( TOUR_NAME TEXT PRIMARY KEY, DATE TEXT, NO_OF_PERSON INTEGER, NAMES TEXT ) ";
		Log.d("FR","St: " + sql);
		db.execSQL(sql);
		sql = "CREATE TABLE IF NOT EXISTS EXPENSE ( DATE_TIME TEXT PRIMARY KEY, TOUR_NAME TEXT, NAME TEXT, PURPOSE TEXT, AMOUNT TEXT, COMMENT TEXT ) ";
		Log.d("FR","St: " + sql);
		db.execSQL(sql);
		sql = "CREATE TABLE IF NOT EXISTS COMMONFUND ( DATE_TIME TEXT PRIMARY KEY, TOUR_NAME TEXT, NAME TEXT, AMOUNT TEXT ) ";
		Log.d("FR","St: " + sql);
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean AddDataTours( Tours tour){
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "INSERT INTO TOURS VALUES (\'"+ tour.tour_name + "\', \'" + tour.date  + "\', \'" + tour.noOfPerson + "\', \'" + tour.names +"\' )";
		db.execSQL(sql);
		Log.d("FR","St: " + sql);
		return true;
	}
	
	public ArrayList<Tours> GetToursData(){
		ArrayList<Tours> tourData = new ArrayList<Tours>();
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT * FROM TOURS";
		
		Cursor cur = db.rawQuery(sql, null);
		
		Log.d("FR","Cnt: " + cur.getCount());
		
		if(cur.moveToFirst()){
			do{
				Log.d("FR","D: " + cur.getString(0) + " " + cur.getString(1));
				Tours tour = new Tours();
				tour.date = cur.getString(1);
				tour.tour_name = cur.getString(0);
				tour.noOfPerson = Integer.parseInt(cur.getString(2));
				tour.names = cur.getString(3);
				tourData.add(tour);
			}while(cur.moveToNext());
		}
		return tourData;
	}
	
	public void ClearTable(String table){
		String sql = "DELETE FROM " + table;
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(sql);
	}

	public ArrayList<String> getTourBuddies(String tourName) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT NAMES FROM TOURS WHERE TOUR_NAME = '" + tourName+"'";
		
		Cursor cur = db.rawQuery(sql, null);
		
		Log.d("FR","Cnt: " + cur.getCount());
		
		String str = "";
		
		if(cur.moveToFirst()){
			do{
				Log.d("FR","D: " + cur.getString(0));
				str = cur.getString(0);
			}while(cur.moveToNext());
		}
		
		ArrayList<String> BuddyList = new ArrayList<String>();
		String[] names = str.split("#");
		
		for(int i=0; i < names.length; i++){
			BuddyList.add(names[i]);
		}
		
		
		return BuddyList;
	}

	public boolean AddExpense(  Expense expense) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "INSERT INTO EXPENSE VALUES (\'"+ expense.dateTime + "\', \'" + expense.tourName  + "\', \'" + expense.name + "\', \'" + expense.purpose + "\', \'" +expense.amount + "\', \'" + expense.comment +"\' )";
		Log.d("FR","St: " + sql);
		db.execSQL(sql);
		return true;
	}

	public boolean AddCommonFund(CommonFund commonfund) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "INSERT INTO COMMONFUND VALUES (\'"+ commonfund.dateTime + "\', \'" + commonfund.tourName  + "\', \'" + commonfund.name + "\', \'" +commonfund.amount  +"\' )";
		Log.d("FR","St: " + sql);
		db.execSQL(sql);
		return true;
	}
	
	
	public ArrayList<Expense> GetAllTourExpense(String tourName){
		int cost = 0;
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT * FROM EXPENSE WHERE TOUR_NAME = '" + tourName+"'";
		
		Cursor cur = db.rawQuery(sql, null);
		
		ArrayList<Expense> allExpense = new ArrayList<Expense>();
		
		Log.d("FR","Cnt: " + cur.getCount());
		
		String str = "";
		
		if(cur.moveToFirst()){
			do{
				Expense expense = new Expense();
				
				str = cur.getString(0);
				Log.d("FR","D: " + str);
				expense.dateTime=str;

				str = cur.getString(1);
				Log.d("FR","D: " + str);
				expense.tourName=str;
				
				str = cur.getString(2);
				Log.d("FR","D: " + str);
				expense.name=str;
				
				str = cur.getString(3);
				Log.d("FR","D: " + str);
				expense.purpose=str;
				
				str = cur.getString(4);
				Log.d("FR","D: " + str);
				expense.amount=Integer.parseInt(str);
				
				str = cur.getString(5);
				Log.d("FR","D: " + str);
				expense.comment=str;
				
				allExpense.add(expense);
				
			}while(cur.moveToNext());
		}
		
		return allExpense;
	}
	
	public int GetBuddyExpense(String tourName, String buddyName){
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT * FROM EXPENSE WHERE TOUR_NAME = '" + tourName+"' AND NAME = '"+buddyName+"'";
		Cursor cur = db.rawQuery(sql, null);
		Log.d("FR","Cnt: " + cur.getCount());
		String str = "";
		int cost = 0;
		if(cur.moveToFirst()){
			do{		
				str = cur.getString(4);
				Log.d("FR","D: " + str);
				cost += Integer.parseInt(str);
				
			}while(cur.moveToNext());
		}
		
		sql = "SELECT * FROM COMMONFUND WHERE TOUR_NAME = '" + tourName+"' AND NAME = '"+buddyName+"'";
		cur = db.rawQuery(sql, null);
		Log.d("FR","Cnt2: " + cur.getCount());
		if(cur.moveToFirst()){
			do{		
				str = cur.getString(3);
				Log.d("FR","D: " + str);
				cost += Integer.parseInt(str);
				
			}while(cur.moveToNext());
		}
		
		return cost;
	}
	
	public int GetCurrentCommonFundStatus(String tourName){
		SQLiteDatabase db = this.getReadableDatabase();
		int amount = 0;
		String sql = "SELECT * FROM COMMONFUND WHERE TOUR_NAME = '" + tourName+"'";
		Cursor  cur = db.rawQuery(sql, null);
		String str="";
		Log.d("FR","Cnt2: " + cur.getCount());
		if(cur.moveToFirst()){
			do{		
				str = cur.getString(3);
				Log.d("FR","D: " + str);
				amount += Integer.parseInt(str);
				
			}while(cur.moveToNext());
		}
		
		sql = "SELECT * FROM EXPENSE WHERE TOUR_NAME = '" + tourName+"' AND NAME = 'Common Fund'";
		cur = db.rawQuery(sql, null);
		Log.d("FR","Cnt: " + cur.getCount());
		str = "";
		if(cur.moveToFirst()){
			do{		
				str = cur.getString(4);
				Log.d("FR","D: " + str);
				amount -= Integer.parseInt(str);
			}while(cur.moveToNext());
		}
		
		return amount;
	}

}
