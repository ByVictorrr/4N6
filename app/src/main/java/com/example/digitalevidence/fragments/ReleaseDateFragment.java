package com.example.digitalevidence.fragments;
import android.annotation.TargetApi;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.digitalevidence.R;
import com.example.digitalevidence.activities.MobileDevicesActivity;
import com.example.digitalevidence.adapters.ReleaseDateFragmentAdapter;
import com.example.digitalevidence.helpers.EndlessRecyclerViewScrollListener;
import com.example.digitalevidence.models.Device;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@TargetApi(23)
public class ReleaseDateFragment extends Fragment {
    private MobileDevicesActivity activity;
    private final int COLS = 1;

    public ReleaseDateFragment() {
        // Required empty public constructor
    }

    public static AlphabeticalFragment newInstance() {
        return new AlphabeticalFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @TargetApi(24)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detailed, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.RecyclerView);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), COLS);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        activity = (MobileDevicesActivity)getActivity();
        List<Device> devices = new ArrayList<>();
        Queue<Device> passed_devices = new LinkedList<>();
        try {
            for(Device d: activity.getBrand().getDevices()){
                passed_devices.add((Device)d.clone());

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        load_device(passed_devices, devices, 4);

        ReleaseDateFragmentAdapter releasedateFragmentAdapter = new ReleaseDateFragmentAdapter(devices);
        recyclerView.setAdapter(releasedateFragmentAdapter);

        EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener =  new EndlessRecyclerViewScrollListener(layoutManager){
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                load_device(passed_devices, devices, 4);
            }
        };

        recyclerView.addOnScrollListener(endlessRecyclerViewScrollListener);
        layoutManager.setMeasuredDimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
        return root;
    }

    void load_device(Queue<Device> q, List<Device> d, int load_num){
        int i = 0;
        while(i < load_num && !q.isEmpty()){
            d.add(q.poll());
        }
    }
}