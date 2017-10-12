package com.example.com.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import com.helper.loadviewhelper.load.LoadViewHelper;
import com.helper.loadviewhelper.help.VaryViewHelperX;


public class Example2Activity extends AppCompatActivity {

    private EditText editText;
    private WebView webView;
    private LoadViewHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example2);
        editText = (EditText) findViewById(R.id.editText1);
        webView = (WebView) findViewById(R.id.webView1);
        helper = new LoadViewHelper(webView);
        webView.setWebViewClient(webViewClient);
        webView.loadUrl(editText.getText().toString());
    }

    public void clickLoad(View view) {
        webView.loadUrl(editText.getText().toString());
    }

    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            helper.showLoading();
        }

        ;

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            helper.showContent();
        }

        ;

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

    };

}
