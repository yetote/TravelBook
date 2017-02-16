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

/**
 * Created by TF on 2017/2/10.
 */

public class TripRVAdapter extends RecyclerView.Adapter {
    private List<String> list;
    private Context context;
    String TAG = "TripRVAdapter";
    private TripAdapaterListener listener=null;

    public static interface TripAdapaterListener {
        void onItemClick(View v, String data);
    }

    public void setOnItemClickListener(TripAdapaterListener listener) {
        this.listener = listener;
    }

    public TripRVAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    class MyTripViewHolder extends RecyclerView.ViewHolder {
        private TextView trip_name;

        public TextView getTrip_name() {
            return trip_name;
        }

        public void setTrip_name(TextView trip_name) {
            this.trip_name = trip_name;
        }

        private MyTripViewHolder(View v) {
            super(v);
            trip_name = (TextView) v.findViewById(R.id.trip_fragment_rv_item_tv);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_fragment_rv_item, parent, false);
        MyTripViewHolder vh = new MyTripViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(v, (String) v.getTag());
                }
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyTripViewHolder vh = (MyTripViewHolder) holder;
        vh.getTrip_name().setText(list.get(position));
        Log.e(TAG, list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
