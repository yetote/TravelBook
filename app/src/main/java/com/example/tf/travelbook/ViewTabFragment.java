//
//
//                            _ooOoo_
//                           o8888888o
//                           88" . "88
//                           (| -_- |)
//                           O\  =  /O
//                        ____/`---'\____
//                      .'  \\|     |//  `.
//                     /  \\|||  :  |||//  \
//                    /  _||||| -:- |||||-  \
//                    |   | \\\  -  /// |   |
//                    | \_|  ''\---/''  |   |
//                    \  .-\__  `-`  ___/-. /
//                  ___`. .'  /--.--\  `. . __
//               ."" '<  `.___\_<|>_/___.'  >'"".
//              | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//              \  \ `-.   \_ __\ /__ _/   .-` /  /
//         ======`-.____`-.___\_____/___.-`____.-'======
//                            `=---='
//        ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
//                      佛祖保佑       永无BUG
package com.example.tf.travelbook;

import android.app.Fragment;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import adapter.PlanAdapterTwo;
import adapter.ViewTabAdapter;
import model.ViewTabModel;
import utils.HttpUtils;
import utils.SpacesItemDecoration;


public class ViewTabFragment extends Fragment {
    private String TAG = "ViewTabFragment";
    private RecyclerView rv, plan_rv;
    private List<ViewTabModel> list;
    private ViewTabAdapter adapter;
    private List<String> plan_list;
    private list_size list_size;
    private PlanAdapterTwo plan_adapter;
    String url;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                List<ViewTabModel> list1 = JSON.parseArray(msg.obj.toString(), ViewTabModel.class);
                for (ViewTabModel model : list1) {
                    list.add(new ViewTabModel(model.getJd_img(), model.getJd_name()));
                }
                adapter.notifyDataSetChanged();
            } else if (msg.what == 0) {
                Toast.makeText(getActivity(), "暂未查询到结果", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 2) {
                plan_list = (List<String>) msg.obj;
                Log.e(TAG, msg.obj.toString());
                plan_adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        list_size = (ViewTabFragment.list_size) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.view_tab_fragment, null);
        rv = (RecyclerView) v.findViewById(R.id.view_tab_fm_rv);
        plan_rv = (RecyclerView) getActivity().findViewById(R.id.plan_rv_plan);
        plan_rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        list = new ArrayList<>();
        plan_list = new ArrayList<>();
        list_size= (ViewTabFragment.list_size) getActivity();
        Bundle bundle = getArguments();
        String city = bundle.getString("city");
        if (city.equals("全国")) {
            url = "http://123.206.60.236/travelbook/view_tab_sc.php?city=";
            Log.e(TAG, "onCreateView: " + url);
        } else {
            url = "http://123.206.60.236/travelbook/view_tab_sc.php?city=" + city;
            Log.e(TAG, "onCreateView: " + url);
        }
        HttpUtils.HttpUtilsConnection(url, new HttpUtils.HttpCallBackListener() {
            @Override
            public void onFinish(String request) {
                Message msg = new Message();
                if (request.equals("null")) {
                    msg.what = 0;
                } else {
                    msg.what = 1;
                }
                msg.obj = request;
                handler.sendMessage(msg);
            }

            @Override
            public void onError(Exception e) {

            }
        });
        adapter = new ViewTabAdapter(list, getActivity());
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);
        adapter.setOnViewTabAdapterListener(new ViewTabAdapter.ViewTabAdapterListener() {
            @Override
            public void OnItemClick(View v, String data) {
                TextView tv = (TextView) v.findViewById(R.id.view_tab_fm_title);
                plan_rv.setVisibility(View.VISIBLE);
                plan_list.add(tv.getText().toString());
                plan_adapter = new PlanAdapterTwo(getActivity(), plan_list);
                plan_rv.setAdapter(plan_adapter);
                plan_adapter.List_size(new PlanAdapterTwo.list_size() {
                    @Override
                    public void list_size(List<String> list) {
                        list_size.list_size(list);
                    }
                });


            }
        });
        plan_rv.addItemDecoration(new SpacesItemDecoration(5));

        return v;
    }

    public void date(List<String> plan_list) {

        Message msg = new Message();
        msg.what = 2;
        msg.obj = plan_list;
        handler.sendMessage(msg);
    }

    public interface list_size {
        void list_size(List<String> list);
    }
}
