package com.example.srinath.studentdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by srina on 11-03-2018.
 */

public class DbHandler extends SQLiteOpenHelper {
    Context context;
    SQLiteDatabase db;

    DbHandler(Context context)
    {
        super(context, "studentdb", null, 1);
        this.context = context;
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "Create table student(rno integer primary key, name text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {}

    public void addStudent(int rno, String name)  {
        ContentValues cv = new ContentValues();
        cv.put("rno", rno);
        cv.put("name", name);
        long rid = db.insert("student", null, cv);
        if(rid < 0)
            Toast.makeText(context, "Insert issue", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "1 record inserted", Toast.LENGTH_SHORT).show();

    }

    public String viewStudent()  {
        StringBuffer sb = new StringBuffer();
        Cursor c = db.query("student", null, null, null, null, null, null);
        c.moveToFirst();
        if(c.getCount() > 0)    {
            do{
                String rno = c.getString(c.getColumnIndex("rno"));
                String name = c.getString(c.getColumnIndex("name"));
                sb.append("Roll No: " + rno + "   Name: " + name + "\n");
            }while (c.moveToNext());
        }
        return sb.toString();
    }

    public void updateStudent(int rno, String name)
    {
        ContentValues cv = new ContentValues();
        cv.put("rno", rno);
        cv.put("name", name);
        long nor = db.update("student", cv, "rno = "+rno, null);
        Toast.makeText(context, nor + "Rows updated", Toast.LENGTH_SHORT).show();
    }
     public void deleteStudent(int rno)
     {
         long nor = db.delete("student", "rno = "+rno, null);
         Toast.makeText(context, nor + "Rows deleted", Toast.LENGTH_SHORT).show();
     }
}
