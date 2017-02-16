package com.example.tf.travelbook;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.OnImageClickListener;
import com.zzhoujay.richtext.callback.OnImageLongClickListener;

import java.util.List;

import utils.HttpUtils;

public class NearView extends AppCompatActivity {
    private static String TAG = "NearView";
    private TextView tv;
    private String url;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            html(msg.obj.toString());

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_view);
        tv = (TextView) findViewById(R.id.near_view_tv);

        Intent i = getIntent();
        String name = i.getStringExtra("title");
        Log.e(TAG, name);
        if (name.equals("金石滩")) {
            String url = "http://123.206.60.236/travelbook/jd_jst.html";
            HttpUtils.HttpUtilsConnection(url, new HttpUtils.HttpCallBackListener() {
                @Override
                public void onFinish(String request) {
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = request.toString();
                    handler.sendMessage(msg);
                }

                @Override
                public void onError(Exception e) {

                }
            });
        } else {
            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setTitle("请您原谅");
            ad.setMessage("因为技术没有女朋友，伤心过度，撂挑子了");
            ad.setPositiveButton("确定", null);
            ad.show();
            finish();
        }


    }

    private void html(String url) {
        Log.e(TAG, "html: " + url);
        RichText.from(url).imageClick(new OnImageClickListener() {
            @Override
            public void imageClicked(List<String> imageUrls, int position) {
                Toast.makeText(NearView.this, "我是图片", Toast.LENGTH_SHORT).show();

            }
        }).imageLongClick(new OnImageLongClickListener() {
            @Override
            public boolean imageLongClicked(List<String> imageUrls, int position) {
                Toast.makeText(NearView.this, "你还按？", Toast.LENGTH_SHORT).show();
                return true;
            }
        }).into(tv);
    }


}
