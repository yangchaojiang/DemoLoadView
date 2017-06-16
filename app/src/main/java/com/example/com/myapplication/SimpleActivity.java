package com.example.com.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.helper.loadviewhelper.load.LoadViewHelper;
import com.helper.loadviewhelper.help.OnLoadViewListener;


public class SimpleActivity extends Activity {

    private LoadViewHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        View contentLayout = findViewById(R.id.content_layout);
        helper = new LoadViewHelper(contentLayout);
        helper.setListener(new OnLoadViewListener() {
            @Override
            public void onRetryClick() {
                Toast.makeText(getApplicationContext(), "点击了重试", Toast.LENGTH_SHORT).show();
            }
        });
        helper.showLoading();
    }

    public void showError(View view) {
        helper.showError();
    }

    public void showEmpty(View view) {
        helper.showEmpty();
    }

    public void showLoading(View view) {
        helper.showLoading("加载中...");
    }

    public void showSuccess(View view) {
        helper.showContent();
    }
}
