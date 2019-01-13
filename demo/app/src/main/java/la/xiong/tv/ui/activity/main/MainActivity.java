package la.xiong.tv.ui.activity.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.flyco.tablayout.SlidingTabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import la.xiong.tv.MyApplication;
import la.xiong.tv.R;
import la.xiong.tv.base.BaseTActivity;
import la.xiong.tv.ui.fragment.catalogue.CatalogueFragment;
import la.xiong.tv.ui.fragment.home.HomeFragment;
import la.xiong.tv.util.AES;
import okhttp3.Authenticator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class MainActivity extends BaseTActivity {

    @BindView(R.id.vp_tab_ftl)
    ViewPager mViewPager;

    @BindView(R.id.ftl_main_tab)
    SlidingTabLayout mSlidingTabLayout;

    private String[] mTitles = {"首页", "栏目一","栏目二","栏目三"};

    private MyPagerAdapter mAdapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsAndEvents() {
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mSlidingTabLayout.setViewPager(mViewPager, mTitles);

        login();
        //getList();
        //signUp();
        getUrl();
    }

    private void getUrl() {
        OkHttpClient okHttpClient  = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("http://vip.714an.cn/login/login/zhibjk.html")
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.d(TAG, "onResponse: " + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject msg = jsonObject.getJSONObject("msg");
                    String pingtai = msg.getString("pingtai");
                    String zhubo = msg.getString("zhubo");
                    if (!TextUtils.isEmpty(pingtai)){
                        MyApplication.getInstance().setHostUrl1(pingtai);
                    }
                    if (!TextUtils.isEmpty(zhubo)){
                        MyApplication.getInstance().setHostUrl2(zhubo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void getList() {
        OkHttpClient okHttpClient  = new OkHttpClient();
        String content = "05924ef1a5dd1a44ca5f03694496cafb" + "|" + Integer.valueOf(String.valueOf(System.currentTimeMillis() / 1000)) + "|Android " + "1.2.2";
        final Request request = new Request.Builder()
                .url("http://api.yzb8.co/tv/room/index?requestData=ZDB4RlpFNTNlV3RTSzFSeVRtaEdaamxsTHpKSFVUMDlPam94TXpnNE1UUXdNVFl6TlRReE9USTE")
                .get()//默认就是GET请求，可以不写
                .addHeader("version","20181122")
                .addHeader("Authorization","Bearer 05924ef1a5dd1a44ca5f03694496cafb")
                .addHeader("Accept", "application/json;version=20181122;auth="+AES.encryptRequest(content))
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.d(TAG, "onResponse: " + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String content = jsonObject.getString("responseData");
                    String decryResult = AES.decryptResponse(content);
                    Log.d(TAG, "onResponse: decryResult == " + decryResult);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void login() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", "15605761349");
            jsonObject.put("password", "123456");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("http://www.2l3371.cn/mobile/user/login")
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString()))//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.d(TAG, "onResponse: " + result);

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    String token = dataObject.getString("token");
                    MyApplication.getInstance().setToken(token);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = HomeFragment.newInstance();
                    break;
                case 1:
                    fragment = CatalogueFragment.newInstance(0);
                    break;
                case 2:
                    fragment = CatalogueFragment.newInstance(1);
                    break;
                case 3:
                    fragment = CatalogueFragment.newInstance(2);
                    break;
                default:
                    break;
            }

            return fragment;
        }
    }
}
