# ExpoFP Fplan - Android library for displaying expo plans

This is an example of how you can integrate ExpoFP maps into an android(Java) application.

Documentation: https://github.com/expofp/expofp-android-sdk

Code example:

```java
package com.example.expofp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.expofp.fplan.FplanEventListener;
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
            _fplanView.selectBooth("720");
        }
        else if(id == R.id.action_build_route){
            _fplanView.buildRoute("720", "751", false);
        }
        else if(id == R.id.action_set_position){
            _fplanView.setCurrentPosition(22270, 44950);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _fplanView = findViewById(R.id.fplanView);

        //noOverlay - Hides the panel with information about exhibitors
        _fplanView.init("https://demo.expofp.com", false, new FplanEventListener() {
            @Override
            public void onFpConfigured() {

            }

            @Override
            public void onBoothSelected(String boothName) {

            }

            @Override
            public void onRouteCreated(Route route) {

            }
        });
    }
}
```

![1111](https://user-images.githubusercontent.com/60826376/171269389-6aca9de6-98a9-481c-9da1-47252e749601.png)
