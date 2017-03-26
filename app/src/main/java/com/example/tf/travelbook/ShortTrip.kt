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

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.TextView

import com.alibaba.fastjson.JSON

import java.util.ArrayList
import kotlinx.android.synthetic.main.activity_short_trip.*
import adapter.PlanRVAdapter
import adapter.ShortTripProvinceRVAdapter
import adapter.ShortTripViewRVAdapter
import android.app.Activity
import model.MainActivityRVModel
import model.Type
import utils.HttpUtils
import utils.SectionDecoration

class ShortTrip : AppCompatActivity() {
    private var province_adapter: ShortTripProvinceRVAdapter? = null
    private val plan_rv: RecyclerView? = null
    private var province_list: MutableList<String>? = mutableListOf()
    private var view_list: MutableList<String> = mutableListOf()
    private var type_list: MutableList<Type> = mutableListOf()
    private val plan_adpter: PlanRVAdapter? = null
    private var trip_list: MutableList<String>? = mutableListOf()
    internal var url = "http://123.206.60.236/travelbook/jd_sc.php"
    private val TAG = "[$javaClass]"
    private val WHAT = 0
    private var view_adapter: ShortTripViewRVAdapter? = null
    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == WHAT) {
                val model = JSON.parseArray(msg.obj.toString(), MainActivityRVModel::class.java)
                //Log.e(TAG, model.get(1).getJd_name());
                for (i in model.indices) {
                    view_list.add(model[i].jd_name)
                    Log.e(TAG, view_list[i])
                }
                type(model)
                short_trip_view_rv.layoutManager = LinearLayoutManager(this@ShortTrip)
                view_adapter = ShortTripViewRVAdapter(this@ShortTrip, view_list)
                short_trip_view_rv.addItemDecoration(SectionDecoration(type_list, this@ShortTrip, object : SectionDecoration.DecorationCallback {
                    override fun getGroupId(position: Int): String {
                        if (type_list[position].type != null) {
                            return type_list[position].type
                        }
                        return "-1"
                    }

                    override fun getGroupFirstLine(position: Int): String {
                        if (type_list[position].type != null) {
                            return type_list[position].type
                        }
                        return ""
                    }
                }))
                view_adapter!!.setOnItemClickListener { v, data ->
                    var trip_tv: TextView = v.findViewById(R.id.short_trip_view_item_tv) as TextView
                    val i = Intent()
                    i.setClass(this@ShortTrip, NearView::class.java)
                    i.putExtra("title", trip_tv.text)
                    startActivity(i)
                }
                short_trip_view_rv.adapter = view_adapter
                view_adapter!!.setOnItemClickListener { v, data ->
                    val tv = v.findViewById(R.id.short_trip_view_item_tv) as TextView
                    val view = tv.text.toString()
                    val i = Intent()
                    i.setClass(this@ShortTrip, ShortTripView::class.java)
                    i.putExtra("view", view)
                    setResult(Activity.RESULT_OK, i)
                    finish()
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_short_trip)
//        province = findViewById(R.id.short_trip_province_rv) as RecyclerView
//        view_rv = findViewById(R.id.short_trip_view_rv) as RecyclerView
        trip_list = ArrayList<String>()
        province()
        view()


    }

    //省份RecyclerView的实现
    private fun province() {
        province_list = ArrayList<String>()

        for (i in 0..9) {
            province_list!!.add(i.toString() + "")
        }
        short_trip_province_rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        short_trip_province_rv.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.set(0, 0, 0, 2)

            }
        })
        province_adapter = ShortTripProvinceRVAdapter(this, province_list)
        short_trip_province_rv.adapter = province_adapter
    }

    //省份下的景点的RecyclerView的实现
    private fun view() {
        view_list = ArrayList<String>()
        HttpUtils.HttpUtilsConnection(url, object : HttpUtils.HttpCallBackListener {
            override fun onFinish(request: String) {
                val msg = Message()
                msg.obj = request.toString()
                msg.what = WHAT
                Log.e(TAG, msg.obj.toString())
                handler.sendMessage(msg)
            }

            override fun onError(e: Exception) {

            }
        })

        //        Button jiahao = (Button) findViewById(R.id.short_trip_view_item_insert);
        //        jiahao.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                Toast.makeText(ShortTrip.this, trip_tv.getText().toString(), Toast.LENGTH_SHORT).show();
        //            }
        //        });

    }

    //计划的recyclerview的实现


    private fun type(list: List<MainActivityRVModel>) {
        type_list = ArrayList<Type>()
        val type_pinyin = Type()
        for (i in list.indices) {
            val type = list[i].jd_type
            type_pinyin.type = type
            type_list.add(type_pinyin)
        }
    }


}
