package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tf.travelbook.R;
import com.example.tf.travelbook.TripPlan;

import java.util.List;

/**
 * Created by TF on 2017/2/16.
 */

public class TripPlanRVAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<String> list;
    private TripPlanRVAdapterListener tripplanlistener = null;

    public static interface TripPlanRVAdapterListener {
        void onItemClik(View v, String data);
    }

    public void setOnItemClickListener(TripPlanRVAdapterListener tripplanlistener) {
        this.tripplanlistener = tripplanlistener;
    }

    public TripPlanRVAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;


        public TextView getTv() {
            return tv;
        }

        public void setTv(TextView tv) {
            this.tv = tv;
        }

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.simple_rv_item_tv);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_rv_item, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = context.getResources().getDisplayMetrics().widthPixels / 4;
        layoutParams.width = layoutParams.height;
        final MyViewHolder vh = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tripplanlistener != null) {
                    tripplanlistener.onItemClik(view, (String) view.getTag());
                }
            }
        });
        //return new PassengerCapsuleViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder vh = (MyViewHolder) holder;
        vh.getTv().setText(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
