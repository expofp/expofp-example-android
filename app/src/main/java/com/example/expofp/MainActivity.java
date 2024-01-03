package com.example.expofp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.expofp.common.GlobalLocationProvider;
import com.expofp.common.Location;
import com.expofp.common.LocationProvider;
import com.expofp.crowdconnected.Mode;
import com.expofp.crowdconnected.Settings;
import com.expofp.fplan.Details;
import com.expofp.fplan.FplanEventsListener;
import com.expofp.fplan.FplanView;
import com.expofp.fplan.Route;
import com.expofp.crowdconnected.CrowdConnectedProvider;

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
                new Settings("b5397af0","a044661ef49648a5b3f6982716b58272",
                        "9RD3Bht95Os86IJ3727elX7B64fS23l6", Mode.IPS_AND_GPS);
        ccSettings.setServiceNotificationInfo("TEST", R.drawable.ic_booth);

        LocationProvider lp = new CrowdConnectedProvider(getApplication(), ccSettings);

        GlobalLocationProvider.init(lp);
        GlobalLocationProvider.start();

        com.expofp.fplan.Settings settings = new com.expofp.fplan.Settings()
                .withGlobalLocationProvider();

        _fplanView = findViewById(R.id.fplanView);
        _fplanView.init("https://parispackagingweek.expofp.com", settings);
    }
}
