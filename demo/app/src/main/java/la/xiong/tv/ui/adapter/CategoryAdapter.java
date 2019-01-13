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
import la.xiong.tv.bean.CategoryModel;
import la.xiong.tv.ui.activity.main.PlayListActicity;
import la.xiong.tv.ui.activity.play.PlayActivity;
import la.xiong.tv.util.ScreenUtil;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class CategoryAdapter extends CommonAdapter<CategoryModel> {

    private int mType;
    public CategoryAdapter(Context context, int layoutId, List<CategoryModel> datas, int type) {
        super(context, layoutId, datas);
        mType = type;
    }

    @Override
    public void convert(CommonViewHolder holder, final CategoryModel categoryModel) {
        ImageView image = (ImageView) holder.getView(R.id.item_category_image);
        TextView name = (TextView) holder.getView(R.id.item_category_name);
        if (mType == 0){
            Glide.with(mContext)
                    .load(categoryModel.getXinimg())
                    .bitmapTransform(new RoundedCornersTransformation(mContext, ScreenUtil.dp2px(mContext, 4), 0))
                    .into(image);
        }else if (mType == 1){
            Glide.with(mContext)
                    .load(categoryModel.getImg())
                    .bitmapTransform(new RoundedCornersTransformation(mContext, ScreenUtil.dp2px(mContext, 4), 0))
                    .into(image);
        }

        if (mType == 0){
            name.setText(categoryModel.getTitle());
        }else if (mType == 1){
            name.setText(categoryModel.getTitle());
        }


        holder.setOnClickListener(R.id.item_category_image, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PlayListActicity.class);
                if (mType == 0){
                    intent.putExtra("name", categoryModel.getAddress());
                }else if (mType == 1){
                    intent.putExtra("name", categoryModel.getName());
                }
                intent.putExtra("type", mType);
                mContext.startActivity(intent);
            }
        });
    }
}
