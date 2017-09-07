package com.bwie.text.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.text.R;
import com.bwie.text.bean.News;
import com.bwie.text.fragment.RightFragment;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 作者： 张少丹
 * 时间：  2017/8/30.
 * 邮箱：1455456581@qq.com
 * 类的用途：adapter类
 */

public class MyApadter extends BaseAdapter{
    private Context context;
    private List<News.ResultBean.DataBean> data;
    private final int atype = 0;
    private final int btype = 1;
    private SharedPreferences con;

    public MyApadter(Context context, List<News.ResultBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * 总共两种
     * @return
     */
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    /**
     * 两种类型
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if(position%2==0){
            return atype;
        }else
        return btype;
    }

    /**
     * 视图的操作
     * @param i
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        int type = getItemViewType(i);
        //初始化viewHolder
        ViewHolder1 holder1 = null;
        ViewHolder2 holder2= null;
        if(view == null){
            switch (type){
                case atype:
                    //获取视图和控件
                    holder1 = new ViewHolder1();
                    view = View.inflate(context, R.layout.lv_item1, null);
                    holder1.iv1 = view.findViewById(R.id.iv1);
                    holder1.tv_name1 = view.findViewById(R.id.tv_name1);
                    holder1.tv_time1 = view.findViewById(R.id.tv_time1);
                    holder1.tv_title1 = view.findViewById(R.id.tv_title1);
                    view.setTag(holder1);
                    break;
                case btype:
                    //获取视图和控件
                    holder2 = new ViewHolder2();
                    view = View.inflate(context, R.layout.lv_item2, null);
                    holder2.iv2 = view.findViewById(R.id.iv2);
                    holder2.tv_name2 = view.findViewById(R.id.tv_name2);
                    holder2.tv_time2 = view.findViewById(R.id.tv_time2);
                    holder2.tv_title2 = view.findViewById(R.id.tv_title2);
                    view.setTag(holder2);
                    break;
            }
        }else{
            switch (type){
                case atype:
                    //绑定
                    holder1 = (ViewHolder1) view.getTag();
                    break;

                case btype:
                    //绑定
                    holder2 = (ViewHolder2) view.getTag();
                    break;
            }
        }
        switch (type){
            case atype:
                //赋值
                holder1.tv_title1.setText(data.get(i).getTitle());
                holder1.tv_name1.setText(data.get(i).getAuthor_name());
                holder1.tv_time1.setText(data.get(i).getDate());

                con = context.getSharedPreferences("con", Context.MODE_PRIVATE);
                boolean b = con.getBoolean("b", true);
                if(b){
                    ImageLoader.getInstance().displayImage(data.get(i).getThumbnail_pic_s(),holder1.iv1);
                }
                break;

            case btype:
                //赋值
                holder2.tv_title2.setText(data.get(i).getTitle());
                holder2.tv_name2.setText(data.get(i).getAuthor_name());
                holder2.tv_time2.setText(data.get(i).getDate());

                boolean b1 = con.getBoolean("b", true);
                if(b1){

                    ImageLoader.getInstance().displayImage(data.get(i).getThumbnail_pic_s(),holder2.iv2);
                }
                break;
        }
        return view;
    }
    //优化ViewHolder1
    public class ViewHolder1{
        public ImageView iv1;
        public TextView tv_title1,tv_time1,tv_name1;
    }
    //优化viewHolder2
    public class ViewHolder2{
        public ImageView iv2;
        public TextView tv_title2,tv_time2,tv_name2;
    }
}
