package com.example.srinath.currentweather;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;

public class MainActivity extends AppCompatActivity {

    EditText etLocation;
    Button btnFind;
    TextView tvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etLocation = (EditText)findViewById(R.id.etLocation);
        btnFind = (Button)findViewById(R.id.btnFind);
        tvData = (TextView)findViewById(R.id.tvData);

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pn = etLocation.getText().toString();
                if (pn.length() == 0)
                {
                    etLocation.setError("Location is empty");
                    etLocation.requestFocus(); return;
                }

                MyT1 t1 = new MyT1();
                t1.execute("http://api.openweathermap.org/data/2.5/weather?units=metric"+"&q="
                        + pn + "&appid=c6e315d09197cec231495138183954bd");

            }
        });

    }

    class MyT1 extends AsyncTask<String, Void, Double>
    {
        double temp;

        @Override
        protected Double doInBackground(String... strings) {
            String json = "", line = "";

            try
            {
                URL url = new URL(strings[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.connect();

                InputStreamReader isr = new InputStreamReader(con.getInputStream());
                BufferedReader br = new BufferedReader(isr);

                while((line = br.readLine()) != null)
                {
                    json = json + line + "\n";
                }

                JSONObject o = new JSONObject(json);
                JSONObject p = o.getJSONObject("main");
                temp = p.getDouble("temp");

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return temp;
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            super.onPostExecute(aDouble);
            tvData.setText("Temperature = " + aDouble);
        }
    }

}
