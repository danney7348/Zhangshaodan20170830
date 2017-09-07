package com.bwie.text.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.bwie.text.LixianActivity;
import com.bwie.text.MainActivity;
import com.bwie.text.R;
import com.bwie.text.utils.NetWorkInfoUtils;

import cn.jpush.android.api.JPushInterface;

/**
 * 作者： 张少丹
 * 时间：  2017/8/30.
 * 邮箱：1455456581@qq.com
 * 类的用途：
 */

public class RightFragment extends Fragment {
    private View mRootView;
    private RelativeLayout lixianxiazai;
    private RelativeLayout notwifi;
    public static SharedPreferences sp;
    private Switch tuisong;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(mRootView == null){
            mRootView = View.inflate(getContext(), R.layout.fragment2_item,null);
        }
        ImageView back = mRootView.findViewById(R.id.iv_right_fragment_back);
        lixianxiazai = mRootView.findViewById(R.id.rl_lixianxiazai);
        tuisong = mRootView.findViewById(R.id.switch_tuisong);

        tuisong.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    JPushInterface.resumePush(getContext());
                }else{
                    JPushInterface.stopPush(getContext());
                }
            }
        });
        notwifi = mRootView.findViewById(R.id.rl_notWifi);
        notwifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(getContext()).setTitle("非WIFI网络流量").setSingleChoiceItems(new String[]{"最佳效果（下载大图）", "极省流量（不下载图）"}, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        sp = getActivity().getSharedPreferences("con", Context.MODE_PRIVATE);
                        if (i == 0) {

                            //需要存储本地状态，选择的什么网络流量节省方式，"wifi"="hasnet"，加载大图
                            boolean b = sp.edit().putBoolean("b", true).commit();

                        } else if (i == 1) {
                            //需要存储本地状态，选择的什么网络流量节省方式，"wifi"="nonet"，不加载图
                            boolean b = sp.edit().putBoolean("b", false).commit();
                        }

                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });
        loadNewsData();
        lixianxiazai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getContext(), LixianActivity.class);
                startActivity(in);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return mRootView;
    }

    private void loadNewsData() {
        new NetWorkInfoUtils().verify(getContext(), new NetWorkInfoUtils.NetWork() {
            @Override
            public void netWifiVisible() {//有wifi时加载网络接口

                if ("wifi".equals("hasnet")) {

                }
            }

            @Override
            public void netUnVisible() {//没有网络的操作，加载本地缓存

                if ("wifi".equals("nonet")) {

                }

            }

            @Override
            public void netMobileVisible() {//有手机网络的时候的判断
                if ("wifi".equals("hasnet")) {//如果设置了加载大图，就走加载大图逻辑

                    //加载大图

                } else if ("wifi".equals("nonet")) {//如果设置了不加载图，就不加载图片

                    //占位图，不加载大图

                }
            }
        });
    }
}
