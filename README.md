# ExpoFP Fplan - Android library for displaying expo plans

This is an example of how you can integrate ExpoFP maps into an android(Java) application.

Documentation: https://expofp.github.io/expofp-mobile-sdk/android-sdk/

## Code example

```java
package com.example.expofp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.expofp.common.Location;
import com.expofp.fplan.Details;
import com.expofp.fplan.FplanEventsListener;
import com.expofp.fplan.FplanView;
import com.expofp.fplan.Route;

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
            _fplanView.selectBooth("656");
        } else if (id == R.id.action_select_exhibitor) {
            _fplanView.selectExhibitor("RPMXPO");
        } else if (id == R.id.action_build_route) {
            _fplanView.selectRoute("519", "656", false);
        } else if (id == R.id.action_set_position) {
            _fplanView.selectCurrentPosition(new Location(null, null, null, null,
                    38.180023, -85.845180), true);
        } else if (id == R.id.action_clear) {
            _fplanView.clear();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        _fplanView.destroy();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        com.expofp.fplan.Settings settings = new com.expofp.fplan.Settings("https://demo.expofp.com")
                .withEventsListener(new FplanEventsListener() {
                    @Override
                    public void onFpConfigured() {
                    }

                    @Override
                    public void onBoothClick(@Nullable String id, @Nullable String name) {
                    }

                    @Override
                    public void onDirection(@Nullable Route route) {
                    }

                    @Override
                    public void onMessageReceived(@Nullable String message) {
                    }

                    @Override
                    public void onDetails(@Nullable Details details) {
                    }

                    @Override
                    public void onExhibitorCustomButtonClick(String externalId, int buttonNumber, String buttonUrl) {
                    }
                });

        _fplanView = findViewById(R.id.fplanView);
        _fplanView.init(settings);
    }
}
```

## Screenshot

<img src="https://user-images.githubusercontent.com/60826376/171269389-6aca9de6-98a9-481c-9da1-47252e749601.png" width=40%>
