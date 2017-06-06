package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tf.travelbook.R;

import java.util.ArrayList;

import model.FriendCircleModel;

/**
 * Created by TF on 2017/5/14.
 */

public class FriendCircleAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<FriendCircleModel> list;
    private setOnClickListener listener;
    private static final String TAG = "FriendCircleAdapter";

    public FriendCircleAdapter(Context context, ArrayList<FriendCircleModel> list) {
        this.context = context;
        this.list = list;
    }

    public interface setOnClickListener {
        void OnItemClick(View v, int data);
    }

    public void adapterListener(setOnClickListener listener) {
        this.listener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView headIv, iv;
        TextView name, content, time;
        Button hobby, comment;

        public TextView getTime() {
            return time;
        }

        public void setTime(TextView time) {
            this.time = time;
        }

        public ImageView getHeadIv() {
            return headIv;
        }

        public void setHeadIv(ImageView headIv) {
            this.headIv = headIv;
        }

        public ImageView getIv() {
            return iv;
        }

        public void setIv(ImageView iv) {
            this.iv = iv;
        }

        public TextView getName() {
            return name;
        }

        public void setName(TextView name) {
            this.name = name;
        }

        public TextView getContent() {
            return content;
        }

        public void setContent(TextView content) {
            this.content = content;
        }

        public Button getHobby() {
            return hobby;
        }

        public void setHobby(Button hobby) {
            this.hobby = hobby;
        }

        public Button getComment() {
            return comment;
        }

        public void setComment(Button comment) {
            this.comment = comment;
        }

        public MyViewHolder(View itemView) {
            super(itemView);
            headIv = (ImageView) itemView.findViewById(R.id.friend_item_headiv);
            iv = (ImageView) itemView.findViewById(R.id.friend_item_iv);
            name = (TextView) itemView.findViewById(R.id.friend_item_name);
            content = (TextView) itemView.findViewById(R.id.friend_item_content);
            hobby = (Button) itemView.findViewById(R.id.friend_item_hobby);
            comment = (Button) itemView.findViewById(R.id.friend_item_comment);
            time = (TextView) itemView.findViewById(R.id.friend_item_time);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.friend_circle_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnItemClick(v, (Integer) v.getTag());
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder vh = (MyViewHolder) holder;
        AlphaAnimation aa=new AlphaAnimation(0,1);
        aa.setDuration(500);
        aa.setStartOffset(500*position);
        vh.itemView.setAnimation(aa);
        Glide.with(context).load(list.get(position).getImg()).into(vh.getHeadIv());
        Glide.with(context).load(list.get(position).getImage()).into(vh.getIv());
        vh.getName().setText(list.get(position).getNickname());
        vh.getContent().setText(list.get(position).getContent());
        vh.getHobby().setText("赞 " + list.get(position).getHobby());
        vh.getComment().setText("评论 " + list.get(position).getComment() + "");
        vh.getTime().setText(list.get(position).getTime());
        Log.e(TAG, "onBindViewHolder: " + list.get(position).getImage());
        vh.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
