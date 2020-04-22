package com.example.srinath.bmicalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class BMIData extends AppCompatActivity {

    TextView tvName1;
    SharedPreferences sp;
    Spinner spnFeet, spnInch;
    EditText etWeight;
    Button btnCalculate, btnHistory ;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmidata);

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff8080")));

        btnCalculate = (Button) findViewById(R.id.btnCalculate);
        btnHistory = (Button) findViewById(R.id.btnHistory);
        etWeight = (EditText) findViewById(R.id.etWeight);
        spnFeet = (Spinner)findViewById(R.id.spnFeet);
        spnInch = (Spinner)findViewById(R.id.spnInch);
        tvName1 = (TextView) findViewById(R.id.tvName1);
        sp = getSharedPreferences("f1", MODE_PRIVATE);

        String name = sp.getString("name", "");
        tvName1.setText(name);

        final String[] size1 = {"1", "2", "3","4","5","6","7","8","9","10"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, size1);
        spnFeet.setAdapter(adapter1);
        final String[] size2 = {"0","1", "2", "3","4","5","6","7","8","9","10","11","12"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, size2);
        spnInch.setAdapter(adapter2);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String w1 = etWeight.getText().toString();

                if(w1.length() == 0)
                {
                    Toast.makeText(BMIData.this, "Weight is empty", Toast.LENGTH_SHORT).show();
                    etWeight.requestFocus();    return;

                }

                float w = Float.valueOf(w1);
                String p1 = spnFeet.getSelectedItem().toString();
                String p2 = spnInch.getSelectedItem().toString();
                int f = Integer.valueOf(p1);
                int in = Integer.valueOf(p2);
                double h1 = f*0.305;
                double h2 = in*0.0254;
                double h = h1+h2;

                double bmi = w/(h*h);
                String bmi1 = String.valueOf(bmi);

                SharedPreferences.Editor editor = sp.edit();
                editor.putString("bmi1",bmi1);
                editor.commit();
                Intent i = new Intent(BMIData.this, BMIResult.class);
                startActivity(i);    finish();
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BMIData.this, History.class);
                startActivity(i);    finish();
            }
        });





    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setMessage("Do you want to exit the app");
        b.setCancelable(false);

        b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        b.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog a = b.create();
        a.setTitle("Exit");
        a.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.m1, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.about) {
            Toast.makeText(this, "App developed by Bhavesh", Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.web){
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://" + "www.srinathshady.com"));
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }


}
