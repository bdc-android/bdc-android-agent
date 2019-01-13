package la.xiong.tv.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import la.xiong.androidquick.ui.adapter.CommonAdapter;
import la.xiong.androidquick.ui.adapter.CommonViewHolder;
import la.xiong.tv.R;
import la.xiong.tv.bean.VideoType;
import la.xiong.tv.ui.activity.main.VideoListActivity;
import la.xiong.tv.ui.activity.main.VideoWebViewActivity;
import la.xiong.tv.ui.activity.play.PlayerActivity;
import la.xiong.tv.util.ScreenUtil;

public class VideoCategoryListAdapter extends CommonAdapter<VideoType> {

    private List<VideoType> mDatas;
    private int mType;
    private String mToken = "";

    public VideoCategoryListAdapter(Context context, int layoutId, List<VideoType> datas, int type) {
        super(context, layoutId, datas);
        mType = type;
        mDatas = datas;
    }

    public void setToken(String token){
        mToken = token;
    }

    @Override
    public void convert(CommonViewHolder holder, final VideoType videoType) {
        ImageView image = (ImageView) holder.getView(R.id.item_category_image);
        TextView name = (TextView) holder.getView(R.id.item_category_name);

        if (mType == 1){
            Glide.with(mContext)
                    .load(videoType.getXinimg())
                    .bitmapTransform(new RoundedCornersTransformation(mContext, ScreenUtil.dp2px(mContext, 4), 0))
                    .into(image);
            name.setText(videoType.getTitle());
        }else if(mType == 2){
            Glide.with(mContext)
                    .load(videoType.getTp())
                    .bitmapTransform(new RoundedCornersTransformation(mContext, ScreenUtil.dp2px(mContext, 4), 0))
                    .into(image);
            name.setText(videoType.getBt());
        } else if (mType == 3){
            Glide.with(mContext)
                    .load("http://thyrsi.com/t6/641/1545745928x2890202707.jpg")
                    .bitmapTransform(new RoundedCornersTransformation(mContext, ScreenUtil.dp2px(mContext, 4), 0))
                    .into(image);
            name.setText(videoType.getTitle());
        } else if (mType == 4){
            Glide.with(mContext)
                    .load("http://vip.714an.cn" + videoType.getImg())
                    .bitmapTransform(new RoundedCornersTransformation(mContext, ScreenUtil.dp2px(mContext, 4), 0))
                    .into(image);
            name.setText(videoType.getTitle());
        } else if (mType == 5){
            Glide.with(mContext)
                    .load(videoType.getVideo_img())
                    .bitmapTransform(new RoundedCornersTransformation(mContext, ScreenUtil.dp2px(mContext, 4), 0))
                    .into(image);
            name.setText(videoType.getTitle());
        }

        holder.setOnClickListener(R.id.item_category_image, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, VideoListActivity.class);
                if (mType == 1){
                    intent.putExtra("url", videoType.getAddress());
                }else if (mType == 2){
                    intent.putExtra("url", videoType.getDz());
                }else if (mType == 3){
                    intent.putExtra("url", videoType.getId()+"");
                    intent.putExtra("token", mToken);
                }else if (mType == 4){
                    intent = new Intent(mContext, VideoWebViewActivity.class);
                    intent.putExtra("url", videoType.getUrl()+"");
                }else if (mType == 5){
                    intent = new Intent(mContext, PlayerActivity.class);
                    intent.putExtra("url", videoType.getVideo_url()+"");
                    intent.putExtra("title", videoType.getTitle()+"");
                }
                intent.putExtra("type", mType);
                mContext.startActivity(intent);
            }
        });
    }
}
