package la.xiong.tv.ui.activity.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.lemon.view.RefreshRecyclerView;
import cn.lemon.view.adapter.Action;
import la.xiong.tv.MyApplication;
import la.xiong.tv.R;
import la.xiong.tv.base.BaseTActivity;
import la.xiong.tv.bean.CategoryModel;
import la.xiong.tv.manager.DaoManager;
import la.xiong.tv.ui.adapter.CategoryAdapter;
import la.xiong.tv.ui.adapter.PlayListAdapter;
import la.xiong.tv.ui.adapter.PlayListAdapterNew;
import la.xiong.tv.ui.fragment.catalogue.CataloguePresenter;
import la.xiong.tv.util.AES;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by chenbiwei on 2018/10/27.
 */

public class PlayListActicity extends BaseTActivity {

    private final static String TAG = PlayListActicity.class.getSimpleName();

    @BindView(R.id.rv_category)
    RefreshRecyclerView mRecyclerView;

    String name="";
    int mType;
    Bundle bundle;
    int page = 1;
    private PlayListAdapterNew mAdapter;
    private List<CategoryModel> categoryModelList;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_refresh_video_list;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        bundle = extras;
    }


    @Override
    protected void initViewsAndEvents() {
        name = bundle.getString("name");
        mType = bundle.getInt("type");
        DaoManager.getInstance().init(this);

        categoryModelList = new ArrayList<>();

        mAdapter = new PlayListAdapterNew(this, mType);

        mRecyclerView.setSwipeRefreshColors(0xFF437845, 0xFFE44F98, 0xFF2FAC21);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addRefreshAction(new Action() {
            @Override
            public void onAction() {
                getPlayList(name,true);
            }
        });

        mRecyclerView.addLoadMoreAction(new Action() {
            @Override
            public void onAction() {
                page++;
                getPlayList(name,false);
            }
        });

        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.showSwipeRefresh();
                getPlayList(name,true);
            }
        });


        mSharedPreferences = getSharedPreferences("save_data", 0);
        mEditor = mSharedPreferences.edit();

        //getPlayList(name);
    }

    private void getPlayList(String name, final boolean isRefresh){
        if (isRefresh) {
            page = 1;
        }
        OkHttpClient okHttpClient = new OkHttpClient();
        if (mType == 0){
            final Request request = new Request.Builder()
                    .url(MyApplication.getInstance().getHostUrl2() + name)
                    .get()
                    //.post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString()))//默认就是GET请求，可以不写
                    .build();
//            JSONObject jsonObject = new JSONObject();
//            try {
//                jsonObject.put("name", name);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            final Request request = new Request.Builder()
//                    .url("http://api.4ud381.cn/mobile/live/anchors")
//                    .addHeader("token","6998D721ED43892F44622CE7F6C4E3E4")
//                    .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString()))//默认就是GET请求，可以不写
//                    .build();
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
//                        JSONObject jsonObject = new JSONObject(result);
//                        int code = jsonObject.getInt("code");
//                        if (code == 0) {
//                            JSONObject data = jsonObject.getJSONObject("data");
//                            final JSONArray list = data.getJSONArray("lists");
//
//                            final List<CategoryModel> playlList = new Gson().fromJson(list.toString(), new TypeToken<List<CategoryModel>>(){}.getType());
//
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    if (isRefresh){
//                                        mRecyclerView.dismissSwipeRefresh();
//                                        mAdapter.clear();
//                                        mAdapter.addAll(playlList);
//                                        mRecyclerView.showNoMore();
//                                    }
//
//                                    //categoryModelList.addAll(playlList);
//                                    //mAdapter.notifyDataSetChanged();
//                                }
//                            });
//
//                        }

                        JSONObject jsonObject = new JSONObject(result);
                        final JSONArray list = jsonObject.getJSONArray("zhubo");
                        final List<CategoryModel> playlList = new Gson().fromJson(list.toString(), new TypeToken<List<CategoryModel>>(){}.getType());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isRefresh) {
                                    mRecyclerView.dismissSwipeRefresh();
                                    mAdapter.clear();
                                    mAdapter.addAll(playlList);
                                    mRecyclerView.showNoMore();
                                }
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }else if (mType == 1){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("name", name);
                jsonObject.put("username", "15605761349");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String url = "http://www.2l3371.cn/mobile/live/anchors";
            if (name.equals("热门")){
                url = "http://www.2l3371.cn/mobile/live/proposed_anchors";
            }
            final Request request = new Request.Builder()
                    .url(url)
                    .addHeader("token", MyApplication.getInstance().getToken())
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
                        int code = jsonObject.getInt("code");
                        if (code == 0) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            JSONArray list = data.getJSONArray("lists");

                            final List<CategoryModel> playlList = new Gson().fromJson(list.toString(), new TypeToken<List<CategoryModel>>(){}.getType());

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (isRefresh){
                                        mRecyclerView.dismissSwipeRefresh();
                                        mAdapter.clear();
                                        mAdapter.addAll(playlList);
                                        mRecyclerView.showNoMore();
                                    }

                                    //categoryModelList.addAll(playlList);
                                    //mAdapter.notifyDataSetChanged();
                                }
                            });

                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        else if (mType == 2){
            mRecyclerView.dismissSwipeRefresh();
            categoryModelList.clear();
            categoryModelList.addAll(DaoManager.getInstance().getDaoSession().getCategoryModelDao().loadAll());
            mAdapter.clear();
            mAdapter.addAll(categoryModelList);
            mRecyclerView.showNoMore();
        }else if (mType == 3){
            String content = MyApplication.getInstance().getAccessToken() + "|" + Integer.valueOf(String.valueOf(System.currentTimeMillis() / 1000)) + "|Android " + "1.2.2";
            final Request request = new Request.Builder()
                    .url("http://api.yzb8.co/tv/room/index?requestData=" + AES.encryptRequest("act=hot&page=" + page))
                    .get()//默认就是GET请求，可以不写
                    .addHeader("version","20181122")
                    .addHeader("Authorization","Bearer " + MyApplication.getInstance().getAccessToken())
                    .addHeader("Accept", "application/json;version=20181122;auth="+ AES.encryptRequest(content))
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

                        JSONObject ss = new JSONObject(decryResult);
                        int code = ss.getInt("code");
                        final String message = ss.getString("message");
                        if (code == 0){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(PlayListActicity.this,message,Toast.LENGTH_SHORT).show();
                                    String saveList = mSharedPreferences.getString("type3","");
                                    if (!TextUtils.isEmpty(saveList)){
                                        final List<CategoryModel> playlList = new Gson().fromJson(saveList, new TypeToken<List<CategoryModel>>(){}.getType());
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                mRecyclerView.dismissSwipeRefresh();
                                                mRecyclerView.showNoMore();
                                                mAdapter.clear();
                                                mAdapter.addAll(playlList);
                                                //categoryModelList.addAll(playlList);
                                                //mAdapter.notifyDataSetChanged();
                                            }
                                        });
                                    }
                                }
                            });
                            return;
                        }
                        JSONObject data = ss.getJSONObject("data");

                        final JSONArray list = data.getJSONArray("item");
                        final List<CategoryModel> playlList = new Gson().fromJson(list.toString(), new TypeToken<List<CategoryModel>>(){}.getType());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isRefresh){
                                    mRecyclerView.dismissSwipeRefresh();
                                    mAdapter.clear();
                                    mAdapter.addAll(playlList);
                                    categoryModelList.clear();
                                    categoryModelList.addAll(playlList);
                                }else {
                                    mAdapter.addAll(playlList);
                                    categoryModelList.addAll(playlList);
                                    if (playlList.size() < 30){
                                        mRecyclerView.showNoMore();
                                    }
                                }
                                mEditor.putString("type3", new Gson().toJson(categoryModelList));
                                mEditor.commit();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }else if (mType == 4){
            String url = "http://vip.714an.cn/login/login/vlist.html?page=";
            if (name!=null && name.equals("baohe")){
                url = "http://vip.714an.cn/login/login/hplist.html?page=";
            }
            final Request request = new Request.Builder()
                    .url(url + page)
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
                        int code = jsonObject.getInt("code");
                        if (code == 0){
                            mRecyclerView.dismissSwipeRefresh();
                            mRecyclerView.showNoMore();
                            return;
                        }

                        final JSONArray list = jsonObject.getJSONArray("msg");
                        final List<CategoryModel> playlList = new Gson().fromJson(list.toString(), new TypeToken<List<CategoryModel>>(){}.getType());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isRefresh){
                                    mRecyclerView.dismissSwipeRefresh();
                                    mAdapter.clear();
                                    mAdapter.addAll(playlList);
                                    categoryModelList.clear();
                                    categoryModelList.addAll(playlList);
                                }else {
                                    mAdapter.addAll(playlList);
                                    categoryModelList.addAll(playlList);

                                }
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
