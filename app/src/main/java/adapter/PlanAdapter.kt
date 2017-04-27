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

package adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.tf.travelbook.R
import kotlinx.android.synthetic.main.plan_view_item.view.*
import model.PlanModelAnother

/**
 * Created by TF on 2017/2/23.
 */

class PlanAdapter(private val context: Context, private val list: MutableList<PlanModelAnother>) : RecyclerView.Adapter<PlanAdapter.MyViewHolder>() {
    override fun getItemCount(): Int {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder? {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val v = LayoutInflater.from(parent!!.context).inflate(R.layout.plan_view_item, parent, false)
        val vh = MyViewHolder(v)

//        Log.e("tag",v.tag.toString())
        v.setOnClickListener { v -> listener!!.onItemClick(v) }
        return vh
    }

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val vh = holder as MyViewHolder
        vh.itemView.plan_view_item_tv.setText(list[position].province)
        Glide.with(context).load(list[position].img).into(vh.itemView.plan_view_item_iv)
    }


    private var listener: setOnPlanAdapterClickListener? = null

    interface setOnPlanAdapterClickListener {
        fun onItemClick(v: View)
    }

    fun OnItemClickListener(listener: setOnPlanAdapterClickListener) {
        this.listener = listener
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


}
