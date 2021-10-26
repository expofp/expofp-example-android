This is an example of how you can integrate ExpoFP maps into an android(Java) application.


## Configuring WebView

```java
 _webView.getSettings().setJavaScriptEnabled(true);
 _webView.getSettings().setDomStorageEnabled(true);
 
 _webView.loadUrl("https://developer.expofp.com/examples/autumnfair.html");
```
## JavaScript code invocation

Calling up the booth selection function:

```java
_webView.evaluateJavascript("selectBooth('1306')", null);
```

Calling the route building function:

```java
_webView.evaluateJavascript("selectRoute('1306', '2206')", null);
```

## Event handling

### Handling the plan load event

Event handler:

```java
class FpReadyJSInterface {
    @JavascriptInterface
    public void postMessage(String message) {
        //Some code
    }
}
```
Register event handler:

```java
_webView.addJavascriptInterface(new FpReadyJSInterface(), "onFpConfiguredHandler");
```

### Handling the booth selection event

Event handler:

```java
class BoothClickJSInterface {
    @JavascriptInterface
    public void postMessage(String boothName) {
        //Some code
    }
}
```
Register event handler:

```java
_webView.addJavascriptInterface(new BoothClickJSInterface(), "onBoothClickHandler");
```


### Route creation event handling

Event handler:

```java
class DirectionJSInterface {
    @JavascriptInterface
    public void postMessage(String directionJson) {
        //Some code
    }
}
```
Register event handler:

```java
_webView.addJavascriptInterface(new DirectionJSInterface(), "onDirectionHandler");
```

## Offline mode

Configuring caching:

```java
_webView.getSettings().setAppCachePath( getApplicationContext().getCacheDir().getAbsolutePath() );
_webView.getSettings().setAllowFileAccess( true );
_webView.getSettings().setAppCacheEnabled( true );

if (!isNetworkAvailable()) {
    _webView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
}
else {
    _webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT );
}
```

Network status:

```java
private boolean isNetworkAvailable() {
    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService( CONNECTIVITY_SERVICE );
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
}
```

## Android permissions

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android" 
          package="com.example.expofp">
 
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>

    <application
    ...
```

## Code example

```java
package com.example.expofp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

class BoothClickJSInterface {
    @JavascriptInterface
    public void postMessage(String boothName) {
        //Some code
    }
}

class FpReadyJSInterface {
    @JavascriptInterface
    public void postMessage(String message) {
        //Some code
    }
}

class DirectionJSInterface {
    @JavascriptInterface
    public void postMessage(String directionJson) {
        //Some code
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
        
        _webView.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
        _webView.getSettings().setAllowFileAccess(true);
        _webView.getSettings().setAppCacheEnabled(true);

        if (!isNetworkAvailable()) {
            _webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        else {
            _webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        }

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
    
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
```
