package com.app_cpslab.android.evinond;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.LocationDisplayManager;
import com.esri.android.map.MapView;

import com.esri.android.map.event.OnSingleTapListener;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.LinearUnit;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.geometry.Unit;
import com.esri.core.map.Graphic;
import com.esri.core.map.GraphicsUtil;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.esri.core.tasks.ags.geoprocessing.GPFeatureRecordSetLayer;
import com.esri.core.tasks.geocode.Locator;
import com.esri.core.tasks.geocode.LocatorFindParameters;
import com.esri.core.tasks.geocode.LocatorGeocodeResult;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;


/**
 * Created by Simon on 6/12/16.
 */
public class FloodMap extends AppCompatActivity {

    MapView mv;

    LocationManager lm;
    String coord;
    double lat;
    double lon;

    Point pt;
    Point wgsPt;

    TextView coordTv;
    Button gpsBtn;

    Graphic myGraphic;
    GraphicsLayer graphicsLayer;

    SimpleMarkerSymbol boueyMarker;



    //private LocationManager locationManager;
    //private LocationListener locationListener;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout for the layout we created
        setContentView(R.layout.flood_map);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.floodMapToolbar);

        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        // Set the back button arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Set the mapview
        mv = (MapView) findViewById(R.id.map1);


        // Call GPS location manager and Display GPS location

        mv.getLocationDisplayManager().setShowLocation(true);
        mv.getLocationDisplayManager().start();
        mv.getLocationDisplayManager().start();

        // Enable map continously
        mv.enableWrapAround(true);

        // Set the Esri logo to be visible, and enable map to wrap around date line.
        mv.setEsriLogoVisible(true);

    }


    // *** OnClick Toolbar: Return to main activity layout
    public void returnMainActivity(View view) {

        Intent getNameScreenIntent = new Intent(this,
                MainActivity.class);

        final int result = 1;

        getNameScreenIntent.putExtra("callingActivity", "MainActivity");
        startActivityForResult(getNameScreenIntent, result);
    }

    public void gpsBtnClick(View view) {

        // Set the Coordinate TextView and Button
        coordTv = (TextView) findViewById(R.id.coordTextView);
        gpsBtn = (Button) findViewById(R.id.gpsButton);

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria crit = new Criteria();
        coord = lm.getBestProvider(crit, false);

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
        Location location = lm.getLastKnownLocation(coord);

        if(location != null)
        {

            lat = location.getLatitude();
            lon = location.getLongitude();


            //define the buoy locations
            //Point buoy1Loc = new Point(lat,lon, SpatialReference.WKID_WGS84);
            //Point buoy1Loc = new Point(5,5);

            //create a marker symbol
            //SimpleMarkerSymbol boueyMarker = new SimpleMarkerSymbol();

            boueyMarker = new SimpleMarkerSymbol(Color.BLUE, 13,
                    SimpleMarkerSymbol.STYLE.TRIANGLE);

            //boueyMarker.setColor(R.color.abc_color_highlight_material);
            //boueyMarker.setStyle(SimpleMarkerSymbol.STYLE.CIRCLE);
            //boueyMarker.setSize(10);

            //create graphics
            //myGraphic = new Graphic();

            GraphicsLayer graphicsLayer = new GraphicsLayer();
            graphicsLayer.addGraphic(myGraphic);

            mv.addLayer(graphicsLayer);

            pt = mv.toMapPoint(4, -5);
            myGraphic = new Graphic(pt, boueyMarker);
            graphicsLayer.addGraphic(myGraphic);
            wgsPt = (Point) GeometryEngine.project(pt,mv.getSpatialReference(),
                    SpatialReference.create(4326));
            mv.addLayer(graphicsLayer);
        }


        //Point myPoint = new Point();

        //myPoint.setX(lon);
        //myPoint.setY(lat);

        //coordTv.setText(Double.toString(lat));

    }
}
