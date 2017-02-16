package com.example.tf.travelbook;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.volokh.danylo.layoutmanager.LondonEyeLayoutManager;
import com.volokh.danylo.layoutmanager.scroller.IScrollHandler;
import com.volokh.danylo.utils.DebugRecyclerView;

import java.util.ArrayList;
import java.util.List;

import adapter.TripPlanRVAdapter;
import adapter.TripRVAdapter;
import model.MainActivityRVModel;
import model.PlanModel;
import model.PlanViewModel;
import model.TestModel;
import model.ViewImgModel;
import utils.HttpUtils;

public class TripPlan extends AppCompatActivity {
    private static String TAG = "TripPlan";
    private DebugRecyclerView plan_rv;
    private TripPlanRVAdapter plan_adapter;
    private LondonEyeLayoutManager mLondonEyeLayoutManager;
    private List<PlanViewModel> view_list;
    private List<String> trip_plan_list;
    private List<ViewImgModel> view_img_list;
    private ImageView iv;
    String view_img;
    String url;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {

                List<PlanViewModel> view_list = JSON.parseArray(msg.obj.toString(), PlanViewModel.class);
                for (int i = 0; i < view_list.size(); i++) {
                    Log.e(TAG, "handleMessage: " + view_list.get(i).getP_jd1());
                    trip_plan_list.add(view_list.get(i).getP_jd1());
                    trip_plan_list.add(view_list.get(i).getP_jd2());
                    trip_plan_list.add(view_list.get(i).getP_jd3());
                    trip_plan_list.add(view_list.get(i).getP_jd4());
                    trip_plan_list.add(view_list.get(i).getP_jd5());
                    trip_plan_list.add(view_list.get(i).getP_jd6());
                    trip_plan_list.add(view_list.get(i).getP_jd7());
                    trip_plan_list.add(view_list.get(i).getP_jd8());
                    trip_plan_list.add(view_list.get(i).getP_jd9());
                    trip_plan_list.add(view_list.get(i).getP_jd10());
                    Log.e(TAG, trip_plan_list.get(1));
                }
                plan_adapter.notifyDataSetChanged();
            }
            if (msg.what == 2) {
                view_img_list = JSON.parseArray(msg.obj.toString(), ViewImgModel.class);
                for (ViewImgModel model : view_img_list) {
                    view_img = view_img_list.get(0).getJd_img();
                    Log.e(TAG, view_img);
                }
                Glide.with(TripPlan.this).load(view_img).into(iv);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_plan);
        Intent i = getIntent();
        String plan_name = i.getStringExtra("plan_name");
        Log.e(TAG, plan_name);
        view_img_list = new ArrayList<>();
        iv = (ImageView) findViewById(R.id.trip_plan_iv);
        trip_plan_list = new ArrayList<>();
        url = "http://123.206.60.236/travelbook/trip_plan_sc.php?name=" + plan_name;
        Log.e(TAG, "onCreate: " + url);
        HttpUtils.HttpUtilsConnection(url, new HttpUtils.HttpCallBackListener() {
            @Override
            public void onFinish(String request) {
                Message msg = new Message();
                msg.what = 1;
                msg.obj = request.toString();
                Log.e(TAG, "onFinish: " + msg.obj);
                handler.sendMessage(msg);
            }

            @Override
            public void onError(Exception e) {

            }
        });
        plan_rv = (DebugRecyclerView) findViewById(R.id.trip_plan_rv);

        int screenWidth = this.getResources().getDisplayMetrics().widthPixels;
        int screenHeight = this.getResources().getDisplayMetrics().heightPixels;

//        List<String> list = new ArrayList<>();
//        list.add("金石滩");
//        list.add("b");
//        list.add("c");
//        list.add("d");
//        list.add("e");
//        list.add("f");
//        list.add("g");
//        list.add("h");
//        list.add("i");
//        list.add("j");
//        list.add("k");
//        list.add("l");
//        list.add("m");
//        list.add("n");

        plan_adapter = new TripPlanRVAdapter(this, trip_plan_list);
        int circleRadius = screenWidth;

        int xOrigin = -200;
        int yOrigin = 0;
        // mRecyclerView = (DebugRecyclerView) rootView.findViewById(R.id.recycler_view);
        plan_rv.setParameters(circleRadius, xOrigin, yOrigin);

//
//            // use this setting to improve performance if you know that changes
//            // in content do not change the layout size of the RecyclerView
        plan_rv.setHasFixedSize(true);
        mLondonEyeLayoutManager = new LondonEyeLayoutManager(
                circleRadius,
                xOrigin,
                yOrigin,
                plan_rv,
                IScrollHandler.Strategy.NATURAL);
        plan_rv.setLayoutManager(mLondonEyeLayoutManager);
        plan_rv.setAdapter(plan_adapter);
        plan_adapter.setOnItemClickListener(new TripPlanRVAdapter.TripPlanRVAdapterListener() {
            @Override
            public void onItemClik(View v, String data) {
                TextView tv = (TextView) v.findViewById(R.id.simple_rv_item_tv);
                String view_name = tv.getText().toString();
                String url2 = "http://123.206.60.236/travelbook/view_url.php?name=" + view_name;
                Log.e(TAG, "onItemClik: " + url2);
                HttpUtils.HttpUtilsConnection(url2, new HttpUtils.HttpCallBackListener() {
                    @Override
                    public void onFinish(String request) {
                        Message msg = new Message();
                        msg.what = 2;
                        msg.obj = request.toString();
                        handler.sendMessage(msg);
                        Log.e(TAG, "onFinish: " + msg.obj);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }
        });

    }
}
