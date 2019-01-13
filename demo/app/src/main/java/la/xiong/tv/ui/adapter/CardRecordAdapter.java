package la.xiong.tv.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import cn.lemon.view.adapter.BaseViewHolder;
import cn.lemon.view.adapter.RecyclerAdapter;
import la.xiong.tv.bean.VideoModel;


public class CardRecordAdapter extends RecyclerAdapter<VideoModel> {

    private int mType;
    private Context mContext;

    public CardRecordAdapter(Context context, int type) {
        super(context);
        mContext = context;
        mType = type;
    }

    @Override
    public BaseViewHolder<VideoModel> onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new CardRecordHolder(mContext, parent, mType);
    }
}