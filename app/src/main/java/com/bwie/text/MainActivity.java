package com.bwie.text;

import android.content.ComponentCallbacks;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.andy.library.ChannelActivity;
import com.andy.library.ChannelBean;
import com.bwie.text.adapter.MyApadter;
import com.bwie.text.bean.CategoryBean;
import com.bwie.text.bean.News;
import com.bwie.text.fragment.LeftFragment;
import com.bwie.text.fragment.MyFragment;
import com.bwie.text.fragment.RightFragment;
import com.bwie.text.view.HorizontalScollTabhost;
import com.google.gson.Gson;
import com.kson.slidingmenu.SlidingMenu;
import com.kson.slidingmenu.app.SlidingFragmentActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import view.xlistview.XListView;

/**
 * 实现新闻的页面
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @ViewInject(R.id.lv)
    XListView lv;

    @ViewInject(R.id.tou_iv_shezhi)
    ImageView shezhi;
    @ViewInject(R.id.tou_iv_user)
    ImageView user;

    private HorizontalScollTabhost mTabhost;
    private List<Fragment> fragmentList;
    private List<String> beans;
    private SlidingMenu menu;
    private ImageView jiahao;
    private List<ChannelBean> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sendBroadcast(new Intent("kson"));
        //使用每个控件的声明和初始化必须用IOC反转控制，通过注解的形式初始化View
        x.view().inject(this);
        mTabhost = (HorizontalScollTabhost) findViewById(R.id.tabhost);
        //使用post请求
        initView();

        initMenu();
        initData();
        jiahao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list = new ArrayList<ChannelBean>();
                ChannelBean channelBean;
                for (int i = 0; i < beans.size(); i++) {
                    if(beans.get(i).equals("体育")||beans.get(i).equals("娱乐")){
                        channelBean = new ChannelBean(beans.get(i),true);
                    }else{
                        channelBean = new ChannelBean(beans.get(i),false);
                    }
                    list.add(channelBean);
                }
                ChannelActivity.startChannelActivity(MainActivity.this, list);
            }
        });
    }

    private void initView() {
        user.setOnClickListener(this);
        shezhi.setOnClickListener(this);
        jiahao = mTabhost.findViewById(R.id.iv_jiahao);
    }

    private void initData() {
        fragmentList = new ArrayList<>();
        beans = new ArrayList<>();
        beans.add("头条");
        beans.add("娱乐");
        beans.add("社会");
        beans.add("体育");
        beans.add("科技");
        beans.add("财经");
        beans.add("时尚");
        beans.add("军事");
        fragmentList.add(new MyFragment());
        fragmentList.add(new MyFragment());
        fragmentList.add(new MyFragment());
        fragmentList.add(new MyFragment());
        fragmentList.add(new MyFragment());
        fragmentList.add(new MyFragment());
        fragmentList.add(new MyFragment());
        fragmentList.add(new MyFragment());
        mTabhost.diaplay(beans, fragmentList);
    }

    private void initMenu() {
        //定义一个左侧空的布局
        menu = new SlidingMenu(this);
        menu.setMenu(R.layout.left_fragment_content);
        getSupportFragmentManager().beginTransaction().replace(R.id.left_fl1, new LeftFragment()).commit();
        menu.setMode(SlidingMenu.LEFT_RIGHT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setBehindOffsetRes(R.dimen.BehindOffsetRes);//设置侧滑菜单的宽度
        menu.setSecondaryMenu(R.layout.right_fragment_content);
        getSupportFragmentManager().beginTransaction().replace(R.id.left_fl2, new RightFragment()).commit();
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tou_iv_user:
                menu.showMenu();
                break;
            case R.id.tou_iv_shezhi:
                menu.showSecondaryMenu();
                break;
        }
    }
}
