package com.example.medwarrior.ui.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medwarrior.R;
import com.example.medwarrior.ui.MainActivity2;
import com.example.medwarrior.ui.ui.chatwin;
import com.example.medwarrior.ui.ui.holderfrag;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class fitnessact extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager = null;

    TextView distancetextview , steptextview;
    private Sensor stepsensor;
    ImageView play , senddetails;
    private  boolean isCountingstep = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private double totalDistance = 0;
    FirebaseAuth fauth;
    private Location lastLocation;

    private int tsteps = 0;
   int previewtotalstep = 0;
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = fauth.getCurrentUser();
//        if(currentUser != null){
//            Intent intent = new Intent(getApplicationContext(), chatwin.class);
//            startActivity(intent);
//        }
//    }
//



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitnessact);
        play = findViewById(R.id.play);
        distancetextview = findViewById(R.id.distancetextview);
        steptextview = findViewById(R.id.steptextview);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        stepsensor  = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        senddetails = findViewById(R.id.senddetails);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(isCountingstep){
                  startcounting();
              }else {
                  stopcounting();
              }

            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startactivity();
                play.setImageResource(R.drawable.baseline_pause_circle_24);

            }
        });
        senddetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), chatwin.class);
                startActivity(intent);
            }
        });



    }
    private void startactivity(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            startLocationUpdates();
        }

    }
    
    private void startLocationUpdates() {
        // Get location updates from the GPS
        // You can use either FusedLocationProviderClient or LocationManager
        // Here, we'll use FusedLocationProviderClient
        FusedLocationProviderClient locationClient = LocationServices.getFusedLocationProviderClient(this);
        com.google.android.gms.location.LocationRequest locationRequest = com.google.android.gms.location.LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(1000); // Update every 1 second (adjust as needed)

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location currentLocation = locationResult.getLastLocation();
                if (lastLocation != null) {
                    float[] distanceResult = new float[1];
                    Location.distanceBetween(lastLocation.getLatitude(), lastLocation.getLongitude(),
                            currentLocation.getLatitude(), currentLocation.getLongitude(), distanceResult);
                    float distance = distanceResult[0];
                    totalDistance += distance;
                    updateDistanceUI(totalDistance);
                }
                lastLocation = currentLocation;
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    private void updateDistanceUI(double distance) {
        distancetextview.setText(String.format(" %.2f meters", distance));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            } else {
                // Handle if the user denied the permission request
            }
        }
    }
    public void startcounting(){
        //super.onResume();
        isCountingstep = true;
        tsteps = 0;
        sensorManager.registerListener(this, stepsensor, SensorManager.SENSOR_DELAY_NORMAL);

    }
    public void stopcounting(){
        //super.onPause();
        isCountingstep = false;
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER){
            tsteps = (int) event.values[0];
           steptextview.setText(tsteps);
          //  progressBar.setProgress(currentsteps);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}