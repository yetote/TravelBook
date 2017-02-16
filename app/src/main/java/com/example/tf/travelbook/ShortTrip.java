package com.example.tf.travelbook;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import adapter.MainActivityRVAdapter;
import adapter.PlanRVAdapter;
import adapter.ShortTripProvinceRVAdapter;
import adapter.ShortTripViewRVAdapter;
import adapter.TripRVAdapter;
import model.MainActivityRVModel;
import model.Type;
import utils.HttpUtils;
import utils.SectionDecoration;

public class ShortTrip extends AppCompatActivity {
    private RecyclerView province, view_rv;
    private TextView view_tv;
    private ShortTripProvinceRVAdapter province_adapter;
    private RecyclerView plan_rv;
    private List<String> province_list;
    List<String> view_list;
    List<Type> type_list;
    private PlanRVAdapter plan_adpter;
    private List<String> trip_list;
    String url = "http://123.206.60.236/travelbook/jd_sc.php";
    private String TAG = "Short_Trip";
    private TextView trip_tv;
    private int WHAT = 0;
    private ShortTripViewRVAdapter view_adapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == WHAT) {
                List<MainActivityRVModel> model = JSON.parseArray(msg.obj.toString(), MainActivityRVModel.class);
                //Log.e(TAG, model.get(1).getJd_name());
                for (int i = 0; i < model.size(); i++) {
                    view_list.add(model.get(i).getJd_name());
                    Log.e(TAG, view_list.get(i));
                }
                type(model);
                view_rv.setLayoutManager(new LinearLayoutManager(ShortTrip.this));

                view_adapter = new ShortTripViewRVAdapter(ShortTrip.this, view_list);

                view_rv.addItemDecoration(new SectionDecoration(type_list, ShortTrip.this, new SectionDecoration.DecorationCallback() {
                    @Override
                    public String getGroupId(int position) {
                        if (type_list.get(position).getType() != null) {
                            return type_list.get(position).getType();
                        }
                        return "-1";
                    }

                    @Override
                    public String getGroupFirstLine(int position) {
                        if (type_list.get(position).getType() != null) {
                            return type_list.get(position).getType();
                        }
                        return "";
                    }
                }));
                view_adapter.setOnItemClickListener(new ShortTripViewRVAdapter.ShortTripViewRVAdapterListener() {
                    @Override
                    public void OnItemClick(View v, String data) {
                        trip_tv = (TextView) v.findViewById(R.id.short_trip_view_item_tv);
                        Intent i = new Intent();
                        i.setClass(ShortTrip.this, NearView.class);
                        i.putExtra("title", trip_tv.getText());
                        startActivity(i);
                    }
                });
                view_rv.setAdapter(view_adapter);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_trip);
        province = (RecyclerView) findViewById(R.id.short_trip_province_rv);
        view_rv = (RecyclerView) findViewById(R.id.short_trip_view_rv);

        trip_list = new ArrayList<>();
        province();
        view();
        plan();


    }

    //省份RecyclerView的实现
    private void province() {
        province_list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            province_list.add(i + "");
        }
        province.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        province.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, 0, 0, 2);

            }
        });
        province_adapter = new ShortTripProvinceRVAdapter(this, province_list);
        province.setAdapter(province_adapter);
    }

    //省份下的景点的RecyclerView的实现
    private void view() {
        view_list = new ArrayList<>();

        HttpUtils.HttpUtilsConnection(url, new HttpUtils.HttpCallBackListener() {
            @Override
            public void onFinish(String request) {
                Message msg = new Message();
                msg.obj = request.toString();
                msg.what = WHAT;
                Log.e(TAG, msg.obj.toString());
                handler.sendMessage(msg);
            }

            @Override
            public void onError(Exception e) {

            }
        });
//        Button jiahao = (Button) findViewById(R.id.short_trip_view_item_insert);
//        jiahao.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(ShortTrip.this, trip_tv.getText().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    //计划的recyclerview的实现

    private void plan() {
        plan_rv = (RecyclerView) findViewById(R.id.short_trip_plan_rv);
        plan_rv.setLayoutManager(new LinearLayoutManager(this));
        Log.e(TAG, "plan: " + trip_list.size());
        plan_rv.setAdapter(plan_adpter);
    }


    private void type(List<MainActivityRVModel> list) {
        type_list = new ArrayList<>();
        Type type_pinyin = new Type();
        for (int i = 0; i < list.size(); i++) {
            String type = list.get(i).getJd_type();
            type_pinyin.setType(type);
            type_list.add(type_pinyin);
        }
    }

    public void plan_list(List<String> plan) {
        plan_adpter = new PlanRVAdapter(this, plan);
        //Log.e(TAG, list.size() + "");
        plan_adpter = new PlanRVAdapter(this, plan);
        Log.e(TAG, "plan_list: " + plan.size());
        plan_adpter.notifyDataSetChanged();
        // plan();

    }
}
