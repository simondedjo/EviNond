package com.app_cpslab.android.evinond; // Package of Flood Locator App

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    //  Click on button "Report a Flood" to open report_flood layout
    public void reportClick(View view) {

        Intent getNameScreenIntent = new Intent(this,
                ReportFlood.class);

        final int result = 1;

        getNameScreenIntent.putExtra("callingActivity", "MainActivity");
        startActivityForResult(getNameScreenIntent, result);

    }

    // Click on button "FLood Risk" to open flood_map layout
    public void floodMapClick(View view) {

        Intent getNameScreenIntent = new Intent(this,
                RoutingSample.class);

        final int result = 1;

        getNameScreenIntent.putExtra("callingActivity", "MainActivity");
        startActivityForResult(getNameScreenIntent, result);

    }
}
