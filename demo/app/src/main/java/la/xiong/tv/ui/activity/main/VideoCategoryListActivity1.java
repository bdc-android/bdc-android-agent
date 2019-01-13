package la.xiong.tv.ui.activity.main;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import la.xiong.tv.R;
import la.xiong.tv.base.BaseTActivity;
import la.xiong.tv.bean.VideoType;
import la.xiong.tv.ui.adapter.VideoCategoryListAdapter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VideoCategoryListActivity1 extends BaseTActivity {

    private final static String TAG = VideoCategoryListActivity1.class.getSimpleName();

    @BindView(R.id.rv_category)
    RecyclerView mCategoryRv;

    String name = "";
    int mType = 1;
    Bundle bundle;

    private VideoCategoryListAdapter mAdapter;
    private List<VideoType> videoTypeList;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_category;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        bundle = extras;
    }


    @Override
    protected void initViewsAndEvents() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mCategoryRv.setLayoutManager(layoutManager);
        videoTypeList = new ArrayList<>();
        mAdapter = new VideoCategoryListAdapter(this, R.layout.item_category, videoTypeList, mType);
        mCategoryRv.setAdapter(mAdapter);

        getVideoList();
    }

    private void getVideoList() {
        OkHttpClient okHttpClient = new OkHttpClient();
        //if (mType == 0) {
            final Request request = new Request.Builder()
                    .url("http://api.xuwen1990.xyz/gdsp.txt")
                    .get()
                    //.post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString()))//默认就是GET请求，可以不写
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
                        JSONArray list = jsonObject.getJSONArray("pingtai");
                        final List<VideoType> playlList = new Gson().fromJson(list.toString(), new TypeToken<List<VideoType>>() {
                        }.getType());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                videoTypeList.addAll(playlList);
                                mAdapter.notifyDataSetChanged();
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        //} else if (mType == 1) {

            //categoryModelList.addAll(DaoManager.getInstance().getDaoSession().getCategoryModelDao().loadAll());
            //mAdapter.notifyDataSetChanged();

//            JSONObject jsonObject = new JSONObject();
//            try {
//                jsonObject.put("name", name);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            final Request request = new Request.Builder()
//                    .url(name)
//                    .get()//默认就是GET请求，可以不写
//                    .build();
//            Call call = okHttpClient.newCall(request);
//            call.enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    Log.d(TAG, "onFailure: ");
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    String result = response.body().string();
//                    Log.d(TAG, "onResponse: " + result);
//
//                    try {
//                        JSONObject jsonObject = new JSONObject(result);
//                        //JSONObject data = jsonObject.getJSONObject("zhubo");
//                        JSONArray list = jsonObject.getJSONArray("zhubo");
//                        final List<CategoryModel> playlList = new Gson().fromJson(list.toString(), new TypeToken<List<CategoryModel>>(){}.getType());
//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                categoryModelList.addAll(playlList);
//                                mAdapter.notifyDataSetChanged();
//                            }
//                        });
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        }


    }
}

