package com.billionsfinance.behaviorsdk.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.billionsfinance.behaviorsdk.demo.R;

/**
 * Created by cbw on 2017/7/12.
 */

public class FragmentTestActivity extends FragmentActivity{

    private FragmentA fragmentA;
    private FragmentB fragmentB;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        mFragmentManager = getSupportFragmentManager();

        fragmentA = new FragmentA(this);
        addFragment(fragmentA, "fragmentA");
    }

    public void showPager(int page){
        if (page == 0){
            removeAllFragment();
            if (fragmentA == null){
                fragmentA = new FragmentA(this);
            }
            addFragment(fragmentA, "fragmentA");
        }else {
            removeAllFragment();
            if (fragmentB == null) {
                fragmentB = new FragmentB(this);
            }
            addFragment(fragmentB, "fragmentB");
        }
    }


    private void removeAllFragment() {
        if (fragmentA != null) {
            removeFragment(fragmentA);
        }
        if (fragmentB != null) {
            removeFragment(fragmentB);
        }
    }


    /**
     * @time 2017/2/21 10:14
     * @desc 显示Fragment的方法
     */
    public void replaceFragment(Fragment fragment) {
        mFragmentManager.beginTransaction().replace(R.id.fragment_activity_base, fragment).commit();
    }

    public void addFragment(Fragment fragment) {
        mFragmentManager.beginTransaction().add(R.id.fragment_activity_base, fragment).commit();
    }

    public void addFragment(Fragment fragment, String tag) {
        mFragmentManager.beginTransaction().add(R.id.fragment_activity_base, fragment, tag).commit();
    }

    public void attachFragment(Fragment fragment) {
        mFragmentManager.beginTransaction().attach(fragment).commit();
    }

    public void hideFragment(Fragment fragment) {
        mFragmentManager.beginTransaction().hide(fragment).commit();
    }

    public void detachFragment(Fragment fragment) {
        mFragmentManager.beginTransaction().detach(fragment).commit();
    }

    public void removeFragment(Fragment fragment) {
        mFragmentManager.beginTransaction().remove(fragment).commit();
    }

    public void showFragment(Fragment fragment) {
        mFragmentManager.beginTransaction().show(fragment).commit();
    }

}
