package com.billionsfinance.behaviorsdk.demo.fragment;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 *
 */
@SuppressLint("ValidFragment")
public class BaseFragment extends Fragment{
    public FragmentTestActivity fragmentTestActivity;

    public BaseFragment(){}

    public BaseFragment(FragmentTestActivity fragmentTestActivity){
        this.fragmentTestActivity = fragmentTestActivity;
    }

}
