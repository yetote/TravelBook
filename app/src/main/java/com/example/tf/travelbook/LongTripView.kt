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
package com.example.tf.travelbook

import adapter.ShortTripProvinceRVAdapter
import adapter.ShortTripViewRVAdapter
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.alibaba.fastjson.JSON
import kotlinx.android.synthetic.main.activity_long_trip_view.*
import model.CityModel
import model.ProvinceModel
import utils.HttpUtils

class LongTripView : AppCompatActivity() {
    private val TAG: String = "$javaClass"
    var city_list: MutableList<String> = mutableListOf()
    var province_list: MutableList<String> = mutableListOf()
    var province_adapter: ShortTripProvinceRVAdapter? = null
    var city_adapter: ShortTripViewRVAdapter? = null
    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 0) {
                Toast.makeText(this@LongTripView, "暂未查询到数据", Toast.LENGTH_SHORT).show()
            } else if (msg.what == 1) {
                var list = JSON.parseArray(msg.obj.toString(), ProvinceModel::class.java)
                Log.e(TAG, list.map { it }.toString())
                province_list.addAll(list.map { it.province })
                province_adapter!!.notifyDataSetChanged()
            } else if (msg.what == 2) {
                var cityList = JSON.parseArray(msg.obj.toString(), CityModel::class.java)
                city_list.addAll(cityList.map { it.city })
                city_adapter!!.notifyDataSetChanged()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_long_trip_view)
        province()
        city()

    }

    private fun province() {
        long_trip_view_province.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val province_url = "http://123.206.60.236/travelbook/province_sc.php"
        HttpUtils.HttpUtilsConnection(province_url, object : HttpUtils.HttpCallBackListener {
            override fun onFinish(request: String) {
                val msg = Message()
                msg.what = 1
                msg.obj = request
                handler.sendMessage(msg)
            }

            override fun onError(e: Exception) {

            }
        })
        province_adapter = ShortTripProvinceRVAdapter(this, province_list)
        //var city_adapter:ShortTripViewRVAdapter=
        long_trip_view_province.adapter = province_adapter
        province_adapter!!.setOnItemClickListener { v, data ->
            city_list.clear()
            city_adapter!!.notifyDataSetChanged()
            val tv = v.findViewById(R.id.short_trip_province_item_tv) as TextView
            var city_url = "http://123.206.60.236/travelbook/city_sc.php?province=" + tv.text.toString()
            HttpUtils.HttpUtilsConnection(city_url, object : HttpUtils.HttpCallBackListener {
                override fun onFinish(request: String) {
                    val msg = Message()
                    msg.obj = request
                    if (msg.obj == "null") {
                        msg.what = 0
                    } else {
                        msg.what = 2
                    }
                    handler.sendMessage(msg)
                }

                override fun onError(e: Exception) {}
            })
        }
    }

    private fun city() {
        long_trip_view_city.layoutManager = LinearLayoutManager(this)
        city_adapter = ShortTripViewRVAdapter(this, city_list)
        city_adapter!!.setOnItemClickListener { v, data ->
            val tv = v.findViewById(R.id.short_trip_view_item_tv) as TextView
            val i = Intent()
            i.putExtra("city", tv.text.toString())
            setResult(Activity.RESULT_OK, i)
            finish()
        }
        long_trip_view_city.adapter = city_adapter
    }
}

