package la.xiong.tv.ui.activity.main;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import la.xiong.tv.MyApplication;
import la.xiong.tv.R;
import la.xiong.tv.base.BaseTActivity;
import la.xiong.tv.bean.VideoType;
import la.xiong.tv.ui.adapter.VideoCategoryListAdapter;
import la.xiong.tv.util.AES;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class VideoCategoryListActivity3 extends BaseTActivity {

    private final static String TAG = VideoCategoryListActivity1.class.getSimpleName();

    @BindView(R.id.rv_category)
    RecyclerView mCategoryRv;

    String name = "";
    int mType = 3;
    Bundle bundle;
    String token = "";

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
        String name = bundle.getString("name");
        if (name != null && name.equals("baohe")){
            mType = 4;
        }if (name != null && name.equals("nba")){
            mType = 5;
        }
        mCategoryRv.setLayoutManager(layoutManager);
        videoTypeList = new ArrayList<>();
        mAdapter = new VideoCategoryListAdapter(this, R.layout.item_category, videoTypeList, mType);
        mCategoryRv.setAdapter(mAdapter);

        getVideoList(name);
    }

    private void getVideoList(String name) {
        if (mType == 4){
            String json = "{\"code\":\"1\",\"msg\":[{\"id\":56,\"title\":\"item1\",\"img\":\"\\/public\\/uploads\\/20181210\\/0138f97661cca041c5844991229d3526.png\",\"url\":\"http:\\/\\/cm8.xyz\\/91\\/?v=1\"},{\"id\":59,\"title\":\"item2\",\"img\":\"\\/public\\/uploads\\/20181213\\/d469786d11f3e8a260a3526b2293c54a.png\",\"url\":\"http:\\/\\/cm8.xyz\\/tv.html\"},{\"id\":57,\"title\":\"item3\",\"img\":\"\\/public\\/uploads\\/20181213\\/f5bc9e2b0f5e41b0c58e08bd71257a0f.png\",\"url\":\"http:\\/\\/cm8.xyz\\/wzxs\\/\"},{\"id\":49,\"title\":\"item4\",\"img\":\"\\/public\\/uploads\\/20181210\\/afc14d031ae604770cc8be546076b427.png\",\"url\":\"http:\\/\\/www.lc1994.com\\/\"},{\"id\":50,\"title\":\"item5\",\"img\":\"\\/public\\/uploads\\/20181209\\/af57b5661d94ca3ec0b30f9374c0713b.png\",\"url\":\"http:\\/\\/1.v08.xyz\\/4\\/\"},{\"id\":51,\"title\":\"item6\",\"img\":\"\\/public\\/uploads\\/20181209\\/a960158cea291250614a282937389020.png\",\"url\":\"http:\\/\\/cm8.xyz\\/xs\\/mp3.php\"},{\"id\":52,\"title\":\"item7\",\"img\":\"\\/public\\/uploads\\/20181209\\/955108dac36ed339f752d693ca5203f6.png\",\"url\":\"http:\\/\\/cm8.xyz\\/av\\/\"},{\"id\":53,\"title\":\"item8\",\"img\":\"\\/public\\/uploads\\/20181209\\/2558fe7f6336174f296c0d9013564528.png\",\"url\":\"http:\\/\\/1.v08.xyz\\/av\\/sae.php\"},{\"id\":58,\"title\":\"item9\",\"img\":\"\\/public\\/uploads\\/20181213\\/c9661bc0b38c333c9893a408b3deb18c.png\",\"url\":\"http:\\/\\/cm8.xyz\\/91\\/1.php\"},{\"id\":55,\"title\":\"item10\",\"img\":\"\\/public\\/uploads\\/20181210\\/bce73a7fe7559588b918fb69ed1515fe.png\",\"url\":\"http:\\/\\/x.cm8.xyz\\/\"},{\"id\":60,\"title\":\"item11\",\"img\":\"\\/public\\/uploads\\/20181213\\/232ed65b63ebfa6a7cd1679f8c62c7a2.png\",\"url\":\"http:\\/\\/cm8.xyz\\/tu\\/\"},{\"id\":61,\"title\":\"item12\",\"img\":\"\\/public\\/uploads\\/20181213\\/6d623979126350352f202049e2a3b680.png\",\"url\":\"http:\\/\\/cm8.xyz\\/gw\\/\"}]}";
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray list = jsonObject.getJSONArray("msg");
                final List<VideoType> playlList = new Gson().fromJson(list.toString(), new TypeToken<List<VideoType>>() {
                }.getType());
                videoTypeList.addAll(playlList);
                mAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if (mType == 3){
            String json = "{\"code\":0,\"msg\":\"成功\",\"data\":[{\"id\":1,\"title\":\"item1\",\"sort\":0},{\"id\":2,\"title\":\"item2\",\"sort\":0},{\"id\":3,\"title\":\"item3\",\"sort\":0},{\"id\":4,\"title\":\"item4\",\"sort\":0},{\"id\":5,\"title\":\"item5\",\"sort\":0},{\"id\":6,\"title\":\"item6\",\"sort\":0},{\"id\":7,\"title\":\"item7\",\"sort\":0},{\"id\":8,\"title\":\"item8\",\"sort\":0},{\"id\":9,\"title\":\"item9\",\"sort\":0},{\"id\":10,\"title\":\"item10\",\"sort\":0}]}";
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray list = jsonObject.getJSONArray("data");
                final List<VideoType> playlList = new Gson().fromJson(list.toString(), new TypeToken<List<VideoType>>() {
                }.getType());
                videoTypeList.addAll(playlList);
                mAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }

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
                        token = dataObject.getString("token");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(VideoCategoryListActivity3.this,"登录成功",Toast.LENGTH_SHORT).show();
                                mAdapter.setToken(token);
                                MyApplication.getInstance().setToken(token);
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else if (mType == 5){
            OkHttpClient okHttpClient = new OkHttpClient();
            FormBody.Builder build = new FormBody.Builder();
            build.add("service", "Home.VideoList");
            build.add("id", "30");
            RequestBody formBody = build.build();

            final Request request = new Request.Builder()
                    .url("http://wwvv.vaizuju.cn/api/public/")
                    .post(formBody)
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
                        JSONObject dataObject = jsonObject.getJSONObject("data");
                        JSONArray list = dataObject.getJSONArray("info");
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

        }

//        OkHttpClient okHttpClient = new OkHttpClient();
//        //if (mType == 0) {
//        final Request request = new Request.Builder()
//                .url("http://www.df69e.cn/mobile/video/cateList")
//                //.get()
//                .addHeader("Cookie","PHPSESSID=bcihqu854j174r12d0u9j8mmt0")
//                .addHeader("token","b187041eecbbaf720557d709e2be3cd5")
//                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), ""))//默认就是GET请求，可以不写
//                .build();
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d(TAG, "onFailure: ");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String result = response.body().string();
//                Log.d(TAG, "onResponse: " + result);
//
//                try {
//                    JSONObject jsonObject = new JSONObject(result);
//                    JSONArray list = jsonObject.getJSONArray("data");
//                    final List<VideoType> playlList = new Gson().fromJson(list.toString(), new TypeToken<List<VideoType>>() {
//                    }.getType());
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            videoTypeList.addAll(playlList);
//                            mAdapter.notifyDataSetChanged();
//                        }
//                    });
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
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


