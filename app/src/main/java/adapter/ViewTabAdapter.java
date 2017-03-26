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
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tf.travelbook.R;
import com.example.tf.travelbook.ViewInformation;

import java.util.List;

import model.ViewTabModel;

/**
 * Created by TF on 2017/2/24.
 */

public class ViewTabAdapter extends RecyclerView.Adapter {
    private List<ViewTabModel> list;
    private Context context;
    ViewTabAdapterListener listener;

    public static interface ViewTabAdapterListener {
        void OnItemClick(View v, String data);
    }

    public void setOnViewTabAdapterListener(ViewTabAdapterListener listener) {
        this.listener = listener;
    }

    public ViewTabAdapter(List<ViewTabModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title, time, information;
        private ImageView iv;

        public TextView getTitle() {
            return title;
        }

        public void setTitle(TextView title) {
            this.title = title;
        }

        public TextView getTime() {
            return time;
        }

        public void setTime(TextView time) {
            this.time = time;
        }

        public TextView getInformation() {
            return information;
        }

        public void setInformation(TextView information) {
            this.information = information;
        }

        public ImageView getIv() {
            return iv;
        }

        public void setIv(ImageView iv) {
            this.iv = iv;
        }

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.view_tab_fm_title);
            time = (TextView) itemView.findViewById(R.id.view_tab_fm_time);
            information = (TextView) itemView.findViewById(R.id.view_tab_fm_imformation);
            iv = (ImageView) itemView.findViewById(R.id.view_tab_fm_iv);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_tab_fm_item, null);
        final MyViewHolder vh = new MyViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnItemClick(v, (String) v.getTag());
            }
        });
        vh.getInformation().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("city", vh.getTitle().getText().toString());
                i.setClass(context, ViewInformation.class);
                context.startActivity(i);
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder vh = (MyViewHolder) holder;
        vh.getTitle().setText(list.get(position).getJd_name());
        vh.getInformation().setText("景点详情");
        //vh.getTime().setText();
        Glide.with(context).load(list.get(position).getJd_img()).into(vh.getIv());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

