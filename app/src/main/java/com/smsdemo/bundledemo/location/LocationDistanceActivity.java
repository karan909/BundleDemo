package com.smsdemo.bundledemo.location;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.smsdemo.bundledemo.R;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocationDistanceActivity extends AppCompatActivity implements LocationListener {

    Button getLocationBtn, btnGetDistance;
    TextView locationText, tvDistance;

    EditText lat2, long2;

    LocationManager locationManager;

    TextView tvParlour;
    DecimalFormat distanceFormat;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_distance);

        getLocationBtn = (Button) findViewById(R.id.getLocationBtn);
        locationText = (TextView) findViewById(R.id.locationText);

        btnGetDistance = (Button) findViewById(R.id.getDistance);
        tvDistance = (TextView) findViewById(R.id.tvDistance);

        lat2 = (EditText) findViewById(R.id.lat2);
        long2 = (EditText) findViewById(R.id.long2);

        tvParlour = (TextView) findViewById(R.id.tvParlour);

        distanceFormat = new DecimalFormat("##.##");


        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }


        getLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });

        btnGetDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDistance();
            }
        });


    }


    public void getDistance(){
        Double[] latList = {22.292143, 22.270846, 22.263904, 22.275837, 22.3064290, 22.299302,
                22.2965218, 22.2989448, 22.269810};
        Double[] longList= {70.759512, 70.779055, 70.814102, 70.759714, 70.80425, 70.831163,
                70.7920314, 70.7946737, 70.808800};
        String[] parlourName = {"Sadhuvasvani Road", "Balaji Hall", "Hudko", "Kalawad Road", "Collector", "Sant Kabir Road",
        "Yagnik Road", "Fulchab Chowk", "Sahkar Main Road"};
        Double[] distanceList = new Double[latList.length];
        double distance;
        double temp=0;
        double lat=0;
        double lng=0;
        double min=0;
        int index = 0;

        for (int i=0; i<latList.length; i++){
            LatLng latLng = new LatLng(latList[i], longList[i]);
            LatLng latLng1 = new LatLng(Double.parseDouble(lat2.getText().toString()), Double.parseDouble(long2.getText().toString()));

            distance = CalculationByDistance(latLng1, latLng);

            distanceList[i] = distance;




            /*if(distance > temp){
                temp = distance;
            } else {
                temp = distance;
            }

            Toast.makeText(this, String.valueOf(distance), Toast.LENGTH_SHORT).show();*/

        }

        double minDist = distanceList[0];


        for (int i = 0; i < distanceList.length; i++){

            if(minDist > distanceList[i]){
                minDist = distanceList[i];
                index = i;
            }

        }

        //Toast.makeText(this, , Toast.LENGTH_SHORT).show();


        String parlour = "Distance is: " + String.valueOf(distanceFormat.format(minDist)) + " "+ "Km" + " "
                + "Nearest parlour is: "+ parlourName[index];

        tvParlour.setText(parlour);


    }


    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        locationText.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());

        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            /*List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(lat2.getText().toString()), Double.parseDouble(long2.getText().toString()), 1);*/
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);




            locationText.setText(locationText.getText() + "\n"+addresses.get(0).getAddressLine(0)+", "+
                    addresses.get(0).getAddressLine(1)+", "+addresses.get(0).getAddressLine(2));
        }catch(Exception e)
        {

        }

        /*LatLng latLng1 = new LatLng(location.getLatitude(), location.getLongitude());
        LatLng latLng2 = new LatLng(Double.parseDouble(lat2.getText().toString()), Double.parseDouble(long2.getText().toString()));


        Double distance = CalculationByDistance(latLng1, latLng2);*/

        //tvDistance.setText(String.valueOf(distance));

    }


    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }



    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(LocationDistanceActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }


}
