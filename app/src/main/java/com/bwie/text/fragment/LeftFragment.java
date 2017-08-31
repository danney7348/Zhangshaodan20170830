package com.bwie.text.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwie.text.MessageLoginActivity;
import com.bwie.text.R;

/**
 * 作者： 张少丹
 * 时间：  2017/8/30.
 * 邮箱：1455456581@qq.com
 * 类的用途：
 */
public class LeftFragment extends Fragment {
    private View mRootView;
    private TextView login_style;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(mRootView == null){
            mRootView = View.inflate(getContext(), R.layout.fragment1_item,null);
        }
        login_style = mRootView.findViewById(R.id.login_style);
        login_style.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getContext(), MessageLoginActivity.class);
                startActivity(in);
            }
        });
        return mRootView;
    }
}
