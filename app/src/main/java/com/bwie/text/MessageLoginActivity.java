package com.bwie.text;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.text.bean.User;
import com.bwie.text.utils.SharedPreferencesUtil;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class MessageLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText yanzhengma;
    private EditText phoneNumber;
    private TextView tv_sendMessage;
    private Button btn_jinrutoutiao;
    private int TIME = 5;
    private final int SECOND = 1000;
    private EventHandler eventHandler;


    Handler timeHandler = new Handler();

    Runnable timeRunable = new Runnable() {
        @Override
        public void run() {
            TIME--;
            if (TIME == 0) {
                timeHandler.removeCallbacks(this);
                TIME = 5;
                tv_sendMessage.setEnabled(true);
                tv_sendMessage.setText("再次获取");
            } else {
                tv_sendMessage.setEnabled(false);
                tv_sendMessage.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tv_sendMessage.setText(TIME + "s");
                timeHandler.postDelayed(this, SECOND);
            }
        }
    };
    private ImageView qq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_login);//这个是页面的布局
        TextView tv_x = (TextView) findViewById(R.id.tv_x);
        yanzhengma = (EditText) findViewById(R.id.et_yanzhengma);
        phoneNumber = (EditText) findViewById(R.id.editText);
        tv_sendMessage = (TextView) findViewById(R.id.tv_sendMessage);
        btn_jinrutoutiao = (Button) findViewById(R.id.btn_jinrutoutiao);
        qq = (ImageView) findViewById(R.id.imageView_qq);
        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MessageLoginActivity.this,QQloginActivity.class);
                startActivity(intent);
            }
        });
        tv_sendMessage.setOnClickListener(this);
        btn_jinrutoutiao.setOnClickListener(this);

        registerSMS();
        tv_x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void registerSMS() {
            // 创建EventHandler对象
            eventHandler = new EventHandler() {
                public void afterEvent(int event, int result, Object data) {
                    if (data instanceof Throwable) {
                        Throwable throwable = (Throwable) data;
                        final String msg = throwable.getMessage();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MessageLoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        if (result == SMSSDK.RESULT_COMPLETE) {//只有回服务器验证成功，才能允许用户登录
                            //回调完成,提交验证码成功
                            if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MessageLoginActivity.this, "服务器验证成功", Toast.LENGTH_SHORT).show();
                                        User user = new User();
                                        user.uid = phoneNumber.getText().toString();
                                        user.phone = phoneNumber.getText().toString();

                                        SharedPreferencesUtil.putPreferences("uid", user.uid);
                                        SharedPreferencesUtil.putPreferences("phone", user.phone);
                                    }
                                });
                            }
                        } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                            //获取验证码成功
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MessageLoginActivity.this,"验证码已发送",Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                            //返回支持发送验证码的国家列表
                        }
                    }
                }
            };

            // 注册监听器
            SMSSDK.registerEventHandler(eventHandler);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_jinrutoutiao:
                verify();
                SMSSDK.submitVerificationCode("86", phoneNumber.getText().toString(), yanzhengma.getText().toString());
                break;
            case R.id.tv_sendMessage:
                if (TextUtils.isEmpty(phoneNumber.getText().toString())) {
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                timeHandler.postDelayed(timeRunable, SECOND);
                SMSSDK.getVerificationCode("86", phoneNumber.getText().toString());
                break;
        }
    }
    private void verify() {
        if (TextUtils.isEmpty(phoneNumber.getText().toString())) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(yanzhengma.getText().toString())) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
