package com.example.expofp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.expofp.common.Location;
import com.expofp.crowdconnected.Mode;
import com.expofp.fplan.FplanView;

import com.expofp.crowdconnected.CrowdConnectedProvider;
import com.expofp.fplan.contracts.DownloadZipToCacheCallback;
import com.expofp.fplan.contracts.FplanEventsListener;
import com.expofp.fplan.models.Bookmark;
import com.expofp.fplan.models.Category;
import com.expofp.fplan.models.Details;
import com.expofp.fplan.models.FloorPlanBoothBase;
import com.expofp.fplan.models.Route;
import com.expofp.fplan.models.Settings;
import com.expofp.indooratlas.IndoorAtlasProvider;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private FplanView _fplanView;
    private ConstraintLayout _progressBar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_main_menu, menu);
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
            _fplanView.selectCurrentPosition(new Location(45000.00, 14000.00, null, false, null,
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

    public void goButtonClick(View view) {
        EditText textView = findViewById(R.id.expoKeyEditText);
        String text = textView.getText().toString();
        String url = text.startsWith("https://") ? text : "https://" + text;
        url = url.endsWith(".expofp.com") ? url : url + ".expofp.com";

        _progressBar.setVisibility(View.VISIBLE);

        FplanView.downloadZipToCache(url, this, new DownloadZipToCacheCallback() {
            @Override
            public void onCompleted(String htmlFilePath) {
                _progressBar.setVisibility(View.INVISIBLE);
                _fplanView.openFileFromCache(null, new Settings());
            }

            @Override
            public void onError(String message) {
                _progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _progressBar = findViewById(R.id.progressBar);
        _fplanView = findViewById(R.id.fplanView);

        //_fplanView.load("https://demo.expofp.com", new Settings());
    }


}
