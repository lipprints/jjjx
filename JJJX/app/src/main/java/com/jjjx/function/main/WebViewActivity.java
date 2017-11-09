package com.jjjx.function.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.jjjx.R;
import com.jjjx.function.base.BaseActivity;

/**
 * @author xz
 * @date 2017/11/6 0006
 * 通用web页面
 */

public class WebViewActivity extends BaseActivity {
    /**
     * 头部标题
     */
    public static final String WEB_TITLE = "WEB_TITLE";

    /**
     * 网页路径
     */
    public static final String WEB_URL = "WEB_URL";

    private WebView mWebView;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initView();
        initData();
    }

    private void initView() {
        mProgressBar = findViewById(R.id.awv_pb);
        mWebView = findViewById(R.id.awv_wv);

        mWebView.getSettings().setAppCacheEnabled(false);
        mWebView.getSettings().setAppCacheEnabled(false);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.CLOSE);// 默认缩放模式
        mWebView.getSettings().setDomStorageEnabled(true);

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                } else {
                    if (View.INVISIBLE == mProgressBar.getVisibility()) {
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                    mProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

        });

    }

    private void initData() {
        if (getIntent() != null) {
            String title = getIntent().getStringExtra(WebViewActivity.WEB_TITLE);
            if (!TextUtils.isEmpty(title)) {
                setTitle(title);
            }
            String url = getIntent().getStringExtra(WebViewActivity.WEB_URL);
            if (!TextUtils.isEmpty(url)) {
                mWebView.loadUrl(url);
            }
        }
    }

}
