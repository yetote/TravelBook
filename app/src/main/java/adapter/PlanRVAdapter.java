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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tf.travelbook.R;

import java.util.List;

/**
 * Created by TF on 2017/2/16.
 */

public class PlanRVAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<String> list;

    public PlanRVAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    class PlanViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public TextView getTv() {
            return tv;
        }

        public void setTv(TextView tv) {
            this.tv = tv;
        }

        public PlanViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.short_trip_province_item_tv);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PlanViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.short_trip_province_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PlanViewHolder vh = (PlanViewHolder) holder;
        vh.getTv().setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
