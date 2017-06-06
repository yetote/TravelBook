package com.example.tf.travelbook;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import adapter.FriendCircleAdapter;
import model.FriendCircleModel;
import utils.HttpUtils;
import utils.RevelBackground;

public class FriendCircleActivity extends AppCompatActivity implements RevelBackground.onStateChangeLisener {
    private RecyclerView rv;
    private FriendCircleAdapter adapter;
    private ArrayList<FriendCircleModel> list;
    private FloatingActionButton add;
    private RevelBackground revel;
    private static final String TAG = "FriendCircleActivity";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    List<FriendCircleModel> list1 = JSON.parseArray(msg.obj.toString(), FriendCircleModel.class);
                    for (FriendCircleModel model : list1) {
                        list.add(new FriendCircleModel(model.getTime(), model.getContent(), model.getNickname(), model.getImage(), model.getImg(), model.getUser(), model.getId(), model.getHobby(), model.getComment()));
                        Log.e(TAG, "handleMessage: " + list);
                    }
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_circle);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        list = new ArrayList<>();

        rv = (RecyclerView) findViewById(R.id.friend_rv);
        add = (FloatingActionButton) findViewById(R.id.publish_add);
        rv.setLayoutManager(new LinearLayoutManager(this));

        revel = (RevelBackground) findViewById(R.id.friend_revel);
        startRevelAnimator();


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                int[] location=new int[2];
                location[0]+=v.getWidth();
                v.getLocationOnScreen(location);
                i.putExtra("location", location);
                i.setClass(FriendCircleActivity.this, PublishActivity.class);
                startActivity(i);
            }
        });
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
        Log.e(TAG, "onStateChange: " + state);
        if (state == RevelBackground.ANIMATOR_FINISH) {
            revel.setVisibility(View.INVISIBLE);
            rv.setVisibility(View.VISIBLE);
            String url = "http://123.206.60.236/travelbook/sees_sc.php";
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

            adapter = new FriendCircleAdapter(this, list);
            rv.setAdapter(adapter);

            adapter.adapterListener(new FriendCircleAdapter.setOnClickListener() {
                @Override
                public void OnItemClick(View v, int data) {
                    Intent i = new Intent();

                    TextView conten1 = (TextView) v.findViewById(R.id.friend_item_content);

                    i.setClass(FriendCircleActivity.this, SeesDetaillsActivity.class);
                    i.putExtra("id", list.get(data).getId());
                    i.putExtra("user", list.get(data).getNickname());
                    i.putExtra("time", list.get(data).getTime());
                    i.putExtra("content", list.get(data).getContent());
//                i.putExtra("headimg", list.get(data).getImg());

                    if (list.get(data).getImage() == null) {
                        i.putExtra("image", "null");
                    } else {
                        i.putExtra("image", list.get(data).getImage());
                    }

                    if (list.get(data).getImg() == null) {
                        i.putExtra("headimg", "null");
                    } else {
                        i.putExtra("headimg", list.get(data).getImg());
                    }
                    startActivity(i);
                }
            });
        } else {
            rv.setVisibility(View.INVISIBLE);
        }
    }
}
