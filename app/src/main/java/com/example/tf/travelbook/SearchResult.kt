
package com.example.tf.travelbook

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.widget.SearchView
import android.widget.Toast

import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener
import com.baidu.mapapi.search.poi.PoiCitySearchOption
import com.baidu.mapapi.search.poi.PoiDetailResult
import com.baidu.mapapi.search.poi.PoiIndoorResult
import com.baidu.mapapi.search.poi.PoiResult
import com.baidu.mapapi.search.poi.PoiSearch
import kotlinx.android.synthetic.main.activity_search_result.*
import java.util.ArrayList

import adapter.SearchResultRVAdapter
import android.view.WindowManager
import model.SearchResultRVModle

class SearchResult : AppCompatActivity() {
    //    private var searchView: SearchView? = null
    private var poiSearch: PoiSearch? = null
    //    private var rv: RecyclerView? = null
    private var adapter: SearchResultRVAdapter? = null
    private var list: MutableList<SearchResultRVModle>? = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        poiSearch = PoiSearch.newInstance()
        searchresult_recycleview.layoutManager = LinearLayoutManager(this)
        list = ArrayList<SearchResultRVModle>()
        adapter = SearchResultRVAdapter(this, list as ArrayList<SearchResultRVModle>)
        searchresult_recycleview.adapter = adapter
        val onGetPoiSearchResultListener = object : OnGetPoiSearchResultListener {
            override fun onGetPoiResult(poiResult: PoiResult) {
                if (poiResult.error != com.baidu.mapapi.search.core.SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(this@SearchResult, "未找到搜索信息，请检查是否正确", Toast.LENGTH_SHORT).show()
                } else {
                    for (i in 0..poiResult.allPoi.size - 1) {
                        val poiname = poiResult.allPoi[i].name
                        val poiadress = poiResult.allPoi[i].address
                        list!!.add(SearchResultRVModle(poiname, poiadress))
                        // adapter.notifyDataSetChanged();
                        Log.e("asxcfvgj", ": " + list);
                    }
                    adapter!!.notifyDataSetChanged()
                }
            }

            override fun onGetPoiDetailResult(poiDetailResult: PoiDetailResult) {
            }

            override fun onGetPoiIndoorResult(poiIndoorResult: PoiIndoorResult) {
            }
        }
        poiSearch!!.setOnGetPoiSearchResultListener(onGetPoiSearchResultListener)

        searchresult_searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                poiSearch!!.searchInCity(PoiCitySearchOption().city(searchresult_searchview.query.toString()).keyword("景点").pageNum(20))
                // Log.e("city", searchresult_searchview.query.toString())
                searchresult_searchview.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (TextUtils.isEmpty(newText)) {

                    searchresult_recycleview.removeAllViews()
                    list!!.clear()
                } else {

                }
                return true
            }
        })
    }
}
