package com.example.tf.travelbook;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import adapter.DetaillsAdapter;
import model.DetaillsModel;
import utils.HttpUtils;

public class SeesDetaillsActivity extends AppCompatActivity {
    private ImageView headimg, image;
    private TextView nickname, content, time;
    private RecyclerView rv;
    private Button send;
    private EditText et;
    private String getNickname;
    private String getTime;
    private String getImage;
    private String getContent;
    private int getId;
    private ArrayList<DetaillsModel> list;
    private String getHeadimg;
    private DetaillsAdapter adapter;
    private static final String TAG = "SeesDetaillsActivity";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (!msg.obj.equals("null")) {
                        List<DetaillsModel> modellist = JSON.parseArray(msg.obj.toString(), DetaillsModel.class);
                        for (DetaillsModel model : modellist) {
                            list.add(new DetaillsModel(model.getId(), model.getNickname(), model.getDate(), model.getContent(), model.getImg()));
                        }
                    }
                    adapter.notifyDataSetChanged();
                    break;
                case 2:
                    if (msg.obj.equals("success")) {
                        Toast.makeText(SeesDetaillsActivity.this, "刷新成功，请下滑查看", Toast.LENGTH_SHORT).show();
                        et.clearFocus();
                        list.clear();
                        getReply();
                    } else {
                        Toast.makeText(SeesDetaillsActivity.this, "未知错误，请联系管理员", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sees_detaills);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        inirtViews();
        list = new ArrayList<>();
        Intent i = getIntent();

        getNickname = i.getStringExtra("user");
        getTime = i.getStringExtra("time");
        getImage = i.getStringExtra("image");
        getContent = i.getStringExtra("content");
        getHeadimg = i.getStringExtra("headimg");
        getId = i.getIntExtra("id", 0);

        if (getImage.equals("null")) {
        } else {
            Glide.with(this).load(getImage).into(image);
        }
        if (getHeadimg.equals("null")) {
            Glide.with(this).load(R.drawable.header).into(headimg);
        } else {
            Glide.with(this).load(getHeadimg).into(headimg);
        }

        nickname.setText(getNickname);
        time.setText(getTime);
        content.setText(getContent);
        getReply();


        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DetaillsAdapter(this, list);
        rv.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et.getText().toString().isEmpty()) {
                    Toast.makeText(SeesDetaillsActivity.this, "请填写回复内容", Toast.LENGTH_SHORT).show();
                } else {
                    Calendar c = Calendar.getInstance();
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH) + 1;
                    int day = c.get(Calendar.DAY_OF_MONTH);
                    int hour = c.get(Calendar.HOUR_OF_DAY);
                    int min = c.get(Calendar.MINUTE);
                    int sec = c.get(Calendar.SECOND);
                    String date = String.format("%d-%d-%d %d:%d:%d", year, month, day, hour, min, sec);

                    sendReply(date);
//                    Log.e(TAG, "onClick: " + date);

                }
            }
        });

    }

    private void sendReply(String date) {
        String postUrl = "http://123.206.60.236/travelbook/reply_in.php?tel=" + MyAppliation.TEL + "&date=" + date + "&theme=" + getId + "&content=" + et.getText().toString();
        HttpUtils.HttpUtilsConnection(postUrl, new HttpUtils.HttpCallBackListener() {
            @Override
            public void onFinish(String request) {
                Message msg = new Message();
                msg.what = 2;
                msg.obj = request;
                handler.sendMessage(msg);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    private void inirtViews() {
        headimg = (ImageView) findViewById(R.id.detaills_iv);
        image = (ImageView) findViewById(R.id.detaills_image);
        nickname = (TextView) findViewById(R.id.detaills_user);
        time = (TextView) findViewById(R.id.detaills_time);
        content = (TextView) findViewById(R.id.detaills_content);
        rv = (RecyclerView) findViewById(R.id.detaills_rv);
        send = (Button) findViewById(R.id.detaills_send);
        et = (EditText) findViewById(R.id.detaills_et);
    }

    public void getReply() {
        String getUrl = "http://123.206.60.236/travelbook/reply_sc.php?id=" + getId;
        HttpUtils.HttpUtilsConnection(getUrl, new HttpUtils.HttpCallBackListener() {
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
    }
}
