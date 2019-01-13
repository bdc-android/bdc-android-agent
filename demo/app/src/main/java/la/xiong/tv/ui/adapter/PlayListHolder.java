package la.xiong.tv.ui.adapter;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import cn.lemon.view.adapter.BaseViewHolder;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import la.xiong.tv.MyApplication;
import la.xiong.tv.R;
import la.xiong.tv.bean.CategoryModel;
import la.xiong.tv.bean.VideoModel;
import la.xiong.tv.manager.DaoManager;
import la.xiong.tv.ui.activity.main.VideoWebViewActivity;
import la.xiong.tv.ui.activity.play.PlayActivity;
import la.xiong.tv.ui.activity.play.PlayerActivity;
import la.xiong.tv.util.ScreenUtil;

public class PlayListHolder extends BaseViewHolder<CategoryModel> {

    private ImageView image;
    private TextView name;
    private Context mContext;
    private int mType;
    private PlayListAdapterNew mAdapter;

    public PlayListHolder(Context context, ViewGroup parent, int type, PlayListAdapterNew playListAdapterNew) {
        super(parent, R.layout.item_category);
        mContext= context;
        mType = type;
        mAdapter= playListAdapterNew;
    }

    @Override
    public void setData(final CategoryModel categoryModel) {
        super.setData(categoryModel);
        if (mType == 3){
            Glide.with(mContext)
                    .load(categoryModel.getHead_image())
                    .bitmapTransform(new RoundedCornersTransformation(mContext, ScreenUtil.dp2px(mContext, 4), 0))
                    .into(image);
            name.setText(categoryModel.getNickname());
        }else {
            Glide.with(mContext)
                    .load(categoryModel.getImg())
                    .bitmapTransform(new RoundedCornersTransformation(mContext, ScreenUtil.dp2px(mContext, 4), 0))
                    .into(image);
            name.setText(categoryModel.getTitle());
        }

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (MyApplication.getInstance().getMode() == 0){
                    intent = new Intent(mContext, PlayActivity.class);
                }else {
                    intent = new Intent(mContext, PlayerActivity.class);
                }
                if (mType == 0){
                    //intent.putExtra("url", categoryModel.getPlay_url());
                    intent.putExtra("url", categoryModel.getAddress());
                    intent.putExtra("title", categoryModel.getTitle());
                }else if (mType == 1){
                    intent.putExtra("url", categoryModel.getPlay_url());
                    intent.putExtra("title", categoryModel.getTitle());
                }else if (mType == 2){
                    intent.putExtra("url", categoryModel.getPlay_url());
                    intent.putExtra("title", categoryModel.getTitle());
                }else if (mType == 3){
                    intent.putExtra("url", categoryModel.getVideo_url());
                    intent.putExtra("title", categoryModel.getNickname());
                }else if (mType == 4){
                    intent.putExtra("url", categoryModel.getUrl());
                    intent.putExtra("title", categoryModel.getTitle());
                }
                mContext.startActivity(intent);
            }
        });

        name.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mType == 0||mType == 1||mType == 3){
                    ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                    if (mType == 0){
                        cm.setText(categoryModel.getAddress());
                        categoryModel.setPlay_url(categoryModel.getAddress());
                    }
                    if (mType == 1){
                        cm.setText(categoryModel.getPlay_url());
                        categoryModel.setAddress(categoryModel.getPlay_url());
                    }
                    if (mType == 3){
                        cm.setText(categoryModel.getVideo_url());
                        categoryModel.setAddress(categoryModel.getVideo_url());
                        categoryModel.setPlay_url(categoryModel.getVideo_url());
                        categoryModel.setImg(categoryModel.getHead_image());
                        categoryModel.setTitle(categoryModel.getNickname());
                    }
//                    if (mType == 4){
//                        cm.setText(categoryModel.getUrl());
//                        categoryModel.setAddress(categoryModel.getUrl());
//                    }
                    Toast.makeText(mContext,"复制成功",Toast.LENGTH_SHORT).show();
                    DaoManager.getInstance().getDaoSession().getCategoryModelDao().insert(categoryModel);
                }
                if(mType == 2){

                    DaoManager.getInstance().getDaoSession().getCategoryModelDao().delete(categoryModel);
                    mAdapter.remove(categoryModel);
                    //mDatas.remove(categoryModel);
                    //update(mDatas);
                }
                return true;
            }
        });

    }

    @Override
    public void onInitializeView() {
        super.onInitializeView();
        image = findViewById(R.id.item_category_image);
        name = findViewById(R.id.item_category_name);
    }

    @Override
    public void onItemViewClick(CategoryModel object) {
        super.onItemViewClick(object);
        //点击事件
        Log.i("CardRecordHolder","onItemViewClick");
    }


}
