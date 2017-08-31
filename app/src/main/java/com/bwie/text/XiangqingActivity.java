package com.bwie.text;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class XiangqingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiangqing);
        setTitle(getIntent().getStringExtra("name"));
        WebView wv = (WebView) findViewById(R.id.wv);
        wv.loadUrl(getIntent().getStringExtra("url"));
    }
}
