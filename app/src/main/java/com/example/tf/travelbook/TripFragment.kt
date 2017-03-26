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

import adapter.TripRVAdapter
import android.app.AlertDialog
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.alibaba.fastjson.JSON
import model.PlanModel
import utils.HttpUtils
import utils.MyDecoration
import java.util.*

/**
 * Created by TF on 2017/2/9.
 */

class TripFragment : Fragment(), View.OnClickListener {


    private var longtrip_btn: Button? = null
    private var shorttrip_btn: Button? = null
    private var longtrip_insert: Button? = null
    private var shorttrip_insert: Button? = null
    private var shorttrip_rv: RecyclerView? = null
    private var longtrip_rv: RecyclerView? = null
    private var shorttrip_tv: TextView? = null
    private var longtrip_tv: TextView? = null
    private var list: MutableList<String>? = null
    internal var plan_list: MutableList<PlanModel> = mutableListOf()
    private var adapter: TripRVAdapter? = null

    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 1) {
                val p_list = JSON.parseArray(msg.obj.toString(), PlanModel::class.java)
                Log.e("TripJson", p_list.toString())
                for (model in p_list) {
                    plan_list.add(PlanModel(model.p_name, model.p_id, model.p_jd1, model.p_jd2, model.p_jd3, model.p_jd4, model.p_jd5, model.p_jd6, model.p_jd7, model.p_jd8, model.p_jd9, model.p_jd10, model.p_user))
                    list!!.add(model.p_name.toString())
                    Log.e("l", list!!.toString())
                }
                adapter!!.notifyDataSetChanged()
                adapter!!.setOnItemClickListener { v, data ->
                    val i = Intent()
                    i.setClass(activity, TripPlan::class.java)
                    var plan_name: TextView = v.findViewById(R.id.trip_fragment_rv_item_tv) as TextView
                    i.putExtra("plan_name", plan_name.text.toString())
                    startActivity(i)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var v: View = inflater.inflate(R.layout.trip_fragment, null)
        //var v=inflater.inflate(R.layout.plan_view_fragment,null)
        longtrip_btn = v.findViewById(R.id.trip_fragment_longtrip_btn) as Button
        shorttrip_btn = v.findViewById(R.id.trip_fragment_shorttrip_btn) as Button
        shorttrip_rv = v.findViewById(R.id.trip_fragment_shorttrip_rv) as RecyclerView
        longtrip_rv = v.findViewById(R.id.trip_fragment_longtrip_rv) as RecyclerView
        shorttrip_tv = v.findViewById(R.id.trip_fragment_shorttrip_tv) as TextView
        longtrip_tv = v.findViewById(R.id.trip_fragment_longtrip_tv) as TextView
        longtrip_insert = v.findViewById(R.id.trip_fragment_longtrip_insert) as Button
        shorttrip_insert = v.findViewById(R.id.trip_fragment_shorttrip_insert) as Button

        list = ArrayList<String>()
        plan_list = ArrayList<PlanModel>()

        val p_user = "admin"
        val url = "http://123.206.60.236/travelbook/jd_plan_sc.php?p_user=admin"

        HttpUtils.HttpUtilsConnection(url, object : HttpUtils.HttpCallBackListener {
            override fun onFinish(request: String) {
                val msg = Message()
                msg.what = 1
                msg.obj = request
                handler.sendMessage(msg)
            }

            override fun onError(e: Exception) {

            }
        })

        onclick()

        longtrip_rv!!.layoutManager = LinearLayoutManager(activity) as RecyclerView.LayoutManager?
        shorttrip_rv!!.layoutManager = LinearLayoutManager(activity)

        if (list!!.size == 0) {
            list!!.add("您还没有行程，请添加")
        }
        if (list!!.size != 0) {
            list!!.removeAt(0)
        }
        Log.e("list", list!!.toString())
        adapter = TripRVAdapter(list, activity)
        longtrip_rv!!.adapter = adapter
        shorttrip_rv!!.adapter = adapter
        shorttrip_rv!!.addItemDecoration(MyDecoration(activity,MyDecoration.VERTICAL_LIST))
        return v
    }

    fun onclick() {
        longtrip_btn!!.setOnClickListener(this)
        shorttrip_btn!!.setOnClickListener(this)
        shorttrip_tv!!.setOnClickListener(this)
        longtrip_tv!!.setOnClickListener(this)
        longtrip_insert!!.setOnClickListener(this)
        shorttrip_insert!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.trip_fragment_longtrip_btn -> {
                val ad = AlertDialog.Builder(activity)
                ad.setTitle("长途行程")
                ad.setMessage("长途行程是指您需要进行跨省旅行，需要购买火车票，飞机票等长途交通工具的行程(暂不支持跨国旅行)")
                ad.setPositiveButton("确定", null)
                ad.show()
            }
            R.id.trip_fragment_shorttrip_btn -> {
                val ad = AlertDialog.Builder(activity)
                ad.setTitle("短途行程")
                ad.setMessage("短途行程是指您在省内旅行的旅行计划，只需乘坐公交车、出租车或者步行即可到达目的地的旅行计划")
                ad.setPositiveButton("确定", null)
                ad.show()
            }
            R.id.trip_fragment_shorttrip_tv -> {
                if (shorttrip_rv!!.visibility == View.GONE) {
                    shorttrip_rv!!.visibility = View.VISIBLE
                    adapter!!.notifyDataSetChanged()
                    //Log.e("short", "可见")
                } else if (shorttrip_rv!!.visibility == View.VISIBLE) {
                    shorttrip_rv!!.visibility = View.GONE
                    adapter!!.notifyDataSetChanged()
                    //Log.e("short", "no可见")
                }
            }
            R.id.trip_fragment_longtrip_tv -> {
                if (longtrip_rv!!.visibility == View.INVISIBLE) {
                    longtrip_rv!!.visibility = View.VISIBLE
                    adapter!!.notifyDataSetChanged()
                    longtrip_rv!!.adapter = adapter
                    //Log.e("long", "可见")
                } else if (longtrip_rv!!.visibility == View.VISIBLE) {
                    longtrip_rv!!.visibility = View.INVISIBLE
                    adapter!!.notifyDataSetChanged()
                    longtrip_rv!!.adapter = adapter
                    //Log.e("long", "no可见")
                }
            }
            R.id.trip_fragment_longtrip_insert -> {
                val i = Intent()
                i.setClass(activity, Plan::class.java)
                startActivity(i)
            }
            R.id.trip_fragment_shorttrip_insert -> {
                val i = Intent()
                i.setClass(activity, Plan::class.java)
                startActivity(i)
            }
        }
    }
}
