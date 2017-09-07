package com.bwie.text.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bwie.text.db.DbHelper;
import com.bwie.text.db.MySQLiteOpenHelper;

/**
 * 作者： 张少丹
 * 时间：  2017/9/7.
 * 邮箱：1455456581@qq.com
 * 类的用途：
 */

public class NewsDao {
    private Context context;
    private String json;
    private final MySQLiteOpenHelper helper;

    public NewsDao(Context context) {
        this.context = context;
        helper = new MySQLiteOpenHelper(context);
    }
    public void clear(){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("json",null,null);
        db.close();
    }
    public void insert(String s){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("json",s);
        db.insert("json",null,values);
        db.close();
    }
    public String query(){
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select json from json", null);
        while (cursor.moveToNext()){
            json = cursor.getString(cursor.getColumnIndex("json"));
        }
        return json;
    }
}
