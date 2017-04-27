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

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_basic_need.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class BasicNeed : AppCompatActivity(), View.OnClickListener {
    //    private var start_city: LinearLayout? = null
//    private var back_city: LinearLayout? = null
//    private var start_date: LinearLayout? = null
//    private var back_date: LinearLayout? = null
//    private var date_tv: TextView? = null
//    private var startcity_tv: TextView? = null
//    private var backcity_tv: TextView? = null
//    private var startdate_tv: TextView? = null
//    private var backdate_tv: TextView? = null
//    private var next: Button? = null
//    private val TAG = "BasicNeed"
    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 1) {
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_need)
        click()
        basic_need_next_btn.startAnimation()

    }

    internal fun click() {
        basic_need_start_ll.setOnClickListener(this)
        basic_need_back_ll.setOnClickListener(this)
        basic_need_startdate_ll.setOnClickListener(this)
        basic_need_backdate_ll.setOnClickListener(this)
        basic_need_next_btn.setOnClickListener(this)
    }

    internal fun setDate() {

        if (basic_need_start_city_choose.text.toString() != "请选择" && basic_need_finish_city_choose.text.toString() != "请选择") {
            val sdf1 = SimpleDateFormat("yyyy-MM-dd")
            try {
                val date1 = sdf1.parse(basic_need_back_date_choose.text.toString())
                val date2 = sdf1.parse(basic_need_start_date_choose.text.toString())
                val t2 = date1.time - date2.time
                val days = t2 / (1000 * 60 * 60 * 24)
                Log.e("[$javaClass]", "run: " + days + "天" + date1 + "date1" + date2 + "date2")
                basic_need_trip_time.text = (days + 1).toString() + "天"
            } catch (e: ParseException) {
                e.printStackTrace()
            }

        }
    }

    override fun onClick(v: View) {
        val i = Intent()
        when (v.id) {
            R.id.basic_need_start_ll -> {
                i.setClass(this, LongTripView::class.java)
                startActivityForResult(i, 1)
            }
            R.id.basic_need_back_ll -> {
                i.setClass(this, LongTripView::class.java)
                startActivityForResult(i, 2)
            }
            R.id.basic_need_startdate_ll -> {
                //使用Calendar类获取时间
                val calendar = Calendar.getInstance()
                //使用时间选择器
                val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    //设置startdate_tv的文字，month是0-11，所以要+1
                    val date = String.format("%d-%d-%d", year, month + 1, dayOfMonth)
                    basic_need_start_date_choose.text = date
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                val dp = datePickerDialog.datePicker
                val d = Date()
                val time = d.time
                //设置时间选择器的最小时间
                dp.minDate = time
                //Log.e(TAG, "onClick: " + calendar.getTime() + "    " + time);
                datePickerDialog.show()
            }
            R.id.basic_need_backdate_ll -> {
                val start_date = basic_need_start_date_choose.text.toString()
                val sdf = SimpleDateFormat("yyyy-MM-dd")
                try {
                    //将String类型的日期转换为date型
                    val date = sdf.parse(start_date)
                    //将转换的date型转换为long型
                    val t = date.time
                    Log.e("[$javaClass]", "onClick: " + date.date)
                    //                    long tdateMax = (t - (29 * 1000 * 24 * 60 * 60));
                    //                    Date dateMax = new Date(tdateMax);
                    //                    Log.e(TAG, "onClick: 11   " + sdf.format(dateMax));
                    //                    Log.e(TAG, "onClick: " + tdateMax + "t" + dateMax.getYear() + "年" + dateMax.getMonth() + "月" + +dateMax.getDay());
                    //Log.e(TAG, "onClick: t" + t + "   " + date);

                    //别问我为什么这么设置时间，我也不知道，反正就很<。)#)))≦（鱼）
                    val ca = Calendar.getInstance()
                    //在Java中，Data函数有问题，他的getYear方法是从1900年开始算起，getMonth是0-11而非1-12月
                    ca.set(Calendar.YEAR, date.year + 1900)
                    ca.set(Calendar.MONTH, date.month + 1)
                    ca.set(Calendar.DAY_OF_MONTH, date.date - 1)
                    Log.e("[$javaClass]", "onClick: 00" + ca.get(Calendar.YEAR) + ca.get(Calendar.MONTH) + ca.get(Calendar.DAY_OF_MONTH))
                    ca.add(Calendar.DAY_OF_MONTH, 29)
                    //                    Date maxTime = ca.getTime();
                    //                    TextView tv = new TextView(this);
                    //                    tv.setVisibility(View.GONE);
                    val maxTime = String.format("%d-%d-%d", ca.get(Calendar.YEAR), ca.get(Calendar.MONTH), ca.get(Calendar.DAY_OF_MONTH))
                    Log.e("[$javaClass]", "onClick: maxtime" + ca.get(Calendar.DATE))
                    val maxdate = sdf.parse(maxTime)
                    //将转换的date型转换为long型
                    val timeMax = maxdate.time
                    //long timeMax = maxTime.getTime();
                    Log.e("[$javaClass]", "onClick: " + maxdate)
                    Log.e("[$javaClass]", "onClick: " + ca.get(Calendar.YEAR) + " " + ca.get(Calendar.MONTH) + "  " + ca.get(Calendar.DAY_OF_MONTH))
                    val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        basic_need_back_date_choose.text = String.format("%d-%d-%d", year, month + 1, dayOfMonth)
                        setDate()
                    }, date.year, date.month, date.day)
                    val dp2 = dpd.datePicker
                    dp2.minDate = t
                    dp2.maxDate = timeMax
                    dpd.show()

                } catch (e: ParseException) {
                    e.printStackTrace()
                }

            }
            R.id.basic_need_next_btn ->
                if (basic_need_back_date_choose.text.toString() != "请选择" && basic_need_finish_city_choose.text.toString() != "请选择" && basic_need_start_date.text.toString() != "请选择" && basic_need_start_city_choose.text.toString() != "请选择") {
                    val getIntent = intent
                    getIntent.setClass(this, CheckPlan::class.java)
                    var plan_list: List<String> = getIntent.getStringArrayListExtra("plan_list")
                    getIntent.putStringArrayListExtra("plan_list", plan_list as ArrayList<String>)
                    val bundle = Bundle()
                    bundle.putString("start_city", basic_need_start_city_choose!!.text.toString())
                    bundle.putString("back_city", basic_need_finish_city_choose!!.text.toString())
                    bundle.putString("start_date", basic_need_start_date_choose!!.text.toString())
                    bundle.putString("back_date", basic_need_back_date_choose!!.text.toString())
                    getIntent.putExtra("bundle", bundle)
                    startActivity(getIntent)
                } else {
                    Toast.makeText(this, "请您完善信息", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("[$javaClass]", "onActivityResult: " + requestCode)
        when (requestCode) {
            1 -> if (resultCode == Activity.RESULT_OK) {
                basic_need_start_city_choose!!.text = data.getStringExtra("city")
                basic_need_finish_city_choose!!.text = data.getStringExtra("city")
            }
            2 -> if (resultCode == Activity.RESULT_OK) {
                basic_need_finish_city_choose!!.text = data.getStringExtra("city")
            }
        }
    }
}
