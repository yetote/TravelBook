package com.volokh.danylo.sample_usage;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.volokh.danylo.layoutmanager.LondonEyeLayoutManager;
import com.volokh.danylo.layoutmanager.scroller.IScrollHandler;
import com.volokh.danylo.sample_usage.adapter.LondonEyeListAdapter;
import com.volokh.danylo.utils.DebugRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        List<String> mList = new ArrayList<>(Arrays.asList(
                "Passenger Cabin 1",
                "Passenger Cabin 2",
                "Passenger Cabin 3",
                "Passenger Cabin 4",
                "Passenger Cabin 5",
                "Passenger Cabin 6",
                "Passenger Cabin 7",
                "Passenger Cabin 8",
                "Passenger Cabin 9",
                "Passenger Cabin 10",
                "Passenger Cabin 11",
                "Passenger Cabin 12",
                "Passenger Cabin 13",
                "Passenger Cabin 14",
                "Passenger Cabin 15",
                "Passenger Cabin 16",
                "Passenger Cabin 17",
                "Passenger Cabin 18",
                "Passenger Cabin 19",
                "Passenger Cabin 20",
                "Passenger Cabin 21",
                "Passenger Cabin 22",
                "Passenger Cabin 23",
                "Passenger Cabin 24",
                "Passenger Cabin 25",
                "Passenger Cabin 26",
                "Passenger Cabin 27",
                "Passenger Cabin 28",
                "Passenger Cabin 29",
                "Passenger Cabin 30",
                "Passenger Cabin 31",
                "Passenger Cabin 32",
                "Passenger Cabin 33",
                "Passenger Cabin 34",
                "Passenger Cabin 35",
                "Passenger Cabin 36",
                "Passenger Cabin 37",
                "Passenger Cabin 38",
                "Passenger Cabin 39",
                "Passenger Cabin 40",
                "Passenger Cabin 41",
                "Passenger Cabin 42",
                "Passenger Cabin 43",
                "Passenger Cabin 44",
                "Passenger Cabin 45",
                "Passenger Cabin 46",
                "Passenger Cabin 47",
                "Passenger Cabin 48",
                "Passenger Cabin 49",
                "Passenger Cabin 50"));

        private DebugRecyclerView mRecyclerView;

        private LondonEyeLayoutManager mLondonEyeLayoutManager;

        private LondonEyeListAdapter mVideoRecyclerViewAdapter;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            int screenWidth = getActivity().getResources().getDisplayMetrics().widthPixels;
            int screenHeight = getActivity().getResources().getDisplayMetrics().heightPixels;

//            int circleRadius = screenHeight*2 + screenWidth/2;
//
//            int xOrigin = -screenHeight*2 + screenWidth/4;
            int circleRadius = screenWidth;

            int xOrigin = -200;
            int yOrigin = 0;
            mRecyclerView = (DebugRecyclerView) rootView.findViewById(R.id.recycler_view);
            mRecyclerView.setParameters(circleRadius, xOrigin, yOrigin);

//
//            // use this setting to improve performance if you know that changes
//            // in content do not change the layout size of the RecyclerView
            mRecyclerView.setHasFixedSize(true);

            mLondonEyeLayoutManager = new LondonEyeLayoutManager(
                    circleRadius,
                    xOrigin,
                    yOrigin,
                    mRecyclerView,
                    IScrollHandler.Strategy.NATURAL);

            mRecyclerView.setLayoutManager(mLondonEyeLayoutManager);//new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

            mVideoRecyclerViewAdapter = new LondonEyeListAdapter(getActivity(), mList);
            mRecyclerView.setAdapter(mVideoRecyclerViewAdapter);

            return rootView;
        }
    }
}
