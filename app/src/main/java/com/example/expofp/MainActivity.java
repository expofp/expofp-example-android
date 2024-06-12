package com.example.expofp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import com.expofp.common.LocationProvider;
import com.expofp.crowdconnected.Mode;
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
    protected void onDestroy() {
        _fplanView.destroy();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        com.expofp.crowdconnected.Settings lpSettings = new com.expofp.crowdconnected.Settings("9defbd49",
                "cd3aea7fa06a476d9cbb83a9594f5306","5c7A9fdCQ8N996O61F64Tw3n3jvol532", Mode.GPS_ONLY);
        LocationProvider lp = new CrowdConnectedProvider(getApplication(), lpSettings);

        com.expofp.fplan.models.Settings settings = new com.expofp.fplan.models.Settings()
                .withLocationProvider(lp);

        _fplanView = findViewById(R.id.fplanView);
        _fplanView.init("https://dlg-feldtage.expofp.com", settings);
    }
}
