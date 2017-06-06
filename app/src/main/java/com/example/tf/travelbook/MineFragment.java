package com.example.tf.travelbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import model.UserModel;
import model.UserUpdate;
import utils.HttpUtils;


public class MineFragment extends Fragment implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private NavigationView nv;
    private TextView name, content;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    List<UserModel> list = JSON.parseArray(msg.obj.toString(), UserModel.class);

                    for (UserModel userModel : list) {
                        name.setText(userModel.getNickname());
                        content.setText(userModel.getContent());
                    }
                    break;
                case 2:
                    UserUpdate up = (UserUpdate) msg.obj;
                    if (up.getCode().equals("success")) {
                        Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                        name.setText(up.getName());
                    } else {
                        Toast.makeText(getActivity(), "系统错误，请联系管理员", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.mine_fragment, null);
        nv = (NavigationView) v.findViewById(R.id.mine_nv);
        View headerView = nv.inflateHeaderView(R.layout.header_layout);
        name = (TextView) headerView.findViewById(R.id.mine_name);
        content = (TextView) headerView.findViewById(R.id.mine_content);
        name.setOnClickListener(this);
        content.setOnClickListener(this);
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
        nv.setNavigationItemSelectedListener(this);
        return v;

    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent();
        switch (v.getId()) {
            case R.id.mine_name:
                AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                final EditText et = new EditText(getActivity());
                et.setText(name.getText().toString());
                ad.setView(et);
                ad.setTitle("修改昵称")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String url = "http://123.206.60.236/travelbook/user_update.php?nickname=" + et.getText().toString() + "&tel="+MyAppliation.TEL;
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
            case R.id.mine_content:
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent i = new Intent();
        if (MyAppliation.IS_LOGIN == true) {
            switch (item.getItemId()) {
                case R.id.personal_center:
                    i.setClass(getActivity(), PersionCenter.class);
                    startActivity(i);
                    break;
                case R.id.setting:
                    i.setClass(getActivity(), SettingActivity.class);
                    startActivityForResult(i, 1);
                    break;
                case R.id.about:
                    i.setClass(getActivity(), AboutActivity.class);
                    startActivity(i);
                    break;
            }
        } else {
            Toast.makeText(getActivity(), "您未登录，请先登录", Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    name.setText("未登录");
                    content.setText("");
                }
                break;
        }
    }
}
