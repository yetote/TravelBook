package utils.swipecardview;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.tf.travelbook.MainActivity;

import java.util.ArrayList;
import java.util.List;

import adapter.MainActivityRVAdapter;
import model.MainActivityRVModel;

/**
 * Created by TF on 2017/4/6.
 */

public class SwipeCardCallback extends ItemTouchHelper.SimpleCallback {
    private RecyclerView mRv;
    private ArrayList<MainActivityRVModel> list;
    private MainActivityRVAdapter adapter;

    SwipeCardCallback(RecyclerView mRv, ArrayList<MainActivityRVModel> list, MainActivityRVAdapter adapter) {
        super(0, ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.LEFT | ItemTouchHelper.DOWN);
        this.adapter = adapter;
        this.list = list;
        this.mRv = mRv;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        list.remove(viewHolder.getAdapterPosition());
    }
    public float getThreashold() {
        return mRv.getWidth() * 0.5f;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

    }
}
