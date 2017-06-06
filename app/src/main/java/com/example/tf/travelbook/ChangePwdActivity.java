package com.example.tf.travelbook;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import utils.HttpUtils;

public class ChangePwdActivity extends AppCompatActivity {
    private EditText oldPwd, newPwd, surePwd;
    private Button sure;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (msg.obj.equals("true")) {
                        Toast.makeText(ChangePwdActivity.this, "修改成功，请重新登陆", Toast.LENGTH_SHORT).show();
                        MyAppliation.TEL = "";
                        MyAppliation.IS_LOGIN = false;
                        finish();
                    } else if (msg.obj.equals("false")) {
                        Toast.makeText(ChangePwdActivity.this, "原密码错误，请检查", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ChangePwdActivity.this, "未知错误，请检查", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        initViews();
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pwdEqual()) {
                    Toast.makeText(ChangePwdActivity.this, "原密码不能与新密码相等", Toast.LENGTH_SHORT).show();
                }else {
//                   Toast.makeText(ChangePwdActivity.this, "原密码不能与新密码相等", Toast.LENGTH_SHORT).show();
                    if (sureEqual()) {
                        String url = "http://123.206.60.236/travelbook/changepwd.php?name=" + MyAppliation.TEL + "&pwd=" + oldPwd.getText().toString() + "&newpwd=" + newPwd.getText().toString();
                        HttpUtils.HttpUtilsConnection(url, new HttpUtils.HttpCallBackListener() {
                            @Override
                            public void onFinish(String request) {
                                Message msg = new Message();
                                msg.what = 1;
                                msg.obj = request;
                                handler.sendMessage(msg);
                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });
                    } else {
                        Toast.makeText(ChangePwdActivity.this, "两次密码不一致，请检查", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean sureEqual() {
        return newPwd.getText().toString().equals(surePwd.getText().toString());
    }

    private boolean pwdEqual() {
        return oldPwd.getText().toString().equals(newPwd.getText().toString());
    }


    private void initViews() {
        oldPwd = (EditText) findViewById(R.id.old_pwd_et);
        newPwd = (EditText) findViewById(R.id.new_pwd_et);
        surePwd = (EditText) findViewById(R.id.sure_pwd_et);
        sure = (Button) findViewById(R.id.change_pwd_sure);
    }
}
