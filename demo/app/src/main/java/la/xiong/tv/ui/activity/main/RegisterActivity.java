package la.xiong.tv.ui.activity.main;

import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import la.xiong.tv.MyApplication;
import la.xiong.tv.R;
import la.xiong.tv.base.BaseTActivity;
import la.xiong.tv.util.AES;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends BaseTActivity {

    @BindView(R.id.registerButton)
    Button mRegisterButton;
    @BindView(R.id.phone_num)
    EditText mPhoneNum;
    @BindView(R.id.phone_uuid)
    EditText mPhoneUuid;
    @BindView(R.id.phone_code)
    EditText mPhoneCode;
    @BindView(R.id.image_code)
    ImageView mCodeImage;

    private OkHttpClient mHttpClient;
    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_register;
    }

    @Override
    protected void initViewsAndEvents() {
        mHttpClient  = new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url.host(), cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(url.host());
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                })
                .build();

        getCodeImage();

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void signUp() {
        String mobile = mPhoneNum.getText().toString().trim();
        String uuid = mPhoneUuid.getText().toString().trim();
        String code = mPhoneCode.getText().toString().trim();


        String content = "" + "|" + Integer.valueOf(String.valueOf(System.currentTimeMillis() / 1000)) + "|Android " + "1.2.2";

        String register = "mobile=" + mobile + "&password=123456&" + "img_code=" + code + "&signup_code=888888&device_info={os:Android,uuid: "+ uuid + ",version:1}";
        FormBody.Builder build = new FormBody.Builder();
        build.add("requestData", AES.encryptRequest(register));
        RequestBody formBody = build.build();

        final Request request = new Request.Builder()
                .url(" http://api.yzb8.co/tv/user/signup ")
                .post(formBody)
                .addHeader("Accept", "application/json;version=20181122;auth="+AES.encryptRequest(content))
                .build();
        Call call = mHttpClient.newCall(request);
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
                                Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }

                    JSONObject dataObject = ss.getJSONObject("data");
                    String access_token = dataObject.getString("access_token");
                    MyApplication.getInstance().setAccessToken(access_token);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterActivity.this,"注册获取token成功",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void getCodeImage() {

        String content = "" + "|" + Integer.valueOf(String.valueOf(System.currentTimeMillis() / 1000)) + "|Android " + "1.2.2";
        final Request request = new Request.Builder()
                .url("http://api.yzb8.co/site/captcha")
                .get()
                .addHeader("Accept", "application/json;version=20181122;auth="+ AES.encryptRequest(content))
                .build();
        Call call = mHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final byte[] image = response.body().bytes();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mCodeImage.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
                    }
                });
            }
        });

    }
}
