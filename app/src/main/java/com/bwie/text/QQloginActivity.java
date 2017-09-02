package com.bwie.text;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import java.util.Map;

public class QQloginActivity extends AppCompatActivity {

    private TextView nameTv;
    private ImageView iconIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqlogin);
        nameTv = (TextView) findViewById(R.id.tv_qq_name);
        iconIv = (ImageView) findViewById(R.id.iv_qq_touxiao);
       Button login = (Button) findViewById(R.id.btn_qq_shouquanlogin);
    }
    public void qqClick(View view) {
        //qq方式
        UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.QQ, umAuthListener);
    }
    UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
        }
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();
            String name = data.get("name");
            String gender = data.get("gender");
            String photoUrl = data.get("iconurl");
            nameTv.setText(name + " " + gender);
            ImageLoader.getInstance().displayImage(photoUrl, iconIv);
        }
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            if (UMShareAPI.get(QQloginActivity.this).isInstall(QQloginActivity.this, SHARE_MEDIA.QQ)) {
                Toast.makeText(getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "no install QQ", Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
