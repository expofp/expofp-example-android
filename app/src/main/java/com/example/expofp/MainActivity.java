package com.example.expofp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.expofp.common.Location;
import com.expofp.fplan.FplanEventsListener;
import com.expofp.fplan.FplanView;
import com.expofp.fplan.Route;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private FplanView _fplanView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_select_booth) {
            selectBooth();
        }
        else if (id == R.id.action_select_exhibitor) {
            selectExhibitor();
        }
        else if (id == R.id.action_build_route) {
            selectRoute();
        }
        else if (id == R.id.action_set_position) {
            selectCurrentPosition();
        }
        else if (id == R.id.action_clear) {
            _fplanView.clear();
        }

        return super.onOptionsItemSelected(item);
    }

    private void selectBooth(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alert_select_booth, null);
        dialogBuilder.setView(dialogView);

        final EditText boothEditText = (EditText) dialogView.findViewById(R.id.boothEditText);
        boothEditText.setText("656");

        dialogBuilder.setTitle("Select booth");
        dialogBuilder.setPositiveButton("Ok", (dialog, whichButton) -> {
            try {
                if(boothEditText.getText() != null){
                    _fplanView.selectBooth(boothEditText.getText().toString());
                }
            }
            catch (Exception ex){
                Log.e("Demo", ex.toString());
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void selectExhibitor(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alert_select_exhibitor, null);
        dialogBuilder.setView(dialogView);

        final EditText exhibitorEditText = (EditText) dialogView.findViewById(R.id.exhibitorEditText);

        exhibitorEditText.setText("RPMXPO");

        dialogBuilder.setTitle("Select exhibitor");
        dialogBuilder.setPositiveButton("Ok", (dialog, whichButton) -> {
            try {
                if(exhibitorEditText.getText() != null){
                    _fplanView.selectExhibitor(exhibitorEditText.getText().toString());
                }
            }
            catch (Exception ex){
                Log.e("Demo", ex.toString());
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void selectRoute(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alert_build_rote, null);
        dialogBuilder.setView(dialogView);

        final EditText fromEditText = (EditText) dialogView.findViewById(R.id.fromEditText);
        final EditText toEditText = (EditText) dialogView.findViewById(R.id.toEditText);

        fromEditText.setText("519");
        toEditText.setText("656");

        dialogBuilder.setTitle("Build route");
        dialogBuilder.setPositiveButton("Ok", (dialog, whichButton) -> {
            try {
                if(fromEditText.getText() != null && toEditText.getText() != null) {
                    _fplanView.selectRoute(fromEditText.getText().toString(),
                            toEditText.getText().toString(), false);
                }
            }
            catch (Exception ex){
                Log.e("Demo", ex.toString());
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void selectCurrentPosition(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alert_select_position, null);
        dialogBuilder.setView(dialogView);

        final EditText latEditText = (EditText) dialogView.findViewById(R.id.latEditText);
        final EditText lonEditText = (EditText) dialogView.findViewById(R.id.lonEditText);

        latEditText.setText("38.254623");
        lonEditText.setText("-85.755180");

        dialogBuilder.setTitle("Set blue-dot");
        dialogBuilder.setPositiveButton("Ok", (dialog, whichButton) -> {
            try {
                if(latEditText.getText() != null && lonEditText.getText() != null) {
                    Double lat = Double.parseDouble(latEditText.getText().toString());
                    Double lon = Double.parseDouble(lonEditText.getText().toString());
                    _fplanView.selectCurrentPosition(new Location(null, null, null, null,
                            lat, lon), true);
                }
            }
            catch (Exception ex){
                Log.e("Demo", ex.toString());
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void showMessage(Activity activity, @Nullable String title, @Nullable String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        if(title != null && !title.equalsIgnoreCase("")){
            builder.setTitle(title);
        }

        if(message != null && !message.equalsIgnoreCase("")){
            builder.setMessage(message);
        }

        builder.setCancelable(true);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        _fplanView = findViewById(R.id.fplanView);
        _fplanView.destroy();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Activity activity = this;

        //noOverlay - Hides the panel with information about exhibitors
        com.expofp.fplan.Settings settings = new com.expofp.fplan.Settings("https://demo.expofp.com", false, false)
                //.withLocationProvider(new CrowdConnectedProvider(getApplication(), new com.expofp.crowdconnected.Settings("APP_KEY","TOKEN","SECRET")))
                //.withLocationProvider(new GpsProvider(getApplication()))
                .withEventsListener(new FplanEventsListener() {
                    @Override
                    public void onFpConfigured() {
                        Log.d("Demo", "OnFpConfigured event");
                    }

                    @Override
                    public void onBoothClick(String boothName) {
                        Log.d("Demo", "OnBoothClick event: " + String.format(Locale.US, "Booth: '%s'", boothName));
                    }

                    @Override
                    public void onDirection(Route route) {
                        String message = String.format(Locale.US, "Distance: '%s'; Time: '%d'; From: '%s'; To: '%s';",
                                route.getDistance(), route.getTime(), route.getBoothFrom().getName(),  route.getBoothTo().getName());

                        Log.d("Demo", "OnDirection event: " + message);
                    }

                    @Override
                    public void onMessageReceived(String message) {
                    }
                });

        _fplanView = findViewById(R.id.fplanView);
        _fplanView.init(settings);
    }
}
