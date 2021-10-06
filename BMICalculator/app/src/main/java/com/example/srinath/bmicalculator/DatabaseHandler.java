package com.example.srinath.bmicalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * Created by kshar on 7/4/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    SQLiteDatabase db;
    Context context;


    public DatabaseHandler(Context context) {
        super(context, "HistoryDB", null, 1);
        Log.d("DB123","DB Created Successfully!");
        this.context=context;
        db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE bmi(Time VARCHAR(255),Date VARCHAR(255),BMI VARCHAR(255))");
        Log.d("DB123","Table Created Successfully!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS bmi");
        onCreate(db);
        Log.d("DB123","Table dropped and created a new table");
    }

    public void addHistory(String time,String date,float bmi){
        ContentValues value= new ContentValues();
        value.put("Time",time);
        value.put("Date",date);
        value.put("BMI",bmi);
        db.insert("bmi",null,value);

    }

    public String getHistory(){
        StringBuffer sb=new StringBuffer();
        Cursor c = db.query("bmi",new String[]{"Time","Date","BMI"},null,null,null,null,null);
        String col = "\n  Time             Date                  BMI\n\n";
        sb.append(col);
        c.moveToFirst();
        if(c!=null &&(c.getCount()>0)){
            do{
                String time = c.getString(c.getColumnIndex("Time"));
                String date = c.getString(c.getColumnIndex("Date"));
                double bmi = c.getDouble(c.getColumnIndex("BMI"));
                DecimalFormat bmiformat= new DecimalFormat("#.00");
                sb.append("  "+time + "          "+ date + "              "+bmiformat.format(bmi)+"\n\n");
                Log.d("DB123",sb.toString());
            }while(c.moveToNext());
        }
        return sb.toString();
    }

    public void deleteHistory(){
        db.delete("bmi",null,null);
        Toast.makeText(context, "History is Deleted", Toast.LENGTH_SHORT).show();
    }
}
