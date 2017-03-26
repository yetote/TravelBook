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
import android.widget.Button;
import android.widget.TextView;

import com.example.tf.travelbook.R;
import com.example.tf.travelbook.ViewTabFragment;

import java.util.List;

/**
 * Created by TF on 2017/2/25.
 */

public class PlanAdapterTwo extends RecyclerView.Adapter {
    private List<String> list;
    private Context context;
    private list_size list_size;

    public PlanAdapterTwo(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    public void List_size(list_size list_size) {
        this.list_size = list_size;
        Log.e("listsize", "List_size: " + list_size);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;
        private Button btn;

        public TextView getTv() {
            return tv;
        }

        public void setTv(TextView tv) {
            this.tv = tv;
        }

        public Button getBtn() {
            return btn;
        }

        public void setBtn(Button btn) {
            this.btn = btn;
        }

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.plan_rv_item_tv);
            btn = (Button) itemView.findViewById(R.id.plan_rv_item_delete);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_rv_item, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder vh = (MyViewHolder) holder;
        vh.getTv().setText(list.get(position));
        list_size.list_size(list);
//        List_size(new list_size() {
//            @Override
//            public void list_size(List<String> list) {
//               list_size(list);
//            }
//        });
        Log.e("listsize", "onBindViewHolder: " + list.size());
        final ViewTabFragment vm = new ViewTabFragment();
        vh.getBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //单击按钮删除Recyclerview的Item
                list.remove(position);
                notifyItemRemoved(position);
//                if(position != list.size()){ // 如果移除的是最后一个，忽略
                notifyItemRangeChanged(position, getItemCount());
                //}
                list_size.list_size(list);
                Log.e("listsize", "onClick: " + list.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface list_size {
        void list_size(List<String> list);
    }

}
