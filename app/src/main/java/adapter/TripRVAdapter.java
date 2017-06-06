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
package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tf.travelbook.R;

import java.util.List;

import model.PlanModel;
import model.PlanNameModel;
import utils.ScrollRemoveRecyclerView.MyRv;
import utils.ScrollRemoveRecyclerView.MyViewHolder;

/**
 * Created by TF on 2017/2/10.
 */

public class TripRVAdapter extends RecyclerView.Adapter {
    private List<PlanNameModel> list;
    private Context context;
    String TAG = "TripRVAdapter";
//    private TripAdapaterListener listener=null;

//    public static interface TripAdapaterListener {
//        void onItemClick(View v, String data);
//    }
//
//    public void setOnItemClickListener(TripAdapaterListener listener) {
//        this.listener = listener;
//    }

    public TripRVAdapter(List<PlanNameModel> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_fragment_rv_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
//        v.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (listener != null) {
//                    listener.onItemClick(v, (String) v.getTag());
//                }
//            }
//        });
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder vh = (MyViewHolder) holder;
        vh.tv.setText(list.get(position).getName());
        vh.itemView.setTag(list.get(position).getId());
//        Log.e(TAG, list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyDataSetChanged();
    }
}
