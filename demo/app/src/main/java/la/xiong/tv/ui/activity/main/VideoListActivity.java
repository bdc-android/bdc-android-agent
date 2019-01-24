package la.xiong.tv.ui.activity.main;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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
import cn.lemon.view.RefreshRecyclerView;
import cn.lemon.view.adapter.Action;
import la.xiong.tv.R;
import la.xiong.tv.base.BaseTActivity;
import la.xiong.tv.bean.VideoModel;
import la.xiong.tv.ui.adapter.CardRecordAdapter;
import la.xiong.tv.ui.adapter.VideoListAdapter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class VideoListActivity extends BaseTActivity {

    private final static String TAG = VideoListActivity.class.getSimpleName();

    @BindView(R.id.rv_category)
    RefreshRecyclerView mRecyclerView;

    String url = "";
    String mToken = "";
    int mType;
    int page = 1;
    int id;
    Bundle bundle;

    //private VideoListAdapter mAdapter;
    private List<VideoModel> videoTypeList;
    private CardRecordAdapter mAdapter;

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
        url = bundle.getString("url");
        mToken = bundle.getString("token");
        mType = bundle.getInt("type");
        id = bundle.getInt("id");

        videoTypeList = new ArrayList<>();

        mAdapter = new CardRecordAdapter(this, mType);

        mRecyclerView.setSwipeRefreshColors(0xFF437845, 0xFFE44F98, 0xFF2FAC21);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addRefreshAction(new Action() {
            @Override
            public void onAction() {
                getVideoList(true);
            }
        });

        mRecyclerView.addLoadMoreAction(new Action() {
            @Override
            public void onAction() {
                page++;
                getVideoList(false);
            }
        });

        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.showSwipeRefresh();
                getVideoList(true);
            }
        });
    }

    private void getVideoList(final boolean isRefresh) {
        if (isRefresh) {
            page = 1;
        }
        OkHttpClient okHttpClient = new OkHttpClient();
        if (mType == 1) {
            final Request request = new Request.Builder()
                    .url(url)
                    .get()
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
                        JSONArray list = jsonObject.getJSONArray("zhubo");
                        final List<VideoModel> playlList = new Gson().fromJson(list.toString(), new TypeToken<List<VideoModel>>() {
                        }.getType());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isRefresh){
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
        } else if (mType == 2) {
            FormBody.Builder build = new FormBody.Builder();
            build.add("a", "0");
            build.add("b", "12");
            build.add("c", url);
            build.add("pink", "android");
            RequestBody formBody = build.build();
            final Request request = new Request.Builder()
                    .url("http://api.xuwen1990.xyz/bt/index/index/")
                    .post(formBody)//默认就是GET请求，可以不写
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
                        //JSONObject data = jsonObject.getJSONObject("zhubo");
                        JSONArray list = jsonObject.getJSONArray("data");
                        final List<VideoModel> playlList = new Gson().fromJson(list.toString(), new TypeToken<List<VideoModel>>() {
                        }.getType());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isRefresh){
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
        } else if (mType == 3) {
            Log.d(TAG, "page: " + page);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("cate_id", url);
                jsonObject.put("limit", 15);
                jsonObject.put("page", page);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final Request request = new Request.Builder()
                    .url(" http://www.df69e.cn/mobile/video/index ")
                    .addHeader("Cookie", "PHPSESSID=bcihqu854j174r12d0u9j8mmt0")
                    .addHeader("token", mToken)
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
                        JSONArray list = dataObject.getJSONArray("lists");
                        final List<VideoModel> playlList = new Gson().fromJson(list.toString(), new TypeToken<List<VideoModel>>() {
                        }.getType());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isRefresh){
                                    mRecyclerView.dismissSwipeRefresh();
                                    mAdapter.clear();
                                    mAdapter.addAll(playlList);
                                }else {
                                    mAdapter.addAll(playlList);
                                    if (playlList.size() < 15){
                                        mRecyclerView.showNoMore();
                                    }
                                }
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }else if (mType == 6) {
            Log.d(TAG, "page: " + page);

            final Request request = new Request.Builder()
                    .url("http://login.lure918.xyz/mobile/video/index?cate_id=" + id + "&page=" + page + "&limit=10")
                    .get()
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
                        JSONArray list = dataObject.getJSONArray("lists");
                        final List<VideoModel> playlList = new Gson().fromJson(list.toString(), new TypeToken<List<VideoModel>>() {
                        }.getType());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isRefresh){
                                    mRecyclerView.dismissSwipeRefresh();
                                    mAdapter.clear();
                                    mAdapter.addAll(playlList);
                                }else {
                                    mAdapter.addAll(playlList);
                                    if (playlList.size() < 10){
                                        mRecyclerView.showNoMore();
                                    }
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

