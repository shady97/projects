package com.example.srinath.studentdatabase;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etRno, etName;
    Button btnAdd, btnView, btnUpdate, btnDelete;
    TextView tvData;
    DbHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etRno = (EditText)findViewById(R.id.etRno);
        etName = (EditText)findViewById(R.id.etName);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnView = (Button)findViewById(R.id.btnView);
        btnUpdate = (Button)findViewById(R.id.btnUpdate);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        tvData = (TextView)findViewById(R.id.tvData);
        db = new DbHandler(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String n = etRno.getText().toString();
                if (name.length() == 0 ) {
                    Toast.makeText(MainActivity.this, "Enter a name and roll number ", Toast.LENGTH_SHORT).show();
                    etRno.requestFocus();
                    return;
                }
                if ( n.length() == 0)
                {
                    Toast.makeText(MainActivity.this, "Enter a Roll no ", Toast.LENGTH_SHORT).show();
                    etRno.requestFocus();
                    return;
                }

                int rno = Integer.parseInt(etRno.getText().toString());


                db.addStudent(rno, name);
                etRno.setText("");
                etName.setText("");
                etRno.requestFocus();


            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = db.viewStudent();
                if(data.length() == 0)
                    tvData.setText("No records to show");
                else
                    tvData.setText(data);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 String name = etName.getText().toString();
                String n = etRno.getText().toString();
                if (name.length() == 0 ) {
                    Toast.makeText(MainActivity.this, "Enter a name and roll number ", Toast.LENGTH_SHORT).show();
                    etRno.requestFocus();
                    return;
                }
                if ( n.length() == 0)
                {
                    Toast.makeText(MainActivity.this, "Enter a Roll no ", Toast.LENGTH_SHORT).show();
                    etRno.requestFocus();
                    return;
                }
                int rno = Integer.parseInt(etRno.getText().toString());
                db.updateStudent(rno, name);
                etRno.setText("");
                etName.setText("");
                etRno.requestFocus();
            }
        });
         btnDelete.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String name = etName.getText().toString();
                 String n = etRno.getText().toString();
                 if (name.length() == 0 ) {
                     Toast.makeText(MainActivity.this, "Enter a name and roll number ", Toast.LENGTH_SHORT).show();
                     etRno.requestFocus();
                     return;
                 }
                 if ( n.length() == 0)
                 {
                     Toast.makeText(MainActivity.this, "Enter a Roll no ", Toast.LENGTH_SHORT).show();
                     etRno.requestFocus();
                     return;
                 }
                 int rno = Integer.parseInt(etRno.getText().toString());
                 db.deleteStudent(rno);
                 etRno.setText("");
                 etName.setText("");
                 etRno.requestFocus();
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
}
