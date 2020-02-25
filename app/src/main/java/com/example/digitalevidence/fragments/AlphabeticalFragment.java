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
import com.example.digitalevidence.activities.MobileActivity;
import com.example.digitalevidence.activities.MobileDevicesActivity;
import com.example.digitalevidence.adapters.AlphabeticalFragmentAdapter;
import com.example.digitalevidence.adapters.DetailedFragmentAdapter;
import com.example.digitalevidence.helpers.EndlessRecyclerViewScrollListener;
import com.example.digitalevidence.models.Device;
import com.example.digitalevidence.models.Manufacturer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@TargetApi(23)
public class AlphabeticalFragment extends Fragment {
    private MobileDevicesActivity activity;
    private final int COLS = 1;

    public AlphabeticalFragment() {
        // Required empty public constructor
    }

    public static AlphabeticalFragment newInstance() {
        return new AlphabeticalFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detailed, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.RecyclerView);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), COLS);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        activity = (MobileDevicesActivity) getActivity();
        List<Device> devices = new ArrayList<>();
        AlphabeticalFragmentAdapter alphabeticalFragmentAdapter = new AlphabeticalFragmentAdapter(devices);
        recyclerView.setAdapter(alphabeticalFragmentAdapter);

        //activity.setDevices(devices);
        //activity.LoadDevices();
        EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener =  new EndlessRecyclerViewScrollListener(layoutManager){
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                //activity.LoadDevices();
            }
        };

        recyclerView.addOnScrollListener(endlessRecyclerViewScrollListener);
        layoutManager.setMeasuredDimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
        return root;
    }
}