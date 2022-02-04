package com.example.expofp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.expofp.fplan.BoothSelectedCallback;
import com.expofp.fplan.FpConfiguredCallback;
import com.expofp.fplan.FplanView;
import com.expofp.fplan.Route;
import com.expofp.fplan.RouteCreatedCallback;


class RouteCallback implements RouteCreatedCallback {

    private FplanView _fplanView;

    public RouteCallback(FplanView fplanView){
        _fplanView = fplanView;
    }

    @Override
    public void onRouteCreated(Route route) {
        //Some code
    }
}

class BoothCallback implements BoothSelectedCallback {

    private FplanView _fplanView;

    public BoothCallback(FplanView fplanView){
        _fplanView = fplanView;
    }

    @Override
    public void onBoothSelected(String boothName) {
        //Some code
    }
}

class FpCallback implements FpConfiguredCallback{

    private FplanView _fplanView;

    public FpCallback(FplanView fplanView){
        _fplanView = fplanView;
    }

    @Override
    public void onFpConfigured() {
        //Some code
    }
}

public class MainActivity extends AppCompatActivity {

    private FplanView _fplanView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _fplanView = findViewById(R.id.fplanView);
        _fplanView.init("https://wayfinding.expofp.com", new BoothCallback(_fplanView), new FpCallback(_fplanView), new RouteCallback(_fplanView));

    }

    public void onSelectBoothClick(View view) {
        _fplanView.selectBooth("1306");
    }

    public void onBuidDirectionClick(View view) {
        _fplanView.buildRoute("1306", "2206", false);
    }
}
