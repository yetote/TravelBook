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

import model.DetaillsModel;

/**
 * Created by TF on 2017/5/14.
 */

public class DetaillsAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<DetaillsModel> list;
    private setOnItemClickListener listener;

    public DetaillsAdapter(Context context, ArrayList<DetaillsModel> list) {
        this.context = context;
        this.list = list;
    }

    private interface setOnItemClickListener {
        void onItemClick(View v, int data);
    }

    private void setOnItemClick(setOnItemClickListener listener) {
        this.listener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView nickname, time, content;

        public ImageView getIv() {
            return iv;
        }

        public void setIv(ImageView iv) {
            this.iv = iv;
        }

        public TextView getNickname() {
            return nickname;
        }

        public void setNickname(TextView nickname) {
            this.nickname = nickname;
        }

        public TextView getTime() {
            return time;
        }

        public void setTime(TextView time) {
            this.time = time;
        }

        public TextView getContent() {
            return content;
        }

        public void setContent(TextView content) {
            this.content = content;
        }

        public MyViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.detaills_item_iv);
            nickname = (TextView) itemView.findViewById(R.id.detaills_item_nickname);
            content = (TextView) itemView.findViewById(R.id.detaills_item_content);
            time = (TextView) itemView.findViewById(R.id.detaills_item_time);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.detaills_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
//        v.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onItemClick(v, (Integer) v.getTag());
//            }
//        });
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder vh = (MyViewHolder) holder;
        Glide.with(context).load(list.get(position).getImg()).into(vh.getIv());
        vh.getTime().setText(list.get(position).getDate());
        vh.getContent().setText(list.get(position).getContent());
        vh.getNickname().setText(list.get(position).getNickname());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
