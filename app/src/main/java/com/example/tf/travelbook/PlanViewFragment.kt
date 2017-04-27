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

import adapter.PlanAdapter
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.alibaba.fastjson.JSON
import model.PlanModelAnother
import utils.HttpUtils
import java.util.*

/**
 * Created by TF on 2017/2/23.
 */

class PlanViewFragment : Fragment() {
    private var list = ArrayList<PlanModelAnother>()
    private var adapter: PlanAdapter? = null
    var view_return: view_return? = null
    private var TAG = "[$javaClass]"
    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 1) {
                if (list.size != 0) else {
                    val list_1 = JSON.parseArray(msg.obj.toString(), PlanModelAnother::class.java)
                    list.addAll(list_1.map { it })
                    adapter!!.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        view_return = context as view_return

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val v = inflater.inflate(R.layout.plan_view_fragment, null)

        var rv: RecyclerView = v.findViewById(R.id.plan_view_fm_rv) as RecyclerView
        rv.layoutManager = GridLayoutManager(activity, 2)
        // rv.layoutManager = LinearLayoutManager(this.activity)
        val url = "http://123.206.60.236/travelbook/plan_province_sc.php"
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
        adapter = PlanAdapter(activity, list)
        (adapter as PlanAdapter).OnItemClickListener(object : PlanAdapter.setOnPlanAdapterClickListener {
            override fun onItemClick(v: View) {
                var tv: TextView = v.findViewById(R.id.plan_view_item_tv) as TextView
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                view_return!!.viewreturn(tv.text.toString())
                Log.e(TAG, tv.text.toString())
            }
        })
        rv.adapter = adapter
        return v
    }


}

interface view_return {
    fun viewreturn(view: String)
}