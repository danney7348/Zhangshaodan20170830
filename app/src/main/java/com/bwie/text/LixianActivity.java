package com.bwie.text;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.bwie.text.adapter.MyLixianAdapter;
import com.bwie.text.bean.Api;
import com.bwie.text.bean.Catogray;
import com.bwie.text.db.DbHelper;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;
import java.util.ArrayList;
import java.util.List;

public class LixianActivity extends AppCompatActivity {
    private RecyclerView lv;
    private List<Catogray> list;
    private Button download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lixian);
        initView();
        initData();
        initDown();
    }

    private void initDown() {

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list != null && list.size() > 0) {
                    for (Catogray catogray : list) {
                        if (catogray.state == true) {//判断是否是选中状态，如果选中则执行下载操作
                            loadData(catogray.type);
                        }
                    }
                }
                for (Catogray catogray : list) {
                    System.out.println("state====" + catogray.state);
                }
            }
        });
    }

    private void initView() {
        lv = (RecyclerView) findViewById(R.id.lv);
        download = (Button) findViewById(R.id.download);
    }

    private void loadData(final String type) {
        RequestParams params = new RequestParams(Api.NEWS);
        params.addParameter("key", Api.APPKEY);
        params.addParameter("type", type);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                saveData(type, result);
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
    private void saveData(String type, String result) {

        DbHelper dbhelper = new DbHelper(this);
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("type", type);
        values.put("content", result);
        db.insert("news", null, values);
        /*Cursor news = db.query("news", null, null, null, null, null, null);
        while(news.moveToNext()){
            Toast.makeText(LixianActivity.this,"数据库有东西",Toast.LENGTH_LONG).show();
        }*/
    }

    private void initData() {
        list = new ArrayList<>();
        Catogray c = new Catogray();
        c.type = "top";
        c.name = "头条";
        list.add(c);
        c = new Catogray();
        c.type = "top";
        c.name = "娱乐";
        list.add(c);
        c = new Catogray();
        c.type = "top";
        c.name = "社会";
        list.add(c);
        c = new Catogray();
        c.type = "top";
        c.name = "体育";
        list.add(c);
        c = new Catogray();
        c.type = "top";
        c.name = "科技";
        list.add(c);

        MyLixianAdapter adapter = new MyLixianAdapter(this, list);
        lv.setLayoutManager(new LinearLayoutManager(this));
        lv.setAdapter(adapter);

        adapter.setOnItemClickListener(new MyLixianAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int pos, View view) {
                CheckBox checkbox = view.findViewById(R.id.checkbox);
                Catogray c = list.get(pos);
                if (checkbox.isChecked()) {
                    checkbox.setChecked(false);
                    c.state = false;
                } else {
                    checkbox.setChecked(true);
                    c.state = true;
                }
                list.set(pos, c);
            }
        });
    }}