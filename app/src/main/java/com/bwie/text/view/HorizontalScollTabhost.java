package com.bwie.text.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwie.text.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： 张少丹
 * 时间：  2017/8/30.
 * 邮箱：1455456581@qq.com
 * 类的用途：
 */

public class HorizontalScollTabhost extends LinearLayout implements ViewPager.OnPageChangeListener {
    private Context mContext;
    private TypedArray typedArray;
    private HorizontalScrollView hscrollview;
    private LinearLayout layout_menu;
    private ViewPager viewpager;
    private List<String> list;
    private List<Fragment> fragmentList;
    private List<TextView> topViews;
    private int color;

    public HorizontalScollTabhost(Context context) {
        this(context,null);
    }

    public HorizontalScollTabhost(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HorizontalScollTabhost(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(context,attrs);
    }

    /**
     * 设置控件的属性
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.HorizontalScollTabhost);
        color = typedArray.getColor(R.styleable.HorizontalScollTabhost_top_background, 0xffffff);
        typedArray.recycle();
        initView();
    }

    /**
     * 自定义控件
     */
    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.horizontal_scroll_tabhost, this, true);
        hscrollview = view.findViewById(R.id.horizontalScrollView);
        layout_menu = view.findViewById(R.id.layout_menu);
        viewpager = view.findViewById(R.id.viewpager_fragment);
        viewpager.addOnPageChangeListener(this);
    }

    public void diaplay(List<String> list, List<Fragment> fragments) {
        this.list = list;
        this.fragmentList = fragments;
        topViews = new ArrayList<>();
        drawUi();
    }

    private void drawUi() {
        drawHorizontal();
        drawViewpager();
    }

    private void drawViewpager() {
        MyPager adapter = new MyPager(((FragmentActivity)mContext).getSupportFragmentManager());
        viewpager.setAdapter(adapter);
    }

    private void drawHorizontal() {
        layout_menu.setBackgroundColor(color);
        for (int i = 0; i < fragmentList.size(); i++) {
            String s = list.get(i);
            TextView tv = (TextView) View.inflate(mContext,R.layout.news_top_tv_item,null);
            tv.setText(s);
            final int finalI = i;
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewpager.setCurrentItem(finalI);
                }
            });
            layout_menu.addView(tv);
            topViews.add(tv);
        }
        topViews.get(0).setSelected(true);
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        if (layout_menu != null && layout_menu.getChildCount() > 0) {

            for (int i = 0; i < layout_menu.getChildCount(); i++) {
                if (i == position) {
                    layout_menu.getChildAt(i).setSelected(true);
                } else {
                    layout_menu.getChildAt(i).setSelected(false);
                }
            }
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class MyPager extends FragmentPagerAdapter{
        public MyPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
