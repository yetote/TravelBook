package com.example.tf.travelbook;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import utils.HttpUtils;
import utils.RevelBackground;

import static android.view.View.GONE;

public class PublishActivity extends AppCompatActivity implements RevelBackground.onStateChangeLisener {
    private EditText title, content;
    private Button sure;
    private RevelBackground revel;
    private static final String TAG = "PublishActivity";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (msg.obj.equals("success")) {
                        Toast.makeText(PublishActivity.this, "添加成功，请刷新", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PublishActivity.this, "未知原因添加失败，请联系管理员", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        initViews();
        startRevelAnimator();
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NULL()) {
                    Toast.makeText(PublishActivity.this, "标题与内容不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    Calendar c = Calendar.getInstance();
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH) + 1;
                    int day = c.get(Calendar.DAY_OF_MONTH);
                    int hour = c.get(Calendar.HOUR_OF_DAY);
                    int min = c.get(Calendar.MINUTE);
                    int sec = c.get(Calendar.SECOND);
                    String date = String.format("%d-%d-%d %d:%d:%d", year, month, day, hour, min, sec);

                    String url = "http://123.206.60.236/travelbook/theme_in.php?tel=" + MyAppliation.TEL + "&content=" + content.getText().toString() + "&date=" + date;
                    Log.e(TAG, "onClick: " + url);
//                    HttpUtils.HttpUtilsConnection(url, new HttpUtils.HttpCallBackListener() {
//                        @Override
//                        public void onFinish(String request) {
//                            Message msg = new Message();
//                            msg.what = 1;
//                            msg.obj = request.toString();
//                            handler.sendMessage(msg);
//                        }
//
//                        @Override
//                        public void onError(Exception e) {
//
//                        }
//                    });
                    HttpUtils.HttpUtilsConnection(url, new HttpUtils.HttpCallBackListener() {
                        @Override
                        public void onFinish(String request) {
                            Message msg = new Message();
                            msg.what = 1;
                            msg.obj = request;
                            Log.e(TAG, "onFinish: "+request );
                            handler.sendMessage(msg);
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });
                }
            }
        });
    }

    private boolean NULL() {
        return content.getText().toString().isEmpty();
    }

    private void initViews() {
//        title = (EditText) findViewById(R.id.publish_title);
        content = (EditText) findViewById(R.id.publish_content);
        sure = (Button) findViewById(R.id.publish_sure);
        revel = (RevelBackground) findViewById(R.id.publish_revel);
    }

    private void startRevelAnimator() {
        revel.setListener(this);
        final int[] location = getIntent().getIntArrayExtra("location");
        revel.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                revel.getViewTreeObserver().removeOnPreDrawListener(this);
                revel.startRevelAnimator(location);
                return true;
            }
        });
    }

    @Override
    public void onStateChange(int state) {
        if (state == RevelBackground.ANIMATOR_FINISH) {
            revel.setVisibility(GONE);
            content.setVisibility(View.VISIBLE);
            sure.setVisibility(View.VISIBLE);
            AlphaAnimation aa = new AlphaAnimation(0, 1);
            aa.setDuration(500);
            content.startAnimation(aa);
            AlphaAnimation aa1 = new AlphaAnimation(0, 1);
            aa1.setDuration(500);
            aa1.setStartOffset(500);
            sure.setAnimation(aa1);
        } else {
            content.setVisibility(GONE);
            sure.setVisibility(GONE);
        }
    }
}
