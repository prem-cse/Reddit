package com.example.reddit.Comments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.reddit.R;

public class WebViewActivity extends AppCompatActivity {

    private static final String TAG = "WebViewActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        WebView webView = findViewById(R.id.webview);
        final ProgressBar webviewLoadingProgressBar = findViewById(R.id.webviewLoadingProgressBar);
        webviewLoadingProgressBar.setVisibility(View.VISIBLE);

        Intent incomingintent = getIntent();
        String imgUrl = incomingintent.getStringExtra("@string/post_url");

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(imgUrl);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webviewLoadingProgressBar.setVisibility(View.GONE);
            }
        });


    }

}
