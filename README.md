# ExpoFP Fplan - Android library for displaying expo plans

This is an example of how you can integrate ExpoFP maps into an android(Java) application.

Documentation: https://github.com/expofp/expofp-android-sdk

## Code example

```java
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
                    }

                    @Override
                    public void onBoothClick(String s) {
                    }

                    @Override
                    public void onDirection(Route route) {
                    }
                });

        _fplanView = findViewById(R.id.fplanView);
        _fplanView.init(settings);
    }
}

```

## Screenshot

<img src="https://user-images.githubusercontent.com/60826376/171269389-6aca9de6-98a9-481c-9da1-47252e749601.png" width=40%>
