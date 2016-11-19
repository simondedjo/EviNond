package com.app_cpslab.android.evinond; // Package of Flood Locator App

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.support.v7.widget.Toolbar;

import com.esri.android.map.MapView;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;

/**
 * Created by Simon on 6/10/16.
 */

/* Activity AddLocationInfo (Report a Flood 2/3:)
User refines the picture location using a map and a point feature
 */

public class AddLocation extends AppCompatActivity {

    MapView mv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout for the layout we created
        setContentView(R.layout.add_location);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.addLocationToolbar);

        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        // Set the back button arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Set the mapview
        mv = (MapView)findViewById(R.id.map1);
        //mv.getSpatialReference().isWGS84();

        // Call GPS location manager
        mv.getLocationDisplayManager().setShowLocation(true);

        // Enable map continously
        mv.enableWrapAround(true);
    }
    //Long press to add point on the map (the exact location of the photo/video)
    public void onLongPress(MotionEvent e) {

        Point geo = (Point) GeometryEngine
                .project(mv.toMapPoint(e.getX(), e.getY()),
                        mv.getSpatialReference(),
                        SpatialReference.create(4326));
        double latitude = geo.getY();
        double longitude = geo.getX();
    }


        // *** OnClick Toolbar: Return to report_flood layout
    public void returnReportFlood(View view) {

        Intent getNameScreenIntent = new Intent(this, ReportFlood.class);

        final int result = 1;

        getNameScreenIntent.putExtra("callingActivity", "ReportFlood");
        startActivityForResult(getNameScreenIntent, result);
    }

    // Click on button "Ajouter detail inondation" to open add_flood_info layout
    public void addFloodInfoButtonClick(View view) {

        Intent getNameScreenIntent = new Intent(this,
                AddFloodInfo.class);

        final int result = 1;

        getNameScreenIntent.putExtra("callingActivity", "AddFloodInfo");
        startActivityForResult(getNameScreenIntent, result);

    }

}










