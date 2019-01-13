package la.xiong.tv.ui.fragment.catalogue;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import la.xiong.androidquick.tool.DialogUtil;
import la.xiong.tv.MyApplication;
import la.xiong.tv.R;
import la.xiong.tv.base.BaseTFragment;
import la.xiong.tv.bean.CategoryModel;
import la.xiong.tv.ui.activity.main.PlayListActicity;
import la.xiong.tv.ui.activity.main.RegisterActivity;
import la.xiong.tv.ui.activity.main.VideoCategoryListActivity1;
import la.xiong.tv.ui.activity.main.VideoCategoryListActivity2;
import la.xiong.tv.ui.activity.main.VideoCategoryListActivity3;
import la.xiong.tv.ui.activity.main.VideoWebViewActivity;
import la.xiong.tv.ui.adapter.CategoryAdapter;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class CatalogueFragment extends BaseTFragment<CataloguePresenter> implements CatalogueContract.View{

    @BindView(R.id.rv_category)
    RecyclerView mCategoryRv;
    @BindView(R.id.empty_ll)
    LinearLayout empty_ll;
    @BindView(R.id.mode1)
    Button mModeButton1;
    @BindView(R.id.mode2)
    Button mModeButton2;
    @BindView(R.id.loveButton)
    Button mLoveButton;
    @BindView(R.id.videoButton1)
    Button mVideoButton1;
    @BindView(R.id.videoButton2)
    Button mVideoButton2;
    @BindView(R.id.videoButton3)
    Button mVideoButton3;
    @BindView(R.id.videoButtonTV)
    Button mVideoButtonTv;
    @BindView(R.id.videoButtonVip)
    Button mVideoButtonVip;
    @BindView(R.id.videoButtonHot)
    Button mVideoButtonHot;
    @BindView(R.id.videoButtonHot2)
    Button mVideoButtonHot2;
    @BindView(R.id.videoButtonRegister)
    Button mVideoButtonRegister;
    @BindView(R.id.videoButtonMoon)
    Button mVideoButtonMoon;
    @BindView(R.id.videoButtonBaoHe)
    Button mVideoButtonBaoHe;
    @BindView(R.id.videoButtonBaoHe2)
    Button mVideoButtonBaoHe2;
    @BindView(R.id.videoButtonNBA)
    Button mVideoButtonNBA;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private CategoryAdapter mAdapter;
    private List<CategoryModel> categoryModelList;
    private static int mType;

    public static CatalogueFragment newInstance(int type) {
        mType = type;
        Bundle args = new Bundle();
        CatalogueFragment fragment = new CatalogueFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViewsAndEvents() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        mCategoryRv.setLayoutManager(layoutManager);
        categoryModelList = new ArrayList<>();
        mAdapter = new CategoryAdapter(getContext(), R.layout.item_category, categoryModelList, mType);
        mCategoryRv.setAdapter(mAdapter);
        mPresenter.setActivity(getActivity());
        mPresenter.setType(mType);
        mPresenter.getCatalogues();

        mSwipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLUE,Color.GREEN);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mType == 0 || mType == 1){
                    mPresenter.getCatalogues();
                }else {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        if (mType == 0 || mType == 1){
            empty_ll.setVisibility(View.GONE);
        }else if (mType == 2){
            empty_ll.setVisibility(View.VISIBLE);
            mModeButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyApplication.getInstance().setMode(0);
                    Toast.makeText(mContext,"设置播放器为Vitamio",Toast.LENGTH_SHORT).show();

                }
            });
            mModeButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyApplication.getInstance().setMode(1);
                    Toast.makeText(mContext,"设置播放器为Ijkplayer",Toast.LENGTH_SHORT).show();

                }
            });
            mLoveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), PlayListActicity.class);
                    intent.putExtra("type", 2);
                    mContext.startActivity(intent);
                }
            });
            mVideoButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), VideoCategoryListActivity1.class);
                    intent.putExtra("type", mType);
                    mContext.startActivity(intent);
                }
            });
            mVideoButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), VideoCategoryListActivity2.class);
                    intent.putExtra("type", mType);
                    mContext.startActivity(intent);
                }
            });
            mVideoButton3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), VideoCategoryListActivity3.class);
                    intent.putExtra("type", mType);
                    intent.putExtra("name", "mVideoButton3");
                    mContext.startActivity(intent);
                }
            });
            mVideoButtonBaoHe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), VideoCategoryListActivity3.class);
                    intent.putExtra("type", mType);
                    intent.putExtra("name", "baohe");
                    mContext.startActivity(intent);
                }
            });
            mVideoButtonVip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), VideoWebViewActivity.class);
                    intent.putExtra("url", "http://www.b8r9.cn");
                    mContext.startActivity(intent);
                }
            });
            mVideoButtonTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), VideoWebViewActivity.class);
                    intent.putExtra("url", "http://www.leshi123.com/");
                    mContext.startActivity(intent);
                }
            });
            mVideoButtonHot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), PlayListActicity.class);
                    intent.putExtra("name", "热门");
                    intent.putExtra("type", 1);
                    mContext.startActivity(intent);
                }
            });
            mVideoButtonRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), RegisterActivity.class);
                    mContext.startActivity(intent);
                }
            });
            mVideoButtonMoon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), PlayListActicity.class);
                    intent.putExtra("name", "Moon");
                    intent.putExtra("type", 3);
                    mContext.startActivity(intent);
                }
            });
            mVideoButtonHot2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), PlayListActicity.class);
                    intent.putExtra("name", "Moon");
                    intent.putExtra("type", 4);
                    mContext.startActivity(intent);
                }
            });
            mVideoButtonBaoHe2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), PlayListActicity.class);
                    intent.putExtra("name", "baohe");
                    intent.putExtra("type", 4);
                    mContext.startActivity(intent);
                }
            });
            mVideoButtonNBA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), VideoCategoryListActivity3.class);
                    intent.putExtra("name", "nba");
                    intent.putExtra("type", 3);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_catalogue;
    }

    @Override
    public void showLoading() {
        DialogUtil.showLoadingDialog(getActivity());
    }

    @Override
    public void stopLoading() {
        DialogUtil.dismissLoadingDialog(getActivity());
    }

    @Override
    public void updateCatalogues(List<CategoryModel> data) {
        mSwipeRefreshLayout.setRefreshing(false);
        categoryModelList.clear();
        categoryModelList.addAll(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setContext(Activity activity) {

    }

}
