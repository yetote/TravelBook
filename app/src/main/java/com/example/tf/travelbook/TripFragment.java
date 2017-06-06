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
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import adapter.TripRVAdapter;
import model.PlanModel;
import model.PlanNameModel;
import utils.HttpUtils;
import utils.MyDecoration;
import utils.ScrollRemoveRecyclerView.MyRv;
import utils.ScrollRemoveRecyclerView.OnItemClickListener;

import static android.R.attr.visibility;



public class TripFragment extends Fragment implements View.OnClickListener {
    private Button longtrip_btn;
    private Button shorttrip_btn;
    private Button longtrip_insert;
    private Button shorttrip_insert;
    private MyRv shorttrip_rv;
    private RecyclerView longtrip_rv;
    private TextView shorttrip_tv;
    private TextView longtrip_tv;
    private ArrayList<PlanNameModel> list;
    private ArrayList<PlanModel> plan_list;
    private TripRVAdapter adapter;
    private static final String TAG = "TripFragment";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (msg.obj.equals("null")) {
                        Toast.makeText(getActivity(), "您还未添加任何行程", Toast.LENGTH_SHORT).show();
                    } else {
                        List<PlanModel> p_list = JSON.parseArray(msg.obj.toString(), PlanModel.class);
                        Log.e("TripJson", p_list.toString());
                        for (PlanModel model : p_list) {
                            plan_list.add(new PlanModel(model.getP_name(), model.getP_id(), model.getP_jd1(), model.getP_jd2(), model.getP_jd3(), model.getP_jd4(), model.getP_jd5(), model.getP_jd6(), model.getP_jd7(), model.getP_jd8(), model.getP_jd9(), model.getP_jd10(), model.getP_user()));
                            list.add(new PlanNameModel(model.getP_name(), model.getP_id()));
                            Log.e("l", list.toString());
                        }
                        adapter.notifyDataSetChanged();

                    }
                    break;
                case 2:
                    Bundle bundle = msg.getData();
                    String resultCode = bundle.getString("resultCode", "failed");
                    Log.e(TAG, "handleMessage: "+resultCode );
                    int position = bundle.getInt("position", -1);
                    if (resultCode.equals("success")) {
                        Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                        adapter.removeItem(position);
                    } else {
                        Toast.makeText(getActivity(), "未知原因导致失败，请联系管理员", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.trip_fragment, null);
        initViews(v);
        list = new ArrayList<>();
        plan_list = new ArrayList<>();

        String p_user = MyAppliation.TEL;
        String url = "http://123.206.60.236/travelbook/jd_plan_sc.php?p_user=" + p_user;

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

        onclick();


        longtrip_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        shorttrip_rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (list.size() == 0) {
            list.add(new PlanNameModel("您还没有行程，请添加", 0));
        }
        if (list.size() != 0) {
            list.remove(0);
        }
        Log.e("list", list.toString());
        adapter = new TripRVAdapter(list, getActivity());
        longtrip_rv.setAdapter(adapter);
        shorttrip_rv.setAdapter(adapter);
        shorttrip_rv.addItemDecoration(new MyDecoration(getActivity(), MyDecoration.VERTICAL_LIST));
        shorttrip_rv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                Intent i = new Intent();
                i.setClass(getActivity(), TripPlan.class);
                TextView plan_name = (TextView) v.findViewById(R.id.trip_fragment_rv_item_tv);
                i.putExtra("plan_name", plan_name.getText().toString());
                startActivity(i);
//                Toast.makeText(getActivity(), "safdrftyu", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteClick(final int position) {
//                adapter.removeItem(position);
                String url = "http://123.206.60.236/travelbook/delete_plan.php?id=" + list.get(position).getId();
                Log.e(TAG, "onDeleteClick: "+url);
                HttpUtils.HttpUtilsConnection(url, new HttpUtils.HttpCallBackListener() {
                    @Override
                    public void onFinish(String request) {
                        Message msg = new Message();
                        msg.what = 2;
                        Bundle bundle = new Bundle();
                        bundle.putString("resultCode", request);
                        bundle.putInt("position", position);
                        Log.e(TAG, "onFinish: "+request );
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }
        });

        return v;
    }

    private void onclick() {
        longtrip_btn.setOnClickListener(this);
        shorttrip_btn.setOnClickListener(this);
        shorttrip_tv.setOnClickListener(this);
        longtrip_tv.setOnClickListener(this);
        longtrip_insert.setOnClickListener(this);
        shorttrip_insert.setOnClickListener(this);
    }

    private void initViews(View v) {
        longtrip_btn = (Button) v.findViewById(R.id.trip_fragment_longtrip_btn);
        shorttrip_btn = (Button) v.findViewById(R.id.trip_fragment_shorttrip_btn);
        shorttrip_rv = (MyRv) v.findViewById(R.id.trip_fragment_shorttrip_rv);
        longtrip_rv = (RecyclerView) v.findViewById(R.id.trip_fragment_longtrip_rv);
        shorttrip_tv = (TextView) v.findViewById(R.id.trip_fragment_shorttrip_tv);
        longtrip_tv = (TextView) v.findViewById(R.id.trip_fragment_longtrip_tv);
        longtrip_insert = (Button) v.findViewById(R.id.trip_fragment_longtrip_insert);
        shorttrip_insert = (Button) v.findViewById(R.id.trip_fragment_shorttrip_insert);

    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
        Intent i = new Intent();
        switch (v.getId()) {
            case R.id.trip_fragment_longtrip_btn:
                ad.setTitle("长途行程");
                ad.setMessage("长途行程是指您需要进行跨省旅行，需要购买火车票，飞机票等长途交通工具的行程(暂不支持跨国旅行)");
                ad.setPositiveButton("确定", null);
                ad.show();
                break;

            case R.id.trip_fragment_shorttrip_btn:
//                AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                ad.setTitle("短途行程");
                ad.setMessage("短途行程是指您在省内旅行的旅行计划，只需乘坐公交车、出租车或者步行即可到达目的地的旅行计划");
                ad.setPositiveButton("确定", null);
                ad.show();
                break;
            case R.id.trip_fragment_shorttrip_tv:
                if (shorttrip_rv.getVisibility() == View.GONE) {
                    shorttrip_rv.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();
                    //Log.e("short", "可见")
                } else if (shorttrip_rv.getVisibility() == View.VISIBLE) {
                    shorttrip_rv.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                    //Log.e("short", "no可见")
                }
                break;

            case R.id.trip_fragment_longtrip_tv:
                if (longtrip_rv.getVisibility() == View.GONE) {
                    longtrip_rv.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();
                    longtrip_rv.setAdapter(adapter);
                    //Log.e("long", "可见")
                } else if (longtrip_rv.getVisibility() == View.VISIBLE) {
                    longtrip_rv.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                    longtrip_rv.setAdapter(adapter);
                    //Log.e("long", "no可见")
                }
                break;
            case R.id.trip_fragment_longtrip_insert:
                i.setClass(getActivity(), Plan.class);
                startActivity(i);
                break;
            case R.id.trip_fragment_shorttrip_insert:
                i.setClass(getActivity(), Plan.class);
                startActivity(i);
        }
    }
}
