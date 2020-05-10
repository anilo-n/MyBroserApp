package com.example.mybroserapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private WebView myWebView;
    private EditText urlText;

    private static final String INITIAL_WEBSITE = "http://www.yahoo.co.jp";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myWebView = (WebView) findViewById(R.id.myWebView);
        urlText = (EditText) findViewById(R.id.urlText);

        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                getSupportActionBar().setSubtitle(view.getTitle());
                urlText.setText(url);
            }
        });
        myWebView.loadUrl(INITIAL_WEBSITE);
    }

    public void showWebsite(View view) {
        String url = urlText.getText().toString().trim();
        if (!Patterns.WEB_URL.matcher(url).matches()) {
            urlText.setError("error url");
        } else {
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }
            myWebView.loadUrl(url);
        }
    }

    public void urlClear(View view){
        urlText.setText("");
    }


    @Override //アクティビティを終わらせたときの動作
    protected void onDestroy() {
        super.onDestroy();
        if (myWebView != null) {
            myWebView.stopLoading();
            myWebView.setWebViewClient(null);
            myWebView.destroy();
        }
        myWebView = null;
    }
    @Override //戻るボタンの設定
    public void onBackPressed(){
        if(myWebView.canGoBack()){
            myWebView.goBack();
            return;
        }
        super.onBackPressed();
    }
}