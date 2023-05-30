package com.example.expofp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.expofp.common.Location;
import com.expofp.fplan.Details;
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
        com.expofp.fplan.Settings settings = new com.expofp.fplan.Settings("https://demo.expofp.com", false)
                //.withLocationProvider(new CrowdConnectedProvider(getApplication(), new com.expofp.crowdconnected.Settings("APP_KEY","TOKEN","SECRET")))
                //.withGlobalLocationProvider()
                .withEventsListener(new FplanEventsListener() {
                    @Override
                    public void onFpConfigured() {
                        Log.d("Demo", "[onFpConfigured]");
                    }

                    @Override
                    public void onBoothClick(@Nullable String id, @Nullable String name) {
                        Log.d("Demo", String.format(Locale.US, "[onBoothClick] booth id: '%s'; booth name: '%s'", id, name));
                    }

                    @Override
                    public void onDirection(@Nullable Route route) {
                        if(route != null) {
                            String from = route.getBoothFrom() != null ? route.getBoothFrom().getName() : "null";
                            String to = route.getBoothTo() != null ? route.getBoothTo().getName() : "null";

                            String message = String.format(Locale.US, "[onDirection] distance: '%s'; time: '%d'; from: '%s'; to: '%s';",
                                    route.getDistance(), route.getTime(), from, to);

                            Log.d("Demo", message);
                        }
                        else {
                            Log.d("Demo", "route = NULL");
                        }
                    }

                    @Override
                    public void onMessageReceived(@Nullable String message) {
                        Log.d("Demo", String.format(Locale.US, "[onMessageReceived] message: '%s'", message));
                    }

                    @Override
                    public void onDetails(@Nullable Details details) {
                        Log.d("Demo", "[onDetails]");
                        if(details != null) {
                            Log.d("Demo", "details name=" + details.getName());
                        }
                        else{
                            Log.d("Demo", "details = NULL");
                        }
                    }

                    @Override
                    public void onExhibitorCustomButtonClick(String externalId, int buttonNumber, String buttonUrl) {
                        Log.d("Demo", "[onExhibitorCustomButtonClick] externalId="+externalId+"; buttonNumber="+buttonNumber+"; buttonUrl="+buttonUrl);
                    }
                });

        _fplanView = findViewById(R.id.fplanView);
        _fplanView.init(settings);
    }
}
