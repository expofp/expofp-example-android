package com.example.expofp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.expofp.fplan.FplanEventListener;
import com.expofp.fplan.FplanView;
import com.expofp.fplan.Route;


public class MainActivity extends AppCompatActivity {

    private FplanView _fplanView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _fplanView = findViewById(R.id.fplanView);
        _fplanView.init("https://demo.expofp.com", new FplanEventListener() {
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

    public void onSelectBoothClick(View view) {
        _fplanView.selectBooth("720");
    }

    public void onBuidDirectionClick(View view) {
        _fplanView.buildRoute("720", "751", false);
    }
}
