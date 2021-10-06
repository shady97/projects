package com.example.srinath.bmicalculator;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class BMILocWea extends AppCompatActivity
implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener
{

    TextView tvData;
    Button btnShare;
    GoogleApiClient gac;
    Location loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmiloc_wea);

        tvData = (TextView) findViewById(R.id.tvData);
        btnShare = (Button) findViewById(R.id.btnShare);
        tvData = (TextView)findViewById(R.id.tvData);

        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);
        builder.addOnConnectionFailedListener(this);
        builder.addConnectionCallbacks(this);
        builder.addApi(LocationServices.API);
        gac = builder.build();

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, "My address is " + tvData.getText().toString() + " and weather is 28.5 ");
                startActivity(i);
            }
        });
    }

    protected void onResume() {
        super.onResume();
        if(gac!=null)   gac.connect();
    }

    protected void onPause() {
        super.onPause();
        if(gac!=null)   gac.disconnect();
    }

    public void onConnected(Bundle bundle) {

        loc = LocationServices.FusedLocationApi.getLastLocation(gac);
        if(loc!=null)
        {
            double latitude = loc.getLatitude();
            double longitude = loc.getLongitude();
            Geocoder g = new Geocoder(getApplicationContext(), Locale.ENGLISH);
            try {
                List<Address> la = g.getFromLocation(latitude, longitude, 1);
                android.location.Address add = la.get(0);
                String msg = add.getPremises() + " " +add.getCountryName() + " " + add.getLocality() + " " + add.getSubLocality() + ""
                        + add.getAdminArea() + " " + add.getSubAdminArea() + " " +
                        add.getPostalCode() + " " + add.getThoroughfare()  ;

                tvData.setText(msg);

                String msg1 = add.getLocality() ;



            }catch (IOException e) {e.printStackTrace();

            }
        }
        else {
            Toast.makeText(this, "Enable gps / come in open area", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onConnectionSuspended(int i) {

    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult){

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(BMILocWea.this, BMIData.class);
        startActivity(i);    finish();
    }
}
