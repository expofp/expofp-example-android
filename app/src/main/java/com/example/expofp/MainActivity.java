package com.example.expofp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.expofp.common.Location;
import com.expofp.common.LocationProvider;
import com.expofp.crowdconnected.CrowdConnectedProvider;
import com.expofp.crowdconnected.Mode;
import com.expofp.fplan.FplanEventsListener;
import com.expofp.fplan.FplanView;
import com.expofp.fplan.Route;
import com.expofp.fplan.Settings;

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
                //.withGlobalLocationProvider()
                .withEventsListener(new FplanEventsListener() {
                    @Override
                    public void onFpConfigured() {
                        showMessage(activity, null, "OnFpConfigured Event");
                    }

                    @Override
                    public void onBoothClick(String boothName) {
                        showMessage(activity, "OnBoothClick Event", String.format(Locale.US, "Booth: '%s'", boothName));
                    }

                    @Override
                    public void onDirection(Route route) {
                        String message = String.format(Locale.US, "Distance: '%s'; Time: '%d'; From: '%s'; To: '%s';",
                                route.getDistance(), route.getTime(), route.getBoothFrom().getName(),  route.getBoothTo().getName());

                        showMessage(activity, "OnDirection Event", message);
                    }

                    @Override
                    public void onMessageReceived(String message) {

                    }
                });

        _fplanView = findViewById(R.id.fplanView);
        _fplanView.init(settings);
    }
}
