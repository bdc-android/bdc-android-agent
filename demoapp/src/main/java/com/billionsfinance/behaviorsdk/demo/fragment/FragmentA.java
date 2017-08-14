package com.billionsfinance.behaviorsdk.demo.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.billionsfinance.behaviorsdk.demo.R;

/**
 * Created by cbw on 2017/7/12.
 */
@SuppressLint("ValidFragment")
public class FragmentA extends BaseFragment{

    public FragmentA(FragmentTestActivity applyActivity) {
        super(applyActivity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item, null);
        Button button = (Button)v.findViewById(R.id.button);
        TextView tv = (TextView) v.findViewById(R.id.tv_fragment);
        tv.setText("FragmentA");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTestActivity.showPager(1);
            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
