//          三清在上     代码永无BUG
//  佛曰：
//        写字楼里写字间,写字间里程序员;
//        程序人员写程序,又拿程序换酒钱;
//        酒醒只在网上坐,酒醉还来网下眠;
//        酒醉酒醒日复日,网上网下年复年;
//        但愿老死电脑间,不愿鞠躬老板前;
//        奔驰宝马贵者趣,公交自行程序员;
//        别人笑我忒疯癫,我笑自己命太贱;
//        不见满街漂亮妹,哪个归得程序员.
package com.example.tf.travelbook;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import adapter.MainActivityRVAdapter;
import model.MainActivityRVModel;
import model.Type;
import utils.HttpUtils;
import utils.SectionDecoration;

/**
 * Created by TF on 2017/1/14.
 */

public class  NearFragment extends Fragment {
    private RecyclerView recyclerView;
    private MainActivityRVAdapter adapter;
    private List<Type> typelist;
    private ArrayList<MainActivityRVModel> list;
    private SearchView searchView;

    private Handler handler = new Handler(
    ) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<MainActivityRVModel> modellist = JSON.parseObject(msg.obj.toString(), new TypeReference<List<MainActivityRVModel>>() {
            }.getType());
            for (MainActivityRVModel model : modellist) {
                list.add(new MainActivityRVModel(model.getJd_name(), model.getJd_id(), model.getJd_content(), model.getJd_img(), model.getJd_price(), model.getJd_city(), model.getJd_adress(), model.getJd_rating(), model.getJd_type()));
            }

            setPullAction(list);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
            recyclerView.addItemDecoration(new SectionDecoration(typelist, getActivity(), new SectionDecoration.DecorationCallback() {

                //返回标记id (即每一项对应的标志性的字符串)
                @Override
                public String getGroupId(int position) {
                    Log.e("TAG", typelist.get(position).getType() + "112");
                    if (typelist.get(position).getType() != null) {

                        return typelist.get(position).getType();
                    }
                    return "-1";
                }

                // 获取同组中的第一个内容
                @Override
                public String getGroupFirstLine(int position) {
                    if (typelist.get(position).getType() != null) {
                        Log.e("TAG", "getGroupId: 12131231131233652");
                        return typelist.get(position).getType();

                    }
                    return "";
                }
            }
            ));

            adapter = new MainActivityRVAdapter(getActivity(), list);
            adapter.setOnItemClickListener(new MainActivityRVAdapter.MainActivityRVListener() {
                @Override
                public void onItemClick(View v, String data) {
                    Intent i = new Intent();
                    i.setClass(getActivity(), NearView.class);
                    TextView tv = (TextView) v.findViewById(R.id.mainactivity_recycleview_item_title);
                    i.putExtra("title", tv.getText());
                    startActivity(i);
                }
            });
            recyclerView.setAdapter(adapter);


        }

    };

    private void setPullAction(List<MainActivityRVModel> list) {
        typelist = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Type nameBean = new Type();
            String name0 = list.get(i).getJd_type();
            //Log.e("typelist", typelist.get(i).getType());
            nameBean.setType(name0);
            typelist.add(nameBean);

        }
    }

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.near_fragment, null, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.nearfragment_recycleview);
        searchView = (SearchView) v.findViewById(R.id.nearfragment_searchview);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getActivity(), SearchResult.class);
                startActivity(i);
            }
        });

        list = new ArrayList<>();
        String url = "http://123.206.60.236/travelbook/jd_sc.php";
        HttpUtils.HttpUtilsConnection(url, new HttpUtils.HttpCallBackListener() {
            @Override
            public void onFinish(String request) {
                Message msg = new Message();
                msg.obj = request.toString();
                msg.what = 0;
                Log.e("msg", msg.obj.toString());
                handler.sendMessage(msg);

            }

            @Override
            public void onError(Exception e) {

            }
        });


        return v;
    }
}
