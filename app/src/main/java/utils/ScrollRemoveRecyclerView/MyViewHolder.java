package utils.ScrollRemoveRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tf.travelbook.R;

/**
 * Created by TF on 2017/5/26.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView tv;
    public Button btn;
    public LinearLayout layout;

    public LinearLayout getLayout() {
        return layout;
    }

    public void setLayout(LinearLayout layout) {
        this.layout = layout;
    }

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
        tv = (TextView) itemView.findViewById(R.id.trip_fragment_rv_item_tv);
        btn = (Button) itemView.findViewById(R.id.trip_fragment_rv_item_btn);
        layout = (LinearLayout) itemView.findViewById(R.id.trip_fragment_rv_item_layout);
    }

}
