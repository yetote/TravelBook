
package com.example.tf.travelbook

import adapter.CheckPlanAdapter
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_check_plan.*
import model.JDModel
import utils.HttpUtils
import java.lang.Exception
import java.util.*

class CheckPlan : AppCompatActivity(), View.OnClickListener {
    private var list: MutableList<String>? = mutableListOf<String>()
    private var TAG: String = "[$javaClass]"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_plan)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        planinrv()
        next()
    }


    internal fun planinrv() {
        val i = intent
        list = i.getStringArrayListExtra("plan_list")

        var bundle: Bundle = i.getBundleExtra("bundle")
        var startCity: String? = bundle.getString("start_city")
        var backCity: String? = bundle.getString("back_city")
        var startData: String? = bundle.getString("start_date")
        var backData: String? = bundle.getString("back_date")

        checkplan_plandate_tv.text = startData + " -至- " + backData
        checkplan_planpeople_tv.text = startCity + " -至- " + backCity
        checkplan_tripplan_rv.layoutManager = LinearLayoutManager(this)
        checkplan_tripplan_rv.adapter = CheckPlanAdapter(this, list)
    }

    fun next() {
        checkplan_next_btn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        var ad: AlertDialog.Builder = AlertDialog.Builder(this)
        ad.setTitle("提示")
        var et: EditText = EditText(this)
        et.hint = "请输入行程名"
        ad.setView(et)
        var jdList: MutableList<String>? = mutableListOf()
        jdList!!.addAll(list!!.map { it })

        ad.setPositiveButton("确定", { _, _ ->
            if (et.text.isEmpty()) {
                Toast.makeText(this, "行程名不能为空", Toast.LENGTH_SHORT).show()
            } else {
                var name = et.text.toString()
                var user = MyAppliation.TEL
                var url = "http://123.206.60.236/travelbook/plan_insert.php?name=" + name + "&user=" + user
                for (i in 0 until list!!.size) {
                    var jd = "&jd" + (i + 1) + "=" + list!!.get(i)
                    url += jd
                }

                Log.e(TAG, ": " + url)
                HttpUtils.HttpUtilsConnection(url, object : HttpUtils.HttpCallBackListener {
                    override fun onFinish(request: String?) {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        var msg: Message = Message()
                        msg.what = 1
                        msg.obj = request
                        handler.sendMessage(msg)

                    }

                    override fun onError(e: Exception?) {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                })
            }
        })
        ad.show()


    }

    private var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 1) {
                Toast.makeText(this@CheckPlan, "添加成功", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

}

