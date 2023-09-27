package com.example.expofp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.expofp.common.GlobalLocationProvider;
import com.expofp.common.LocationProvider;
import com.expofp.fplan.FplanView;
import com.expofp.crowdconnected.CrowdConnectedProvider;

public class MainActivity extends AppCompatActivity {

    private FplanView _fplanView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

        LocationProvider lp = new CrowdConnectedProvider(getApplication(),
                new com.expofp.crowdconnected.Settings("fc29e3b3","caf52b0ee2e64686bed72dc26040d105","2539Ta76rD4KI177y8enT2M3mIX1a93l"));
        GlobalLocationProvider.init(lp);
        GlobalLocationProvider.start();

        com.expofp.fplan.Settings settings = new com.expofp.fplan.Settings()
                .withGlobalLocationProvider();

        _fplanView = findViewById(R.id.fplanView);
        _fplanView.init("https://imexamerica23.expofp.com", settings);
    }
}
