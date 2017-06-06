package com.example.tf.travelbook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.util.List;

import model.UserModel;
import model.UserUpdate;
import utils.HttpUtils;

public class PersionCenter extends AppCompatActivity implements View.OnClickListener {
    private TextView nickname, pwd, tel, content;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    List<UserModel> list = JSON.parseArray(msg.obj.toString(), UserModel.class);
                    for (UserModel usermodel : list) {
//                    tel.setText(usermodel.getTel());
                        nickname.setText(usermodel.getNickname());
                        content.setText(usermodel.getContent());
                    }
                    break;
                case 2:
                    UserUpdate up = (UserUpdate) msg.obj;
                    if (up.getCode().equals("success")) {
                        Toast.makeText(PersionCenter.this, "修改成功", Toast.LENGTH_SHORT).show();
                        nickname.setText(up.getName());
                    } else {
                        Toast.makeText(PersionCenter.this, "系统错误，请联系管理员", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 3:
                    UserUpdate up1 = (UserUpdate) msg.obj;
                    if (up1.getCode().equals("success")) {
                        Toast.makeText(PersionCenter.this, "修改成功", Toast.LENGTH_SHORT).show();
                        content.setText(up1.getName());
                    } else {
                        Toast.makeText(PersionCenter.this, "系统错误，请联系管理员", Toast.LENGTH_SHORT).show();
                    }
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persion_center);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        initViews();
        tel.setText(MyAppliation.TEL);
        String url = "http://123.206.60.236/travelbook/user_sc.php?tel=" + MyAppliation.TEL;
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
        nickname.setOnClickListener(this);
        pwd.setOnClickListener(this);
        content.setOnClickListener(this);
    }

    private void initViews() {
        nickname = (TextView) findViewById(R.id.persion_nickname_tv);
        pwd = (TextView) findViewById(R.id.persion_changepwd);
        tel = (TextView) findViewById(R.id.persion_tel_tv);
        content = (TextView) findViewById(R.id.persion_content_tv);
    }

    @Override
    public void onClick(View v) {
        if (MyAppliation.IS_LOGIN == true) {
            switch (v.getId()) {
                case R.id.persion_nickname_tv:
                    AlertDialog.Builder ad = new AlertDialog.Builder(PersionCenter.this);
                    final EditText et = new EditText(PersionCenter.this);
                    et.setText(nickname.getText().toString());
                    ad.setView(et);
                    ad.setTitle("修改昵称")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String url = "http://123.206.60.236/travelbook/user_update.php?nickname=" + et.getText().toString() + "&tel=" + MyAppliation.TEL;
                                    HttpUtils.HttpUtilsConnection(url, new HttpUtils.HttpCallBackListener() {
                                        @Override
                                        public void onFinish(String request) {
                                            Message msg = new Message();
                                            msg.what = 2;
                                            UserUpdate up = new UserUpdate(et.getText().toString(), request);
                                            msg.obj = up;
                                            handler.sendMessage(msg);
                                        }

                                        @Override
                                        public void onError(Exception e) {
                                        }
                                    });
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();
                    break;
                case R.id.persion_changepwd:
                    Intent i = new Intent();
                    i.setClass(this, ChangePwdActivity.class);
                    startActivity(i);
                    break;
                case R.id.persion_content_tv:
                    AlertDialog.Builder contentad = new AlertDialog.Builder(PersionCenter.this);
                    final EditText contentet = new EditText(PersionCenter.this);
                    contentet.setText(content.getText().toString());
                    contentad.setView(contentet);
                    contentad.setTitle("修改简介")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String url = "http://123.206.60.236/travelbook/content_update.php?content=" + contentet.getText().toString() + "&tel=" + MyAppliation.TEL;
                                    HttpUtils.HttpUtilsConnection(url, new HttpUtils.HttpCallBackListener() {
                                        @Override
                                        public void onFinish(String request) {
                                            Message msg = new Message();
                                            msg.what = 3;
                                            UserUpdate up = new UserUpdate(contentet.getText().toString(), request);
                                            msg.obj = up;
                                            handler.sendMessage(msg);
                                        }

                                        @Override
                                        public void onError(Exception e) {
                                        }
                                    });
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();
                    break;
            }
        } else {
            Toast.makeText(this, "您未登录，请先登录", Toast.LENGTH_SHORT).show();
        }
    }
}
