package la.xiong.tv.ui.adapter;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import la.xiong.androidquick.ui.adapter.CommonAdapter;
import la.xiong.androidquick.ui.adapter.CommonViewHolder;
import la.xiong.tv.MyApplication;
import la.xiong.tv.R;
import la.xiong.tv.bean.VideoModel;
import la.xiong.tv.bean.VideoType;
import la.xiong.tv.ui.activity.main.VideoListActivity;
import la.xiong.tv.ui.activity.main.VideoWebViewActivity;
import la.xiong.tv.ui.activity.play.PlayActivity;
import la.xiong.tv.ui.activity.play.PlayerActivity;
import la.xiong.tv.util.ScreenUtil;

public class VideoListAdapter extends CommonAdapter<VideoModel> {

    private List<VideoModel> mDatas;
    private int mType;

    public VideoListAdapter(Context context, int layoutId, List<VideoModel> datas, int type) {
        super(context, layoutId, datas);
        mType = type;
        mDatas = datas;
    }

    @Override
    public void convert(CommonViewHolder holder, final VideoModel videoModel) {
        ImageView image = (ImageView) holder.getView(R.id.item_category_image);
        TextView name = (TextView) holder.getView(R.id.item_category_name);

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
        }else if (mType == 3){
            Glide.with(mContext)
                    .load(videoModel.getImage())
                    .bitmapTransform(new RoundedCornersTransformation(mContext, ScreenUtil.dp2px(mContext, 4), 0))
                    .into(image);
            name.setText(videoModel.getTitle());
        }

        holder.setOnClickListener(R.id.item_category_image, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (MyApplication.getInstance().getMode() == 0){
                    intent = new Intent(mContext, PlayActivity.class);
                }else {
                    intent = new Intent(mContext, PlayerActivity.class);
                }
                if (mType == 1){
                    intent.putExtra("url", videoModel.getAddress());
                    intent.putExtra("title", videoModel.getTitle());
                }else if (mType == 2){
                    intent.putExtra("url", videoModel.getDz());
                    intent.putExtra("title", videoModel.getBt());
                }else if (mType == 3){
                    intent = new Intent(mContext, VideoWebViewActivity.class);
                    intent.putExtra("url", videoModel.getLink());
                    intent.putExtra("title", videoModel.getBt());
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
                } else if (mType == 3){
                    cm.setText(videoModel.getLink());
                }
                Toast.makeText(mContext,"复制成功",Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }
}

