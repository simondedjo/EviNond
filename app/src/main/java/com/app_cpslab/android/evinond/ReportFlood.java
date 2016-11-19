package com.app_cpslab.android.evinond;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;
import android.provider.MediaStore;
import android.widget.Toast;


public class ReportFlood extends AppCompatActivity {


    LocationManager locationManager;
    String provider;

    Location location;



    private static int ACTIVITY_START_CAMERA_APP = 0;

    private ImageView photoCapturedImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_flood);

        // Set the layout for the layout we created
        setContentView(R.layout.report_flood);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        // Set the back button arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        photoCapturedImageView = (ImageView) findViewById(R.id.photoImageView);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        provider = locationManager.getBestProvider(new Criteria(), false);

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

        location = locationManager.getLastKnownLocation(provider);

    }


    // *** OnClick Toolbar: Return to main activity layout
    public void returnMainActivity(View view) {

        Intent getNameScreenIntent = new Intent(this,
                MainActivity.class);

        final int result = 1;

        getNameScreenIntent.putExtra("callingActivity", "ReportFlood");
        startActivityForResult(getNameScreenIntent, result);
    }

    // Click on button "suivant" to open add_location layout
    public void addLocationButtonClick(View view) {

        Intent getNameScreenIntent = new Intent(this,
                AddLocation.class);

        final int result = 1;

        getNameScreenIntent.putExtra("callingActivity", "ReportFlood");
        startActivityForResult(getNameScreenIntent, result);

    }



    // *** Camera: Take Picture

    public void takePhoto(View view) {

        //Toast.makeText(this, "Camera button pressed", Toast.LENGTH_SHORT).show();

        Intent callCameraAppIntent = new Intent();

        callCameraAppIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(callCameraAppIntent, ACTIVITY_START_CAMERA_APP);


    }

    protected void onActivityResult (int requestCode, int resultCode, Intent data) {

        if(requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK) {

            Toast.makeText(this, "Picture Taken Successfully", Toast.LENGTH_SHORT).show();

            Bundle extras = data.getExtras();

            Bitmap photoCapturedBitmap = (Bitmap) extras.get("data");

            photoCapturedImageView.setImageBitmap(photoCapturedBitmap);


            // Get Long and Lat info

            Double lat = location.getLatitude();
            Double lng = location.getLongitude();
            Double alt = location.getAltitude();

            // Create string to hold the coordinates

            String lat_media = Double.toString(lat);
            String lon_media = Double.toString(lng);
            String alt_media = Double.toString(alt);


            Intent i = new Intent(getApplicationContext(), ReportFlood.class);
            i.putExtra("my_variable",lat_media);
            startActivity(i);

        }
    }


}

