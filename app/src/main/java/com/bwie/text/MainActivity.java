package com.bwie.text;

import android.content.ComponentCallbacks;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bwie.text.adapter.MyApadter;
import com.bwie.text.bean.News;
import com.bwie.text.fragment.LeftFragment;
import com.bwie.text.fragment.RightFragment;
import com.google.gson.Gson;
import com.kson.slidingmenu.SlidingMenu;
import com.kson.slidingmenu.app.SlidingFragmentActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import view.xlistview.XListView;

/**
 * 实现新闻的页面
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends SlidingFragmentActivity {

    @ViewInject(R.id.lv)
    XListView lv;
    private String url = "http://v.juhe.cn/toutiao/index";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //使用每个控件的声明和初始化必须用IOC反转控制，通过注解的形式初始化View
        x.view().inject(this);
        //使用post请求
        initPost();
        initMenu();
    }

    private void initMenu() {
        setBehindContentView(R.layout.left_fragment_content);
        getSupportFragmentManager().beginTransaction().replace(R.id.left_fl1, new LeftFragment()).commit();

        SlidingMenu menu = getSlidingMenu();
        menu.setMode(SlidingMenu.LEFT_RIGHT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setBehindOffsetRes(R.dimen.BehindOffsetRes);

        menu.setSecondaryMenu(R.layout.right_fragment_content);
        getSupportFragmentManager().beginTransaction().replace(R.id.left_fl2,new RightFragment()).commit();
    }

    /**
     * post请求的具体操作
     */
    private void initPost() {
        //实例化RequestParams对象
        RequestParams params = new RequestParams(url);
        params.addParameter("key","c1885686ef47f19bcb45e39c4447e040");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                System.out.println("result = " + result);
                //使用gson解析
                Gson gson = new Gson();
                News news = gson.fromJson(result, News.class);
                News.ResultBean result1 = news.getResult();
                //获取集合
                List<News.ResultBean.DataBean> data = result1.getData();
                //实例化适配器
                MyApadter adapter = new MyApadter(MainActivity.this,data);
                //设置适配器
                lv.setAdapter(adapter);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
            }
        });
    }
}
