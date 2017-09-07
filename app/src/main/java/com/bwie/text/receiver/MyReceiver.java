package com.bwie.text.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 作者： 张少丹
 * 时间：  2017/9/6.
 * 邮箱：1455456581@qq.com
 * 类的用途：
 */

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equals("kson")){

        }
    }
}
