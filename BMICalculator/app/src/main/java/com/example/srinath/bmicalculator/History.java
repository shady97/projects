package com.example.srinath.bmicalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class History extends AppCompatActivity {
    TextView tvHistory;
    Button btnHistory;
    DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        tvHistory=(TextView)findViewById(R.id.tvHistory);
        btnHistory=(Button)findViewById(R.id.btnHistory);
        db = new DatabaseHandler(this);
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(History.this);

                builder.setTitle("Confirm");
                builder.setMessage("Do you want to Delete the History?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        db.deleteHistory();
                        tvHistory.setText(db.getHistory());
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        String data = db.getHistory();
        if(data.length() == 0)
            tvHistory.setText("No records to show");
        else
            tvHistory.setText(data);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(History.this, BMIData.class);
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
