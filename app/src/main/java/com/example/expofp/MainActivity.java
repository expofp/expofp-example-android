package com.example.expofp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

class BoothClickJSInterface {
    @JavascriptInterface
    public void postMessage(String boothName) {

    }
}

class FpReadyJSInterface {
    @JavascriptInterface
    public void postMessage(String message) {
    }
}

class DirectionJSInterface {
    @JavascriptInterface
    public void postMessage(String directionJson) {
    }
}

public class MainActivity extends AppCompatActivity {

    private WebView _webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _webView = (WebView) findViewById(R.id.webView);

        _webView.getSettings().setJavaScriptEnabled(true);
        _webView.getSettings().setDomStorageEnabled(true);
        _webView.getSettings().setJavaScriptEnabled(true);

        _webView.addJavascriptInterface(new BoothClickJSInterface(), "onBoothClickHandler");
        _webView.addJavascriptInterface(new FpReadyJSInterface(), "onFpConfiguredHandler");
        _webView.addJavascriptInterface(new DirectionJSInterface(), "onDirectionHandler");

        _webView.loadUrl("https://developer.expofp.com/examples/autumnfair.html");

    }

    public void onSelectBoothClick(View view) {
        _webView.evaluateJavascript("selectBooth('1306')", null);
    }

    public void onBuidDirectionClick(View view) {
        _webView.evaluateJavascript("selectRoute('1306', '2206')", null);
    }
}