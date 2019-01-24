package la.xiong.tv.ui.activity.main;

import android.content.Context;
import android.content.res.Resources;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import la.xiong.tv.R;
import la.xiong.tv.base.BaseTActivity;

public class VideoWebViewActivity extends BaseTActivity {

    @BindView(R.id.video_webview)
    WebView mVideoView;
    Bundle bundle;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_webview_video;
    }

    @Override
    protected void initViewsAndEvents() {
        String url = bundle.getString("url");
        WebView.setWebContentsDebuggingEnabled(true);

        WebSettings settings = mVideoView.getSettings();
        settings.setJavaScriptEnabled(true);

        settings.setDomStorageEnabled(true);
        settings.setAllowContentAccess(true);
        settings.setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        settings.setAppCachePath(appCachePath);
        settings.setAppCacheEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setDatabaseEnabled(true);

        mVideoView.loadUrl(url);
        mVideoView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                //String js = getClearAdDivJs(VideoWebViewActivity.this);

                //view.loadUrl(js);

                //view.loadUrl("javascript:function setTop(){document.querySelector('#dsycg1418').style.display=\"none\";}setTop();");
            }

        });
    }

    public static String getClearAdDivJs(Context context){
        String js = "javascript:";
        Resources res = context.getResources();
        String[] adDivs = res.getStringArray(R.array.adBlockDiv);
        for(int i=0;i<adDivs.length;i++){
            js += "var adDiv"+i+"= document.getElementByclassName('"+adDivs[i]+"');if(adDiv"+i+" != null)adDiv"+i+".parentNode.removeChild(adDiv"+i+");";
        }
        return js;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mVideoView.canGoBack()) {
            mVideoView.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        bundle = extras;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        //暂停WebView在后台的所有活动
//        mVideoView.onPause();
//        //暂停WebView在后台的JS活动
//        mVideoView.pauseTimers();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mVideoView.onResume();
//        mVideoView.resumeTimers();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mVideoView.destroy();
//        mVideoView = null;
//    }
}
