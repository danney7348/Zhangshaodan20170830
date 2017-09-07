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
import com.bwie.text.dao.NewsDao;
import com.bwie.text.fragment.CaijingFragment;
import com.bwie.text.fragment.GuojiFragment;
import com.bwie.text.fragment.GuoneiFragment;
import com.bwie.text.fragment.JunshiFragment;
import com.bwie.text.fragment.KejiFragment;
import com.bwie.text.fragment.LeftFragment;
import com.bwie.text.fragment.MyFragment;
import com.bwie.text.fragment.RightFragment;
import com.bwie.text.fragment.ShehuiFragment;
import com.bwie.text.fragment.ShishangFragment;
import com.bwie.text.fragment.TiyuFragment;
import com.bwie.text.fragment.YuleFragment;
import com.bwie.text.view.HorizontalScollTabhost;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.kson.slidingmenu.SlidingMenu;
import com.kson.slidingmenu.app.SlidingFragmentActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
    private NewsDao dao;
    private ChannelBean b;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sendBroadcast(new Intent("kson"));
        //使用每个控件的声明和初始化必须用IOC反转控制，通过注解的形式初始化View
        x.view().inject(this);
        mTabhost = (HorizontalScollTabhost) findViewById(R.id.tabhost);
        //使用post请求
        initView();
        dao = new NewsDao(this);
        initMenu();
        initData();
        jiahao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list = new ArrayList<ChannelBean>();
                String query = dao.query();
                if(query == null){
                    ChannelBean channelBean;
                    for (int i = 0; i < beans.size(); i++) {
                            channelBean = new ChannelBean(beans.get(i),true);
                        list.add(channelBean);
                    }
                }else {
                    try {
                       JSONArray arr=new JSONArray(query);
                        for (int i = 0; i <arr.length() ; i++) {
                            JSONObject oo = (JSONObject) arr.get(i);
                            String name = oo.getString("name");
                            boolean isSelect = oo.getBoolean("isSelect");
                            ChannelBean b=new ChannelBean(name,isSelect);
                            list.add(b);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
        beans.add("国际");
        beans.add("国内");
        fragmentList.add(new MyFragment());
        fragmentList.add(new YuleFragment());
        fragmentList.add(new ShehuiFragment());
        fragmentList.add(new TiyuFragment());
        fragmentList.add(new KejiFragment());
        fragmentList.add(new CaijingFragment());
        fragmentList.add(new ShishangFragment());
        fragmentList.add(new JunshiFragment());
        fragmentList.add(new GuojiFragment());
        fragmentList.add(new GuoneiFragment());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 101){
            String s = data.getExtras().getString("json");
            System.out.println("s = " + s);
            String query = dao.query();
            if(query != null){
                dao.clear();
            }
            beans.clear();
            list.clear();
            dao.insert(s);
            List<Fragment> fragmentLists = new ArrayList<>();
            try {
                JSONArray arr = new JSONArray(s);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject oo = (JSONObject) arr.get(i);
                    String name = oo.getString("name");
                    boolean isSelect = oo.getBoolean("isSelect");
                    if(isSelect){
                        beans.add(name);
                        fragmentLists.add(fragmentList.get(i));
                        System.out.println("beans = " + beans);
                    }
                    System.out.println("beans =========== " + beans.toString());
                }
                mTabhost.remove();
                mTabhost.diaplay(beans,fragmentLists);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
