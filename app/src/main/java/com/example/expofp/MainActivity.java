package com.example.expofp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.expofp.common.GlobalLocationProvider;
import com.expofp.common.Location;
import com.expofp.common.LocationProviderEventsListener;
import com.expofp.crowdconnected.Mode;
import com.expofp.fplan.models.Bookmark;
import com.expofp.fplan.models.Details;
import com.expofp.fplan.FplanEventsListener;
import com.expofp.fplan.FplanView;

import com.expofp.fplan.models.Route;
import com.expofp.crowdconnected.CrowdConnectedProvider;
import com.expofp.indooratlas.IndoorAtlasProvider;

import java.util.Locale;

public class MainActivity extends AppCompatActivity  {

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
            _fplanView.selectBooth("305");
        } else if (id == R.id.action_select_exhibitor) {
            _fplanView.selectExhibitor("Aria Style");
        } else if (id == R.id.action_build_route) {
            _fplanView.selectRoute("305", "339", false);
        } else if (id == R.id.action_set_position) {
            _fplanView.selectCurrentPosition(new Location(45000.00, 14000.00, null, null,
                    null, null), true);
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


        com.expofp.crowdconnected.Settings ccSettings =
                new com.expofp.crowdconnected.Settings("bd931173", "5139171006c448219db05ca250afff45",
                        "ENYt9213LF8339rlS8P7tH2341VEdK52", Mode.IPS_ONLY);
        ccSettings.setServiceNotificationInfo("TEST", R.drawable.ic_booth);

        CrowdConnectedProvider provider = new CrowdConnectedProvider(getApplication(), ccSettings);

        GlobalLocationProvider.init(provider);
        GlobalLocationProvider.start();

        com.expofp.fplan.models.Settings settings = new com.expofp.fplan.models.Settings()
                .withGlobalLocationProvider();

        _fplanView = findViewById(R.id.fplanView);
        _fplanView.init("https://cloudnext24.expofp.com/", settings);

    }
}
