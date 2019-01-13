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
import la.xiong.tv.bean.CategoryModel;
import la.xiong.tv.manager.DaoManager;
import la.xiong.tv.ui.activity.main.PlayListActicity;
import la.xiong.tv.ui.activity.play.PlayActivity;
import la.xiong.tv.ui.activity.play.PlayerActivity;
import la.xiong.tv.util.ScreenUtil;

/**
 * Created by chenbiwei on 2018/10/27.
 */

public class PlayListAdapter extends CommonAdapter<CategoryModel> {

    private List<CategoryModel> mDatas;
    private int mType;

    public PlayListAdapter(Context context, int layoutId, List<CategoryModel> datas, int type) {
        super(context, layoutId, datas);
        mType = type;
        mDatas = datas;
    }

    @Override
    public void convert(CommonViewHolder holder, final CategoryModel categoryModel) {
        ImageView image = (ImageView) holder.getView(R.id.item_category_image);
        TextView name = (TextView) holder.getView(R.id.item_category_name);
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

        holder.setOnClickListener(R.id.item_category_image, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (MyApplication.getInstance().getMode() == 0){
                    intent = new Intent(mContext, PlayActivity.class);
                }else {
                    intent = new Intent(mContext, PlayerActivity.class);
                }
                if (mType == 0){
                    intent.putExtra("url", categoryModel.getPlay_url());
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
                }
                mContext.startActivity(intent);
            }
        });

        name.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mType == 0||mType == 1||mType == 3){
                    ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                    if (mType == 0 || mType == 1){
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
                    Toast.makeText(mContext,"复制成功",Toast.LENGTH_SHORT).show();
                    DaoManager.getInstance().getDaoSession().getCategoryModelDao().insert(categoryModel);
                }
                if(mType == 2){

                    DaoManager.getInstance().getDaoSession().getCategoryModelDao().delete(categoryModel);
                    mDatas.remove(categoryModel);
                    update(mDatas);
                }
                return true;
            }
        });

    }
}
