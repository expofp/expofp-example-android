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
import com.expofp.indooratlas.IndoorAtlasProvider;

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

        com.expofp.fplan.models.Settings settings = new com.expofp.fplan.models.Settings()
                //.withLocationProvider(new CrowdConnectedProvider(getApplication(), new com.expofp.crowdconnected.Settings("APP_KEY","TOKEN","SECRET", Mode.IPS_AND_GPS)))
                //.withLocationProvider(new IndoorAtlasProvider(getApplication(), "API_KEY", "API_SECRET_KEY"))
                .withEventsListener(new FplanEventsListener() {
                    @Override
                    public void onFpConfigured() {
                    }

                    @Nullable
                    @Override
                    public void onFpConfigureError(int errorCode, String description) {
                    }

                    @Override
                    public void onBoothClick(@Nullable FloorPlanBoothBase booth) {
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
                    public void onBookmarkClick(Bookmark bookmark) {
                    }

                    @Override
                    public void onCategoryClick(Category category) {
                    }

                    @Override
                    public void onExhibitorCustomButtonClick(String externalId, int buttonNumber, String buttonUrl) {
                    }
                });


        _fplanView = findViewById(R.id.fplanView);
        _fplanView.init("https://demo.expofp.com", settings);
    }
}
