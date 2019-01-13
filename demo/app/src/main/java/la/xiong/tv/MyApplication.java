package la.xiong.tv;

import android.app.Application;

import io.vov.vitamio.Vitamio;
import la.xiong.androidquick.network.RetrofitManager;
import la.xiong.androidquick.tool.ToastUtil;
import la.xiong.tv.injector.component.ApplicationComponent;
import la.xiong.tv.injector.component.DaggerApplicationComponent;
import la.xiong.tv.injector.module.ApplicationModule;

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class MyApplication extends Application {

    private static MyApplication INSTANCE;
    private static int mPlayMode = 1;// 0 vitamio 1 ijkplayer
    private static String mToken;
    private static String mAccessToken;
    private static String mHostUrl1 = "http://api.hclyz.cn:81/mf/json.txt";
    private static String mHostUrl2 = "http://api.hclyz.cn:81/mf/";
    public String getHostUrl1() {
        return mHostUrl1;
    }

    public void setHostUrl1(String mHostUrl1) {
        MyApplication.mHostUrl1 = mHostUrl1;
    }

    public String getHostUrl2() {
        return mHostUrl2;
    }

    public void setHostUrl2(String mHostUrl2) {
        MyApplication.mHostUrl2 = mHostUrl2;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //获取全局变量Application
        if (INSTANCE == null) {
            INSTANCE = this;
        }
        //初始化ToastUtil
        ToastUtil.register(this);
        //初始化url
        RetrofitManager.initBaseUrl("http://www.quanmin.tv/json/");
        //vitamio
        Vitamio.isInitialized(this);
    }

    public void setMode(int mode){
        mPlayMode = mode;
    }

    public int getMode(){
        return mPlayMode;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }

    public String getAccessToken() {
        return mAccessToken;
    }

    public void setAccessToken(String accessToken) {
        mAccessToken = accessToken;
    }

    public static synchronized MyApplication getInstance() {
        return INSTANCE;
    }

    //dagger2:get ApplicationComponent
    public static ApplicationComponent getApplicationComponent() {
        return DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(INSTANCE)).build();
    }

}
