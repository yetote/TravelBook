package com.example.tf.travelbook;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import adapter.TripRVAdapter;
import model.PlanModel;
import utils.HttpUtils;

/**
 * Created by TF on 2017/2/9.
 */

public class TripFragment extends Fragment {
    private Button longtrip_btn, shorttrip_btn, longtrip_insert, shorttrip_insert;
    private RecyclerView shorttrip_rv, longtrip_rv;
    private TextView shorttrip_tv, longtrip_tv;
    private List<String> list;
    List<PlanModel> plan_list;
    private TripRVAdapter adapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                List<PlanModel> p_list = JSON.parseArray(msg.obj.toString(), PlanModel.class);
                Log.e("TripJson", p_list.toString());
                for (PlanModel model : p_list) {
                    plan_list.add(new PlanModel(model.getP_name(), model.getP_id(), model.getP_jd1(), model.getP_jd2(), model.getP_jd3(), model.getP_jd4(), model.getP_jd5(), model.getP_jd6(), model.getP_jd7(), model.getP_jd8(), model.getP_jd9(), model.getP_jd10(), model.getP_user()));
                    list.add(model.getP_name().toString());
                    Log.e("l", list.toString());
                }
                adapter.notifyDataSetChanged();
                adapter.setOnItemClickListener(new TripRVAdapter.TripAdapaterListener() {
                    @Override
                    public void onItemClick(View v, String data) {
                        TextView tv = (TextView) v.findViewById(R.id.trip_fragment_rv_item_tv);
                        Intent i = new Intent();
                        i.setClass(getActivity(), TripPlan.class);
                        i.putExtra("plan_name", tv.getText().toString());
                        startActivity(i);
                    }
                });
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.trip_fragment, null);
        longtrip_btn = (Button) v.findViewById(R.id.trip_fragment_longtrip_btn);
        shorttrip_btn = (Button) v.findViewById(R.id.trip_fragment_shorttrip_btn);
        shorttrip_rv = (RecyclerView) v.findViewById(R.id.trip_fragment_shorttrip_rv);
        longtrip_rv = (RecyclerView) v.findViewById(R.id.trip_fragment_longtrip_rv);
        shorttrip_tv = (TextView) v.findViewById(R.id.trip_fragment_shorttrip_tv);
        longtrip_tv = (TextView) v.findViewById(R.id.trip_fragment_longtrip_tv);
        longtrip_insert = (Button) v.findViewById(R.id.trip_fragment_longtrip_insert);
        shorttrip_insert = (Button) v.findViewById(R.id.trip_fragment_shorttrip_insert);
        list = new ArrayList<>();
        plan_list = new ArrayList<>();
        String p_user = "admin";
        String url = "http://123.206.60.236/travelbook/jd_plan_sc.php?p_user=admin";
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


        longtrip_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                ad.setTitle("长途行程");
                ad.setMessage("长途行程是指您需要进行跨省旅行，需要购买火车票，飞机票等长途交通工具的行程(暂不支持跨国旅行)");
                ad.setPositiveButton("确定", null);
                ad.show();

            }
        });
        shorttrip_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                ad.setTitle("短途行程");
                ad.setMessage("短途行程是指您在省内旅行的旅行计划，只需乘坐公交车、出租车或者步行即可到达目的地的旅行计划");
                ad.setPositiveButton("确定", null);
                ad.show();

            }
        });
        shorttrip_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (shorttrip_rv.getVisibility() == View.GONE) {
                    shorttrip_rv.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();
                    Log.e("short", "可见");
                } else if (shorttrip_rv.getVisibility() == View.VISIBLE) {
                    shorttrip_rv.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                    Log.e("short", "no可见");
                }
            }
        });
        longtrip_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (longtrip_rv.getVisibility() == View.INVISIBLE) {
                    longtrip_rv.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();
                    longtrip_rv.setAdapter(adapter);
                    Log.e("long", "可见");
                } else if (longtrip_rv.getVisibility() == View.VISIBLE) {
                    longtrip_rv.setVisibility(View.INVISIBLE);
                    adapter.notifyDataSetChanged();
                    longtrip_rv.setAdapter(adapter);
                    Log.e("long", "no可见");
                }
            }
        });
        longtrip_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getActivity(), LongTrip.class);
                startActivity(i);
            }
        });
        shorttrip_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getActivity(), ShortTrip.class);
                startActivity(i);
            }
        });

        longtrip_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        shorttrip_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (list.size() == 0) {
            list.add("您还没有行程，请添加");
        }
        if (list.size() != 0) {
            list.remove(0);
        }

        Log.e("list", list.toString());
        adapter = new TripRVAdapter(list, getActivity());

        longtrip_rv.setAdapter(adapter);
        shorttrip_rv.setAdapter(adapter);
        return v;
    }
}
