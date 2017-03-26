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

import com.example.tf.travelbook.CheckPlan;
import com.example.tf.travelbook.R;

import java.util.List;

/**
 * Created by TF on 2017/2/28.
 */

public class CheckPlanAdapter extends RecyclerView.Adapter {
    private List<String> list;
    private Context context;

    public CheckPlanAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView num, view;

        public MyViewHolder(View itemView) {
            super(itemView);
            num = (TextView) itemView.findViewById(R.id.checkplanitem_num);
            view = (TextView) itemView.findViewById(R.id.checkplanitem_view);
        }

        public TextView getNum() {
            return num;
        }

        public void setNum(TextView num) {
            this.num = num;
        }

        public TextView getView() {
            return view;
        }

        public void setView(TextView view) {
            this.view = view;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_plan_rv_item,null);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder vh = (MyViewHolder) holder;
        vh.getNum().setText("第" + position + "站");
        vh.getView().setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
