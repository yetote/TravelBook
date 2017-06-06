package com.example.tf.travelbook;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import utils.HttpUtils;

import static com.baidu.location.d.j.v;

public class LoginActivity extends Activity implements View.OnClickListener {
    private EditText username, pwd;
    private Button enroll;
    private CardView cardView;
    private FloatingActionButton fab;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                if (msg.obj.equals("true")) {
                    MyAppliation.TEL = username.getText().toString();
                    MyAppliation.IS_LOGIN = true;
                    onBackPressed();
                } else {
                    Toast.makeText(LoginActivity.this, "用户名或密码出错，请检查", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        username = (EditText) findViewById(R.id.login_username);
        pwd = (EditText) findViewById(R.id.login_password);
        fab = (FloatingActionButton) findViewById(R.id.login_fab);
        enroll = (Button) findViewById(R.id.login_sure);
        fab.setOnClickListener(this);
        enroll.setOnClickListener(this);
        cardView= (CardView) findViewById(R.id.login_cardView);

    }

    private boolean NULL() {
        return username.getText().toString().isEmpty()
                || pwd.getText().toString().isEmpty();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_sure:
                //登陆
                if (NULL()) {
                    Toast.makeText(LoginActivity.this, "登录信息未填写完整，请检查", Toast.LENGTH_SHORT).show();
                } else {
                    String url = "http://123.206.60.236/travelbook/login.php?name=" + username.getText().toString() + "&pwd=" + pwd.getText().toString();
                    HttpUtils.HttpUtilsConnection(url, new HttpUtils.HttpCallBackListener() {
                        @Override
                        public void onFinish(String request) {
                            Message msg = new Message();
                            msg.obj = request;
                            msg.what = 1;
                            handler.sendMessage(msg);
                        }

                        @Override
                        public void onError(Exception e) {
                            Toast.makeText(LoginActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            case R.id.login_fab:
                //注册
                Intent i = new Intent();
                i.setClass(LoginActivity.this, EnrollActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    cardView.setVisibility(View.GONE);
                    startActivity(i, ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, fab, fab.getTransitionName()).toBundle());
                } else {
                    startActivity(i);
                }
                break;
        }
    }
}
