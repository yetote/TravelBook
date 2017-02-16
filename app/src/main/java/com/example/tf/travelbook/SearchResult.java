package com.example.tf.travelbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import java.util.ArrayList;

import adapter.SearchResultRVAdapter;
import model.SearchResultRVModle;

public class SearchResult extends AppCompatActivity {
    private SearchView searchView;
    private PoiSearch poiSearch;
    private RecyclerView rv;
    private SearchResultRVAdapter adapter;
    private ArrayList<SearchResultRVModle> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        searchView = (SearchView) findViewById(R.id.searchresult_searchview);
        poiSearch = PoiSearch.newInstance();
        rv = (RecyclerView) findViewById(R.id.searchresult_recycleview);
        rv.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new SearchResultRVAdapter(this, list);
        rv.setAdapter(adapter);

        OnGetPoiSearchResultListener onGetPoiSearchResultListener = new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                if (poiResult.error != com.baidu.mapapi.search.core.SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(SearchResult.this, "未找到搜索信息，请检查是否正确", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < poiResult.getAllPoi().size(); i++) {
                        String poiname = poiResult.getAllPoi().get(i).name;
                        String poiadress = poiResult.getAllPoi().get(i).address;
                        list.add(new SearchResultRVModle(poiname, poiadress));
                       // adapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        };
        poiSearch.setOnGetPoiSearchResultListener(onGetPoiSearchResultListener);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                poiSearch.searchInCity(new PoiCitySearchOption().city(searchView.getQuery().toString()).keyword("景点").pageNum(20));
                Log.e("city", searchView.getQuery().toString());
                searchView.clearFocus();//隐藏键盘
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(TextUtils.isEmpty(newText))
                {
                    //清楚ListView的过滤
                    rv.removeAllViews();
                    list.clear();
                }
                else
                {
                    //使用用户输入的内容对ListView的列表项进行过滤
//                    rv.setFilterText(newText);

                }

                return true;

            }
        });


    }
}
