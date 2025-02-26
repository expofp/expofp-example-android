package com.example.expofp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.expofp.common.Location;
import com.expofp.crowdconnected.Mode;
import com.expofp.fplan.FplanView;

import com.expofp.crowdconnected.CrowdConnectedProvider;
import com.expofp.fplan.contracts.FplanEventsListener;
import com.expofp.fplan.models.Bookmark;
import com.expofp.fplan.models.Category;
import com.expofp.fplan.models.Details;
import com.expofp.fplan.models.FloorPlanBoothBase;
import com.expofp.fplan.models.Route;
import com.expofp.fplan.models.Settings;
import com.expofp.indooratlas.IndoorAtlasProvider;

public class MainActivity extends AppCompatActivity {

    private FplanView _fplanView;

    @Override
    protected void onDestroy() {
        _fplanView.destroy();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _fplanView = findViewById(R.id.fplanView);
        _fplanView.openZipFromAssets("data_0.zip", null, null, this);
    }
}
