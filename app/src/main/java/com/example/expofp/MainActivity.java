package com.example.expofp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.expofp.common.Location;
import com.expofp.crowdconnected.CrowdConnectedProvider;
import com.expofp.fplan.FplanEventsListener;
import com.expofp.fplan.FplanView;
import com.expofp.fplan.Route;
import com.expofp.fplan.Settings;

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
            _fplanView.selectBooth("720");
        } else if (id == R.id.action_select_exhibitor) {
            _fplanView.selectExhibitor("ExpoPlatform");
        } else if (id == R.id.action_build_route) {
            _fplanView.selectRoute("720", "751", false);
        } else if (id == R.id.action_set_position) {
            _fplanView.selectCurrentPosition(new Location(22270, 44950), false);
        } else if (id == R.id.action_clear) {
            _fplanView.clear();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Activity activity = this;

        //noOverlay - Hides the panel with information about exhibitors
        Settings settings = new Settings("https://demo.expofp.com", false)
                //.withLocationProvider(new CrowdConnectedProvider(activity, "APP_KEY", "TOKEN", "SECRET"), false)
                .withEventsListener(new FplanEventsListener() {
                    @Override
                    public void onFpConfigured() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setMessage("OnFpConfigured Event");
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
                    public void onBoothClick(String s) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setTitle("OnBoothClick Event");
                        builder.setMessage("Booth: " + s);
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
                    public void onDirection(Route route) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setTitle("OnDirection Event");
                        builder.setMessage("Distance: " + route.getDistance()
                                + "; Time:" + route.getTime()
                                + "; From:" + route.getBoothFrom().getName()
                                + "; To:" + route.getBoothTo().getName());
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
                });

        _fplanView = findViewById(R.id.fplanView);
        _fplanView.init(settings);
    }
}
