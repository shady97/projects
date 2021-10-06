package com.example.srinath.bmicalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etName, etAge, etPhone;
    Button btnRegister;
    SharedPreferences sp;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ccff66")));

        etName = (EditText) findViewById(R.id.etName);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etAge = (EditText) findViewById(R.id.etAge);

        btnRegister = (Button) findViewById(R.id.btnRegister);

        sp = getSharedPreferences("f1", MODE_PRIVATE);

        String name = sp.getString("name", "");


        if(name.length() ==0)  {
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String n = etName.getText().toString();
                    String a = etAge.getText().toString();
                    String phone = etPhone.getText().toString();
                    if(n.length() == 0)  {
                        etName.setError("Name is empty");
                        etName.requestFocus();  return;
                    }
                    else if(a.length() == 0)
                    {
                        etAge.setError("Age is empty");
                        etAge.requestFocus(); return;
                    }
                    else if(phone.length() == 0)
                    {
                        etPhone.setError("Enter your phone number");
                        etPhone.requestFocus(); return;
                    }

                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("name", n);
                    editor.putString("age", a);
                    editor.putString("phone", phone);
                    editor.commit();
                    Intent i = new Intent(MainActivity.this, BMIData.class);
                    startActivity(i);    finish();
                }
            });
        }
        else {
            Intent i = new Intent(MainActivity.this, BMIData.class);
            startActivity(i);    finish();
        }

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
