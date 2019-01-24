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
import la.xiong.tv.bean.VideoModel;
import la.xiong.tv.ui.activity.main.VideoWebViewActivity;
import la.xiong.tv.ui.activity.play.PlayActivity;
import la.xiong.tv.ui.activity.play.PlayerActivity;
import la.xiong.tv.util.ScreenUtil;

class CardRecordHolder extends BaseViewHolder<VideoModel> {

    private ImageView image;
    private TextView name;
    private Context mContext;
    private int mType;

    public CardRecordHolder(Context context, ViewGroup parent, int type) {
        super(parent, R.layout.item_category);
        mContext= context;
        mType = type;
    }

    @Override
    public void setData(final VideoModel videoModel) {
        super.setData(videoModel);
        if (mType == 1){
            Glide.with(mContext)
                    .load(videoModel.getImg())
                    .bitmapTransform(new RoundedCornersTransformation(mContext, ScreenUtil.dp2px(mContext, 4), 0))
                    .into(image);
            name.setText(videoModel.getTitle());
        }else if (mType == 2){
            Glide.with(mContext)
                    .load(videoModel.getTp())
                    .bitmapTransform(new RoundedCornersTransformation(mContext, ScreenUtil.dp2px(mContext, 4), 0))
                    .into(image);
            name.setText(videoModel.getBt());
        }else if (mType == 3 || mType == 6){
            Glide.with(mContext)
                    .load(videoModel.getImage())
                    .bitmapTransform(new RoundedCornersTransformation(mContext, ScreenUtil.dp2px(mContext, 4), 0))
                    .into(image);
            name.setText(videoModel.getTitle());
        }

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (MyApplication.getInstance().getMode() == 0){
                    intent = new Intent(mContext, PlayActivity.class);
                }else if (MyApplication.getInstance().getMode() == 1){
                    intent = new Intent(mContext, PlayerActivity.class);
                }else {
                    intent = new Intent(mContext, VideoWebViewActivity.class);
                }
                if (mType == 1){
                    intent.putExtra("url", videoModel.getAddress());
                    intent.putExtra("title", videoModel.getTitle());
                }else if (mType == 2){
                    intent.putExtra("url", videoModel.getDz());
                    intent.putExtra("title", videoModel.getBt());
                }else if (mType == 3){
                    //intent = new Intent(mContext, VideoWebViewActivity.class);
                    intent.putExtra("url", videoModel.getLink());
                    intent.putExtra("title", videoModel.getBt());
                }else if (mType == 6){
                    //intent = new Intent(mContext, VideoWebViewActivity.class);
                    intent.putExtra("url", videoModel.getLink());
                    intent.putExtra("title", videoModel.getTitle());
                }

                mContext.startActivity(intent);
            }
        });

        name.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                if (mType == 1){
                    cm.setText(videoModel.getAddress());
                } else if (mType == 2){
                    cm.setText(videoModel.getDz());
                } else if (mType == 3 || mType == 6){
                    cm.setText(videoModel.getLink());
                }
                Toast.makeText(mContext,"复制成功",Toast.LENGTH_SHORT).show();
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
    public void onItemViewClick(VideoModel object) {
        super.onItemViewClick(object);
        //点击事件
        Log.i("CardRecordHolder","onItemViewClick");
    }
}