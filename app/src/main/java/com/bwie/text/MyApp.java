package com.bwie.text;

import android.app.Application;
import android.content.Context;

import com.bwie.text.bean.Constans;
import com.mob.MobSDK;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

/**
 * 作者： 张少丹
 * 时间：  2017/8/30.
 * 邮箱：1455456581@qq.com
 * 类的用途：全局的图片加载
 */

public class MyApp extends Application {
    {
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }
    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        UMShareAPI.get(this);
        mContext = this;
        MobSDK.init(this, Constans.KEY, Constans.SECRET);
        //图片的操作
        initImage();
        //使用每个控件的声明和初始化必须用IOC反转控制
        initData();
    }
    /**
     * 操作图片
     */
    private void initImage() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new   ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(config);
    }

    /**
     * -	每个控件的声明和初始化必须用IOC反转控制
     */
    private void initData() {
        x.Ext.init(this);
        x.Ext.setDebug(false);
    }

}
