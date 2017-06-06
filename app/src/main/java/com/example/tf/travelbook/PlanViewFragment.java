package com.example.tf.travelbook;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import adapter.PlanAdapter;
import model.PlanModelAnother;
import utils.HttpUtils;



public class PlanViewFragment extends Fragment {
    private RecyclerView rv;
    private PlanAdapter adapter;
    private ArrayList<PlanModelAnother> list;
    private view_return view_return;
    private static final String TAG = "PlanViewFragment";

    public void setView_return(PlanViewFragment.view_return view_return) {
        this.view_return = view_return;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    List<PlanModelAnother> list_1 = JSON.parseArray(msg.obj.toString(), PlanModelAnother.class);
                    for (PlanModelAnother modelAnother : list_1) {
                        list.add(new PlanModelAnother(modelAnother.getProvince(), modelAnother.getImg()));
                    }
                    adapter.notifyDataSetChanged();

                    break;
            }
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            view_return = (PlanViewFragment.view_return) context;
        } catch (Exception e) {
            Log.e(TAG, "onAttach: 实例化");
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.plan_view_fragment, null);
        rv = (RecyclerView) v.findViewById(R.id.plan_view_fm_rv);
        list = new ArrayList<>();

        rv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        String url = "http://123.206.60.236/travelbook/plan_province_sc.php";
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
        adapter = new PlanAdapter(getActivity(), list);
        adapter.OnItemClickListener(new PlanAdapter.setOnPlanAdapterClickListener() {
            @Override
            public void onItemClick(View v) {
                TextView tv = (TextView) v.findViewById(R.id.plan_view_item_tv);
                view_return = (PlanViewFragment.view_return) getActivity();
                view_return.viewreturn(tv.getText().toString());

            }
        });
        rv.setAdapter(adapter);
        return v;
    }

    interface view_return {
        void viewreturn(String view);
    }
}
