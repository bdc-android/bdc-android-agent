package la.xiong.tv.ui.fragment.catalogue;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import la.xiong.androidquick.network.RetrofitManager;
import la.xiong.tv.MyApplication;
import la.xiong.tv.bean.CategoryModel;
import la.xiong.tv.network.TVApis;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/3/28.
 */

public class CataloguePresenter extends CatalogueContract.Presenter {
    private final static String TAG = CataloguePresenter.class.getSimpleName();

    private RetrofitManager mRetrofitManager;
    private Context mContext;

    @Inject
    public CataloguePresenter(Context mContext, RetrofitManager mRetrofitManager) {
        this.mContext = mContext;
        this.mRetrofitManager = mRetrofitManager;
    }



    @Override
    public void getCatalogues() {
        if (mType == 0){
            OkHttpClient okHttpClient = new OkHttpClient();
            final Request request = new Request.Builder()
                    //.url("http://api.4ud381.cn/mobile/live/index")
                    .url(MyApplication.getInstance().getHostUrl1())
                    .get()
                    //.post(RequestBody.create(null, ""))//默认就是GET请求，可以不写
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
//wanhua
//                        JSONObject jsonObject = new JSONObject(result);
//                        int code = jsonObject.getInt("code");
//                        if (code == 0) {
//                            JSONObject data = jsonObject.getJSONObject("data");
//                            JSONArray list = data.getJSONArray("lists");
//
//                            final List<CategoryModel> categoryModelList = new Gson().fromJson(list.toString(), new TypeToken<List<CategoryModel>>(){}.getType());
//
//                            mActivity.runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    mView.updateCatalogues(categoryModelList);
//                                }
//                            });
//
//                        }
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray list = jsonObject.getJSONArray("pingtai");

                        final List<CategoryModel> categoryModelList = new Gson().fromJson(list.toString(), new TypeToken<List<CategoryModel>>(){}.getType());

                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mView.updateCatalogues(categoryModelList);
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        }else if (mType == 1){
            OkHttpClient okHttpClient = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url("http://www.2l3371.cn/mobile/live/index")
                    .post(RequestBody.create(null, ""))//默认就是GET请求，可以不写
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
                        JSONObject data = jsonObject.getJSONObject("data");
                        JSONArray list = data.getJSONArray("lists");
                        final List<CategoryModel> categoryModelList = new Gson().fromJson(list.toString(), new TypeToken<List<CategoryModel>>(){}.getType());

                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mView.updateCatalogues(categoryModelList);
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        }




//        addSubscription(mRetrofitManager.createApi(MyApplication.getInstance().getApplicationContext(), TVApis.class)
//                .getCategoryModelList()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<List<CategoryModel>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(List<CategoryModel> categoryModelList) {
//                        mView.updateCatalogues(categoryModelList);
//                    }
//                }));
    }

    private Activity mActivity;
    @Override
    public void setActivity(Activity activity) {
        mActivity = activity;
    }

    private int mType;
    @Override
    public void setType(int type) {
        mType = type;
    }
}
