package com.bwie.text.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.VolumeShaper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwie.text.MainActivity;
import com.bwie.text.MessageLoginActivity;
import com.bwie.text.QQloginActivity;
import com.bwie.text.R;
import com.example.city_picker.CityListActivity;

import static android.content.Context.MODE_PRIVATE;

/**
 * 作者： 张少丹
 * 时间：  2017/8/30.
 * 邮箱：1455456581@qq.com
 * 类的用途：
 */
public class LeftFragment extends Fragment {
    private View mRootView;
    private TextView login_style;
    private LinearLayout night;
    private TextView tv_xitongshezhi;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(mRootView == null){
            mRootView = View.inflate(getContext(), R.layout.fragment1_item,null);
        }
        login_style = mRootView.findViewById(R.id.login_style);
        ImageView qq = mRootView.findViewById(R.id.iv_qq);
        night = mRootView.findViewById(R.id.ll_tv_night);
        tv_xitongshezhi = mRootView.findViewById(R.id.tv_xitongshezhi);
        tv_xitongshezhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CityListActivity.startCityActivityForResult(getActivity());
            }
        });
        night.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int uiMode;
                uiMode = getResources().getConfiguration().uiMode& Configuration.UI_MODE_NIGHT_MASK;
                if(uiMode == Configuration.UI_MODE_NIGHT_YES){
                    TextView tv_night = night.findViewById(R.id.tv_night);
                    tv_night.setText("白天");
                    ( (MainActivity)getActivity()).getDelegate().setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    getActivity().getSharedPreferences("theme",MODE_PRIVATE).edit().putBoolean("night_theme",false).commit();

                }else if(uiMode == Configuration.UI_MODE_NIGHT_NO){
                    TextView tv_night = night.findViewById(R.id.tv_night);
                    tv_night.setText("夜间");
                    ( (MainActivity)getActivity()).getDelegate().setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    getActivity().getSharedPreferences("theme",MODE_PRIVATE).edit().putBoolean("night_theme", true).commit();
                }

                getActivity().recreate();


            }
        });
        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),QQloginActivity.class);
                startActivity(intent);
            }
        });
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
