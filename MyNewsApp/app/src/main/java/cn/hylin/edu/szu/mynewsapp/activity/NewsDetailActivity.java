package cn.hylin.edu.szu.mynewsapp.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import cn.hylin.edu.szu.mynewsapp.R;

public class NewsDetailActivity extends AppCompatActivity {

    private WebView webViewNewsDetail;
    private String url;
    private ProgressBar newDetailProgressBar;
    private ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        webViewNewsDetail = (WebView) findViewById(R.id.newsDetail);
        newDetailProgressBar = (ProgressBar) findViewById(R.id.newDetailProgressBar);
        url = getIntent().getStringExtra("detailsUrl");

        ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        webViewNewsDetail.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webViewNewsDetail.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                newDetailProgressBar.setVisibility(View.VISIBLE);
                Log.i("newProgress",newProgress + "");
                if (newProgress == 100) {
                    newDetailProgressBar.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onReceivedTitle(WebView view, String title) {
                ab.setTitle(title);
            }
        });
        WebSettings settings = webViewNewsDetail.getSettings();
        //支持javascript
        settings.setJavaScriptEnabled(true);
        // 设置可以支持缩放
        settings.setSupportZoom(true);
        // 设置出现缩放工具
        settings.setBuiltInZoomControls(true);
        //扩大比例的缩放
        settings.setUseWideViewPort(true);
        //自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        settings.setDefaultTextEncodingName("utf-8");
        webViewNewsDetail.loadUrl(url);
    }
}
