package la.xiong.tv.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import cn.lemon.view.adapter.BaseViewHolder;
import cn.lemon.view.adapter.RecyclerAdapter;
import la.xiong.tv.bean.CategoryModel;
import la.xiong.tv.bean.VideoModel;

public class PlayListAdapterNew extends RecyclerAdapter<CategoryModel> {

    private int mType;
    private Context mContext;

    public PlayListAdapterNew(Context context, int type) {
        super(context);
        mContext = context;
        mType = type;
    }

    @Override
    public BaseViewHolder<CategoryModel> onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new PlayListHolder(mContext, parent, mType, this);
    }
}
