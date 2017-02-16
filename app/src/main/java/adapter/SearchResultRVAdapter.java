package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tf.travelbook.R;

import java.util.ArrayList;

import model.SearchResultRVModle;

/**
 * Created by TF on 2017/1/17.
 */

public class SearchResultRVAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<SearchResultRVModle> list;

    class MysvViewHolder extends RecyclerView.ViewHolder {
        private TextView title, adress;

        public TextView getTitle() {
            return title;
        }

        public TextView getAdress() {
            return adress;
        }

        private MysvViewHolder(View root) {
            super(root);
            title = (TextView) root.findViewById(R.id.searchresult_recycleview_item_title);
            adress = (TextView) root.findViewById(R.id.searchresult_recycleview_item_adress);
        }
    }

    public SearchResultRVAdapter(Context context, ArrayList<SearchResultRVModle> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MysvViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.searchresult_recycleview_item, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MysvViewHolder vh = (MysvViewHolder) holder;
        vh.getTitle().setText(list.get(position).getTitle());
        vh.getAdress().setText(list.get(position).getAdress());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
