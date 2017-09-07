package com.bwie.text.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.bwie.text.R;
import com.bwie.text.XiangqingActivity;
import com.bwie.text.adapter.MyApadter;
import com.bwie.text.bean.News;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import view.xlistview.XListView;

/**
 * 作者： 张少丹
 * 时间：  2017/9/7.
 * 邮箱：1455456581@qq.com
 * 类的用途：
 */

public class ShehuiFragment extends Fragment implements XListView.IXListViewListener{
    private View mRootView;
    private String url = "http://v.juhe.cn/toutiao/index?type=shehui";
    private XListView lv;
    private List<News.ResultBean.DataBean> data;
    private MyApadter adapter;
    private News news;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(mRootView == null){
            mRootView = View.inflate(getContext(), R.layout.fragmentmy_item,null);
        }

        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lv = mRootView.findViewById(R.id.lv);
        lv.setXListViewListener(this);
        lv.setPullLoadEnable(true);
        lv.setPullRefreshEnable(true);
        initPost();
    }

    private void initPost() {
        /**
         * post请求的具体操作
         */
        //实例化RequestParams对象
        RequestParams params = new RequestParams(url);
        params.addParameter("key","c1885686ef47f19bcb45e39c4447e040");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                System.out.println("result = " + result);
                //使用gson解析
                Gson gson = new Gson();
                news = gson.fromJson(result, News.class);
                News.ResultBean result1 = news.getResult();
                //获取集合
                data = result1.getData();
                //实例化适配器
                if(adapter == null){
                    adapter = new MyApadter(getContext(), data);
                }else{
                    adapter.notifyDataSetChanged();
                }
                //设置适配器
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(getContext(), XiangqingActivity.class);
                        intent.putExtra("url", data.get(i-1).getUrl());
                        intent.putExtra("name", data.get(i-1).getTitle());
                        startActivity(intent);
                    }
                });
                lv.stopLoadMore();
                lv.stopRefresh();
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

    @Override
    public void onRefresh() {
        if(adapter != null){
            adapter = null;
        }
        initPost();
    }
    @Override
    public void onLoadMore() {
        data.addAll(data);
    }

}
