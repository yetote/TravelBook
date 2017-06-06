
package com.example.tf.travelbook

import adapter.TripPlanRVAdapter
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.alibaba.fastjson.JSON
import com.bumptech.glide.Glide
import com.volokh.danylo.layoutmanager.LondonEyeLayoutManager
import com.volokh.danylo.layoutmanager.scroller.IScrollHandler
import com.volokh.danylo.utils.DebugRecyclerView
import model.PlanViewModel
import model.ViewImgModel
import model.WeatherModel
import model.weather.Forecast
import model.weather.Root
import utils.HttpUtils
import utils.WeatherUtils
import java.util.*

class TripPlan : AppCompatActivity() {
    private var plan_rv: DebugRecyclerView? = null
    private var plan_adapter: TripPlanRVAdapter? = null
    private var mLondonEyeLayoutManager: LondonEyeLayoutManager? = null
    private val view_list: MutableList<PlanViewModel> = mutableListOf()
    private var trip_plan_list: MutableList<String> = mutableListOf()
    private var view_img_list: MutableList<ViewImgModel> = mutableListOf()
    private var weatherin: MutableList<Forecast> = mutableListOf()
    private var iv: ImageView? = null
    internal var view_img: String? = null
    internal var url: String? = null
    private var view_city: String? = null
    private var TAG = "[$javaClass]"
    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 1) {

                val view_list = JSON.parseArray(msg.obj.toString(), PlanViewModel::class.java)
//
                trip_plan_list!!.addAll(view_list.map { it.p_jd1 })
                trip_plan_list!!.addAll(view_list.map { it.p_jd2 })
                trip_plan_list!!.addAll(view_list.map { it.p_jd3 })
                trip_plan_list!!.addAll(view_list.map { it.p_jd4 })
                trip_plan_list!!.addAll(view_list.map { it.p_jd5 })
                trip_plan_list!!.addAll(view_list.map { it.p_jd6 })
                trip_plan_list!!.addAll(view_list.map { it.p_jd7 })
                trip_plan_list!!.addAll(view_list.map { it.p_jd8 })
                trip_plan_list!!.addAll(view_list.map { it.p_jd9 })
                trip_plan_list!!.addAll(view_list.map { it.p_jd10 })

//                }

                plan_adapter!!.notifyDataSetChanged()
            }
            if (msg.what == 2) {
                view_img_list = JSON.parseArray(msg.obj.toString(), ViewImgModel::class.java)
                view_img = view_img_list.map { it.jd_img }.get(0)
                Glide.with(this@TripPlan).load(view_img).into(iv!!)
                view_city = view_img_list.map { it.jd_city }.get(0)
                Log.e(TAG, ": " + view_city)
//                var weatherUrl = "http://wthrcdn.etouch.cn/weather_mini?city=" + view_city;
//                HttpUtils.HttpUtilsConnection(weatherUrl, object : HttpUtils.HttpCallBackListener {
//                    override fun onFinish(request: String?) {
////                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                        var m: Message = Message()
//                        m.what = 3
//                        m.obj = request
//                        handleMessage(m)
//                    }
//
//                    override fun onError(e: java.lang.Exception?) {
////                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                    }
//
//                })
            }
            if (msg.what == 3) {
//                var r: Root = JSON.parseObject(msg.obj.toString(), Root::class.java)
//                weatherin.addAll(r.data.forecast)
//                Log.e(TAG, ": "+weatherin )

            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_plan)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        val i = intent
        val plan_name = i.getStringExtra("plan_name")

        view_img_list = ArrayList<ViewImgModel>()
        iv = findViewById(R.id.trip_plan_iv) as ImageView
        trip_plan_list = ArrayList<String>()
        url = "http://123.206.60.236/travelbook/trip_plan_sc.php?name=" + plan_name

        HttpUtils.HttpUtilsConnection(url, object : HttpUtils.HttpCallBackListener {
            override fun onFinish(request: String) {
                val msg = Message()
                msg.what = 1
                msg.obj = request.toString()

                handler.sendMessage(msg)
            }

            override fun onError(e: Exception) {

            }
        })
        plan_rv = findViewById(R.id.trip_plan_rv) as DebugRecyclerView

        val screenWidth = this.resources.displayMetrics.widthPixels
        val screenHeight = this.resources.displayMetrics.heightPixels

        plan_adapter = TripPlanRVAdapter(this, trip_plan_list)
        val circleRadius = screenWidth

        val xOrigin = -200
        val yOrigin = 0
        // mRecyclerView = (DebugRecyclerView) rootView.findViewById(R.id.recycler_view);
        plan_rv!!.setParameters(circleRadius, xOrigin, yOrigin)
        //            // use this setting to improve performance if you know that changes
        //            // in content do not change the layout size of the RecyclerView
        plan_rv!!.setHasFixedSize(true)
        mLondonEyeLayoutManager = LondonEyeLayoutManager(
                circleRadius,
                xOrigin,
                yOrigin,
                plan_rv,
                IScrollHandler.Strategy.NATURAL)
        plan_rv!!.layoutManager = mLondonEyeLayoutManager
        plan_rv!!.adapter = plan_adapter
        plan_adapter!!.setOnItemClickListener { v, data ->
            val tv = v.findViewById(R.id.simple_rv_item_tv) as TextView
            val view_name = tv.text.toString()
            val url2 = "http://123.206.60.236/travelbook/view_url.php?name=" + view_name
            Log.e(TAG, ": "+url2 );
            HttpUtils.HttpUtilsConnection(url2, object : HttpUtils.HttpCallBackListener {
                override fun onFinish(request: String) {
                    val msg = Message()
                    msg.what = 2
                    msg.obj = request.toString()
                    handler.sendMessage(msg)

                }

                override fun onError(e: Exception) {
                }
            })

        }
    }
}
