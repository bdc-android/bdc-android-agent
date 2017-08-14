package com.billionsfinance.behaviorsdk.demo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by cbw on 2017/7/11.
 */

public class WebActivity extends Activity {

    WebView webView;
    String url = "http://www.jianshu.com/p/6a7c91f1d804";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        webView = (WebView)findViewById(R.id.web_view);
        setCookie();
        webSetting(webView.getSettings());

        webView.loadUrl(url);
    }

    private void setCookie() {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.flush();
        }else{
            CookieSyncManager.getInstance().sync();
        }
        cookieManager.setCookie(url, "userId = 10162589755");
        cookieManager.setCookie(url, "userId = 20162589755");
        cookieManager.setCookie(url, "deviceId = 30127475655");
        cookieManager.setCookie(url, "deviceId = 40127475655");
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void webSetting(WebSettings settings) {
        settings.setAllowFileAccess(true);
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(false);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(false);
        settings.setSupportZoom(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);//
        settings.setLoadWithOverviewMode(true);
        settings.setSavePassword(true);
        settings.setSaveFormData(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            settings.setAllowUniversalAccessFromFileURLs(true);
        }
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    }

}
