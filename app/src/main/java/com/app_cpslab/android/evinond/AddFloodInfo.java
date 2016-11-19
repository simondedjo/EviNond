package com.app_cpslab.android.evinond; // Package of Flood Locator App

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import JSONParser.Parser;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Simon on 6/11/16.
 */

// Activity AddFloodInfo (Report a Flood 3/3:) User
public class AddFloodInfo  extends AppCompatActivity {

    public TextView damageQuantityTv, damageCostTv;
    float damageQty = 0;

    private String srt; // cost
    private String damageInfo;
    private String depth;
    private String duration;
    private int damageQt;
    private String deviceId;

    private String lat = "-9999";
    private String lon = "-9999";
    private String alt = "-9999";
    private String acc = "-9999";
    private String media = "00000xx";
    private String mediaID ="00x8hh6fxcv";


    private static final MediaType JSON =
            MediaType.parse("application/json; charset=utf-8");

    private JSONObject JSONoparams;

    OkHttpClient client = new OkHttpClient();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout for the layout we created
        setContentView(R.layout.add_flood_info);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.addFloodInfoToolbar);

        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        // Set the back button arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //get the device id
        //deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        // IMPLEMENTATION OF DIALOG BOXES

        // Get reference of widgets from XML layout
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayoutAddLocation);

        // IMPLEMENTATION OF DAMAGE DIALOG BOX

        Button damageBtn = (Button) findViewById(R.id.damageButton);
        final TextView damageTv = (TextView) findViewById(R.id.damageTextView);

        damageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Build an AlertDialog
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AddFloodInfo.this);

                // String array for alert dialog multi choice items
                String[] damageItem = new String[]{
                        "House",
                        "Business",
                        "Industry",
                        "Education",
                        "Health Center",
                        "Security",
                        " Public Space",
                        "Agriculture",
                        "Road",
                        "Injured",
                        "Death",
                        "Homeless"
                };

                // Boolean array for initial selected items
                final boolean[] checkedDamageItem = new boolean[]{
                        false, // residence
                        false, // Commerce
                        false, // Industrie
                        false, // Education
                        false, // Centre Sante
                        false, // Securite
                        false, // Espace public
                        false, // Agriculture
                        false, // Voirie
                        false, // Blesse
                        false, // Mort
                        false // Deplace

                };

                // Convert the color array to list
                final List<String> damageItemList = Arrays.asList(damageItem);

                // Set multiple choice items for alert dialog

                builder.setMultiChoiceItems(damageItem, checkedDamageItem, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                        // Update the current focused item's checked status
                        checkedDamageItem[which] = isChecked;

                        // Get the current focused item
                        String currentDamageItem = damageItemList.get(which);

                        // Notify the current action
                        /*Toast.makeText(getApplicationContext(),
                                currentDamageItem + " " + isChecked, Toast.LENGTH_SHORT).show();*/
                    }
                });

                // Specify the dialog is not cancelable
                builder.setCancelable(false);

                // Set a title for alert dialog
                builder.setTitle("Flooded Assets");

                // Set the positive/yes button click listener
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when click positive button
                        damageTv.setText("");
                        for (int i = 0; i < checkedDamageItem.length; i++) {
                            boolean checked = checkedDamageItem[i];
                            if (checked) {
                                damageTv.setText(damageTv.getText() + damageItemList.get(i) + ", ");
                                damageInfo = damageTv.getText() + damageItemList.get(i) + ", ";
                            }
                        }
                    }
                });

                // Set the negative/no button click listener
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when click the negative button
                    }
                });

                // Set the neutral/cancel button click listener
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when click the neutral button
                    }
                });

                android.app.AlertDialog dialog = builder.create();
                // Display the alert dialog on interface
                dialog.show();
            }
        });


        // IMPLEMENTATION OF DAMAGE DIALOG BOX

        Button depthBtn = (Button) findViewById(R.id.depthButton);
        final TextView depthTv = (TextView) findViewById(R.id.depthTextView);


        final CharSequence[] depthValue = {"none", "0.3 - 0.5m", "0.5 - 1m", "1 - 1.5m", "1.5 - 2m", "2 - 2.5m", "2.5 - 3m", "3 - 3.5m", "3.5 - 4m", "> 4m"};

        depthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddFloodInfo.this);
                builder.setTitle("Flood depth (meter)");

                //builder.setIcon(R.drawable.icon);

                builder.setSingleChoiceItems(depthValue, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        Toast.makeText(getApplicationContext(), depthValue[item], Toast.LENGTH_SHORT).show();

                        depthTv.setText(depthValue[item]);
                        depth = (depthValue[item]).toString();
                    }
                });

                builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                Toast.makeText(AddFloodInfo.this, "Succes", Toast.LENGTH_SHORT).show();


                            }
                        });
                builder.setNegativeButton("",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(AddFloodInfo.this, "Fail", Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


        // IMPLEMENTATION OF DURATION DIALOG BOX

        Button durationBtn = (Button) findViewById(R.id.durationButton);
        final TextView durationTv = (TextView) findViewById(R.id.durationTextView);


        final CharSequence[] durationValue = {"none", "30mn - 1h", "1 - 3h", "3 - 6h", "6 - 12h", "12 - 24h", ">24h"};

        durationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddFloodInfo.this);
                builder.setTitle("Inundation Duration)");

                //builder.setIcon(R.drawable.icon);

                builder.setSingleChoiceItems(durationValue, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        Toast.makeText(getApplicationContext(), durationValue[item], Toast.LENGTH_SHORT).show();

                        durationTv.setText(durationValue[item]);
                        duration = durationValue[item].toString();
                    }
                });

                builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                // Toast.makeText(AddFloodInfo.this, "Succes", Toast.LENGTH_SHORT).show();
                            }
                        });

                builder.setNegativeButton("",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(AddFloodInfo.this, "Fail", Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        // IMPLEMENTATION OF QUANTITY DIALOG BOX
        damageQuantityTv = (TextView) findViewById(R.id.quantityTextView); // TextView

        final Button quantityBtn = (Button) findViewById(R.id.quantityButton);
        quantityBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                ShowQuantityDialog();
            }
        });

 // IMPLEMENTATION OF COST DIALOG BOX
        damageCostTv = (TextView) findViewById(R.id.costTextView); // TextView

        final Button costBtn = (Button) findViewById(R.id.costButton);


        //Create onclick listener class
        costBtn.setOnClickListener(new View.OnClickListener() {
            //When you click the button, Alert dialog will be showed
            public void onClick(View v) {
       /* Alert Dialog Code Start*/
                android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(AddFloodInfo.this);
                alert.setTitle("Estimated Damage"); //Set Alert dialog title here
                alert.setMessage("Enter amount:"); //Message here

                // Set an EditText view to get user input
                final EditText input = new EditText(AddFloodInfo.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);

                alert.setView(input);

                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //You will get as string input data in this variable.
                        // here we convert the input to a string and show in a toast.
                        srt = input.getEditableText().toString();
                        Toast.makeText(AddFloodInfo.this, srt, Toast.LENGTH_LONG).show();

                        damageCostTv.setText(srt);
                    } // End of onClick(DialogInterface dialog, int whichButton)
                }); //End of alert.setPositiveButton
                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                        dialog.cancel();
                    }
                }); //End of alert.setNegativeButton
                android.app.AlertDialog alertDialog = alert.create();
                alertDialog.show();
       /* Alert Dialog Code End*/
            }// End of onClick(View v)
        }); //button.setOnClickListener

// ... End IMPLEMENTATION OF COST DIALOG BOX
    }




    // .... Continue IMPLEMENTATION OF QUANTITY DIALOG BOX
    public void ShowQuantityDialog(){
        final android.app.AlertDialog.Builder popDialog = new android.app.AlertDialog.Builder(this);
        final SeekBar seek = new SeekBar(this);
        seek.setMax(100);
        seek.setKeyProgressIncrement(1);

        //popDialog.setIcon(android.R.drawable.btn_star_big_on);
        popDialog.setTitle("Number of affected assets (Example: 3 Houses)");
        popDialog.setView(seek);


        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {



            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){


                damageQuantityTv.setText("" + progress);
                damageQt = progress;
            }

            public void onStartTrackingTouch(SeekBar arg0) {
                //do something

// TODO Auto-generated method stub+bu
            }



            public void onStopTrackingTouch(SeekBar seekBar) {
//do something

            }
        });


// Button OK
        popDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        popDialog.create();
        popDialog.show();
    }

 // .... End IMPLEMENTATION OF QUANTITY DIALOG BOX





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }





    // *** OnClick Toolbar: Return to add_location_layout
    public void returnAddLocation(View view) {

        Intent getNameScreenIntent = new Intent(this, AddLocation.class);

        final int result = 1;

        getNameScreenIntent.putExtra("callingActivity", "AddLocation");
        startActivityForResult(getNameScreenIntent, result);
    }


    public void sendFloodInfoButtonClick(View view) {

        Bundle extras = getIntent().getExtras();
        String latitude = extras.getString("my_variable");

        //Toast.makeText(getApplicationContext(), latitude, Toast.LENGTH_SHORT).show();
        //damageQuantityTv.setText("meters");





        JSONoparams = new JSONObject();

        try {
            JSONoparams.put("FloodWaterDepth", depth);
            JSONoparams.put("FloodDuration", duration);
            JSONoparams.put("damagedAsset", damageInfo);
            JSONoparams.put("damageQuantity", damageQt);
            JSONoparams.put("estimatedCost", srt);

            JSONoparams.put("latitude", lat);
            JSONoparams.put("longitude", lon);
            JSONoparams.put("altitude", alt);
            JSONoparams.put("gps_accuracy", "000");
            JSONoparams.put("media", media);
            JSONoparams.put("mediaID", mediaID);
            JSONoparams.put("dateTime", Utils.printStandardDate(getApplicationContext()));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        SendDataToEC2 sendData = new SendDataToEC2();
        sendData.execute(JSONoparams);
    }
    // class to send data to the server in the background. it must extend AsyncTask
    private class SendDataToEC2 extends AsyncTask<JSONObject, Void, String>{

        @Override
        protected String doInBackground(JSONObject... jsonObjects) {
            RequestBody bodyData = RequestBody.create(JSON, String.valueOf(jsonObjects[0]));
            //replace http://localhost:5000 by the sever url
            Request request = new Request.Builder()
                    .url("http://ec2-52-24-221-33.us-west-2.compute.amazonaws.com/api/v1/report")
                    .post(bodyData)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
                //update UI according to the returned status of the request
                //e.g if status equal success display success message

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            String result = Parser.RepsonceParser(s);
            if (result == "success") {
                Toast.makeText(getApplicationContext(), "Data saved successfully", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                Log.d("AddFloodInfo", "Data Saving Failed");
            }


        }
    }
}
