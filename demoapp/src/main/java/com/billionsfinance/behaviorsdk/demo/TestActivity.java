package com.billionsfinance.behaviorsdk.demo;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.billionsfinance.behaviorsdk.hook.manager.HookViewManager;

/**
 * Created by cbw on 2017/6/23.
 */

public class TestActivity extends Activity implements View.OnClickListener {

    private Button show_dialog;
    private Button show_pop;
    private Button screen_catch;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        show_dialog = (Button) findViewById(R.id.show_dialog);
        show_pop = (Button) findViewById(R.id.show_pop);
        screen_catch = (Button) findViewById(R.id.screen_catch);
        imageView = (ImageView) findViewById(R.id.image_view);

        show_dialog.setOnClickListener(this);
        show_pop.setOnClickListener(this);
        screen_catch.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_dialog:
                showDialog();
                break;
            case R.id.show_pop:
                showPopWindow(v);
                break;
            case R.id.screen_catch:
//                try {
//                    Bitmap bitmap = ScreenUtils.captureWithoutStatusBar(this);
//                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//                    String aa = Base64Utils.encryptBASE64(baos.toByteArray());
//
//                    File dir = LogCollectorUtility.getExternalDir(TestActivity.this, "cache");
//                    CacheUtils.getInstance(dir).put("testa", aa);
//
//                    byte[] bytes = Base64Utils.decryptBASE64(aa);
//
//                    Bitmap bb = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                    imageView.setImageBitmap(bb);
//
//                }catch (Exception e){
//                    e.printStackTrace();
//                }

                //imageView.setImageBitmap(bitmap);

//                if (!dir.exists()) {
//                    dir.mkdirs();
//                }
//
//                BitmapFactory.Options o = new BitmapFactory.Options();
//
//                o.inJustDecodeBounds = true;
//
//                try {
//                    BitmapFactory.decodeStream(new FileInputStream(new File(dir, "aaa")), null, o);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                BitmapFactory.Options o2 = new BitmapFactory.Options();
//                // 根据原始图片长宽和需要的长宽计算采样比例，必须是2的倍数，
//                //  IMAGE_WIDTH_DEFAULT=768， IMAGE_HEIGHT_DEFAULT=1024
//                int needWidth = (int) (1024 * 1.0 / o.outHeight * o.outWidth);
//                o2.inSampleSize = 2;
//                // 每像素采用RGB_565的格式保存
//                o2.inPreferredConfig = Bitmap.Config.RGB_565;
//                // 根据压缩参数的设置进行第二次解码
//                Bitmap b = null;
//                try {
//                    b = BitmapFactory.decodeStream(new FileInputStream(new File(dir, "aaa")), null, o2);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, needWidth, 1024, true);
//
//                imageView.setImageBitmap(scaledBitmap);
////          b.recycle();  // b.recycle will cause prev Bitmap.createScaledBitmap null pointer exception on b occasionally
//                System.gc();
//
//
////                try {
////                    FileOutputStream fos = new FileOutputStream(new File(dir, "aaa"));
////                    //通过io流的方式来压缩保存图片
////                    boolean isSuccess = bitmap.compress(Bitmap.CompressFormat.PNG, 80, fos);
////                    fos.flush();
////                    fos.close();
////
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }


                break;
        }
    }

    private void showDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("测试dialog")
                .setMessage("测试")
                //.setPositiveButton()
                //.create();
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.setCancelable(false);
        dialog.show();

        HookViewManager.getInstance().hookView(dialog.getWindow().getDecorView());
    }

    private void showPopWindow(View view) {
        View mPopView = getLayoutInflater().inflate(R.layout.popou_window_select, null);
        TextView mPopTitle = (TextView) mPopView.findViewById(R.id.title_text);
        ListView mLv = (ListView) mPopView.findViewById(R.id.select_list);

        final PopupWindow mPopWindow = new PopupWindow(mPopView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setFocusable(true);

        mPopTitle.setText("PopupWindow测试");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1);
        adapter.add("1111111");
        adapter.add("2222222");
        adapter.add("3333333");
        mLv.setAdapter(adapter);

        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mPopWindow.dismiss();
            }
        });

        mPopWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        //HookViewManager.getInstance().hookView(mPopView);
    }

}
