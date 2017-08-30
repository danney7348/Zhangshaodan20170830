package com.bwie.text.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.text.R;

/**
 * 作者： 张少丹
 * 时间：  2017/8/30.
 * 邮箱：1455456581@qq.com
 * 类的用途：
 */

public class LeftFragment extends Fragment {
    private View mRootView;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(mRootView == null){
            mRootView = View.inflate(getContext(), R.layout.fragment1_item,null);
        }
        return mRootView;
    }
}
