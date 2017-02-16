package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tf.travelbook.MainActivity;
import com.example.tf.travelbook.NearView;
import com.example.tf.travelbook.R;
import com.example.tf.travelbook.ShortTrip;

import java.util.ArrayList;
import java.util.List;

import model.MainActivityRVModel;

/**
 * Created by TF on 2017/2/12.
 */

public class ShortTripViewRVAdapter extends RecyclerView.Adapter {
    private Context context;
    final List<String> list_p = new ArrayList<>();
    private List<String> list;
    private ShortTripViewRVAdapterListener listener = null;

    public static interface ShortTripViewRVAdapterListener {
        void OnItemClick(View v, String data);
    }

    public void setOnItemClickListener(ShortTripViewRVAdapterListener listener) {
        this.listener = listener;
    }

    public ShortTripViewRVAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;
        private Button btn;

        public Button getBtn() {
            return btn;
        }

        public void setBtn(Button btn) {
            this.btn = btn;
        }

        public TextView getTv() {
            return tv;
        }

        public void setTv(TextView tv) {
            this.tv = tv;
        }

        public MyViewHolder(View itemView) {
            super(itemView);
            btn = (Button) itemView.findViewById(R.id.short_trip_view_item_insert);
            tv = (TextView) itemView.findViewById(R.id.short_trip_view_item_tv);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.short_trip_view_item, null);
        MyViewHolder vh = new MyViewHolder(v);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.OnItemClick(v, (String) v.getTag());
                }
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MyViewHolder vh = (MyViewHolder) holder;
        vh.getTv().setText(list.get(position));
        Log.e("TAG", list.get(position));

        final ShortTrip st = new ShortTrip();

        vh.getBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, vh.getTv().getText(), Toast.LENGTH_SHORT).show();
                list_p.add(vh.getTv().getText().toString());
                //st.plan(vh.getTv().getText().toString());
                // Log.e("adapter", list_p.size()+"");
                st.plan_list(list_p);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
