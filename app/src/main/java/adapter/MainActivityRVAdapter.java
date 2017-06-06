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
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.tf.travelbook.R;
import java.util.ArrayList;

import model.MainActivityRVModel;

/**
 * Created by TF on 2017/1/15.
 */

public class MainActivityRVAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<MainActivityRVModel> list;
    private MainActivityRVListener mainlistener = null;

    public static interface MainActivityRVListener {
        void onItemClick(View v, String data);
    }

    public void setOnItemClickListener(MainActivityRVListener mainlistener) {
        this.mainlistener = mainlistener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView contnet;
        private ImageView iv;

        public TextView getTitle() {
            return title;
        }

        public TextView getContnet() {
            return contnet;
        }

        public ImageView getIv() {
            return iv;
        }

        private View root;

        public MyViewHolder(View root) {
            super(root);

            iv = (ImageView) root.findViewById(R.id.mainactivity_recycleview_item_iv);
            title = (TextView) root.findViewById(R.id.mainactivity_recycleview_item_title);
            contnet = (TextView) root.findViewById(R.id.mainactivity_recycleview_item_content);

        }
    }

    public MainActivityRVAdapter(Context context, ArrayList<MainActivityRVModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mainactivity_recycleview_item,null);
        MyViewHolder vh = new MyViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainlistener != null) {
                    mainlistener.onItemClick(v, (String) v.getTag());
                }
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder vh = (MyViewHolder) holder;
        //vh.getIv().setImageDrawable(list.get(position).getImg());
        vh.getContnet().setText(list.get(position).getJd_city());
        vh.getTitle().setText(list.get(position).getJd_name());
        Glide.with(context).load(list.get(position).getJd_img()).into(vh.getIv());
//        System.out.println(list.get(position).getJd_content());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


}
