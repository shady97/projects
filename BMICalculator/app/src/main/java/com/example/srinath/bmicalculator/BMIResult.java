package com.example.srinath.bmicalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class BMIResult extends AppCompatActivity {

    TextView tvResult,tvInfo1,tvInfo2,tvInfo3,tvInfo4;
    SharedPreferences sp;
    Button btnBack, btnShare, btnSave;
    ActionBar actionBar;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmiresult);

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#66ffcc")));

        tvResult = (TextView) findViewById(R.id.tvResult);
        tvInfo1 = (TextView) findViewById(R.id.tvInfo1);
        tvInfo2 = (TextView) findViewById(R.id.tvInfo2);
        tvInfo3 = (TextView) findViewById(R.id.tvInfo3);
        tvInfo4 = (TextView) findViewById(R.id.tvInfo4);
        sp = getSharedPreferences("f1", MODE_PRIVATE);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnShare = (Button) findViewById(R.id.btnShare);
        btnSave = (Button) findViewById(R.id.btnSave);


        tvInfo1.setText("BMI below 18.5 is Underweight ");
        tvInfo2.setText("BMI between 18.5 and 25 is Normal " );
        tvInfo3.setText("BMI between 25 and 30 is Overweight " );
        tvInfo4.setText("BMI more than 30 is Obese " );

        final String bmi = sp.getString("bmi1", "");
        final float bmi2 = Float.valueOf(bmi);
        if(bmi2 < 18.5)
        {
            tvResult.setText("Your BMI is " + bmi2 + "and you are underweight" );
            tvInfo1.setTextColor(Color.parseColor("#FF0000"));

        }
        else if(bmi2 >= 18.5 && bmi2 <= 25)
        {
            tvResult.setText("Your BMI is " + bmi2 + "and you are normal");
            tvInfo2.setTextColor(Color.parseColor("#15FF00"));
        }
        else if(bmi2 >= 25 && bmi2 <= 30)
        {
            tvResult.setText("Your BMI is " + bmi2 + "and you are overweight" );
            tvInfo3.setTextColor(Color.parseColor("#FF0000"));
        }
        else if(bmi2 > 30)
        {
            tvResult.setText("Your BMI is " + bmi2 + "and you are obese \nConsult a doctor dude" );
            tvInfo4.setTextColor(Color.parseColor("#FF0000"));
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent i = new Intent(BMIResult.this, BMIData.class);
                    startActivity(i);    finish();
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = sp.getString("name", "");
                String age = sp.getString("age", "");
                String phone = sp.getString("phone", "");
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, "Name: "+ name + "\nAge: "+ age +" \nBody mass Index (BMI): "+ bmi2);
                startActivity(i);
            }
        });

        db=new DatabaseHandler(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Calendar cal = Calendar.getInstance();

                String date = ""+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR);

                String time = ""+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE);
                db.addHistory( time, date , bmi2);

                Toast.makeText(BMIResult.this, "Your data has been saved successfully", Toast.LENGTH_SHORT).show();





            }
        });
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent(BMIResult.this, BMIData.class);
        startActivity(i);    finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.m1, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.about) {
            Toast.makeText(this, "App developed by Srinath", Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.web){
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://" + "www.srinathshady.com"));
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
