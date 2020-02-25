package com.example.digitalevidence.fragments;
import android.annotation.TargetApi;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalevidence.R;
import com.example.digitalevidence.activities.MobileActivity;
import com.example.digitalevidence.adapters.CompactFragmentAdapter;
import com.example.digitalevidence.adapters.DetailedFragmentAdapter;
import com.example.digitalevidence.helpers.EndlessRecyclerViewScrollListener;
import com.example.digitalevidence.models.Manufacturer;
import com.example.digitalevidence.models.PageViewModel;
import com.google.android.gms.maps.MapFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CompactFragment extends Fragment {
    private static final String TAG = "Compact";
    private final int COLS = 3;
    private MobileActivity activity;

    public CompactFragment() {
        // Required empty public constructor
    }

    public static CompactFragment newInstance() {
        return new CompactFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PageViewModel pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        pageViewModel.setIndex(TAG);
    }
    @TargetApi(24)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_compact, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.RecyclerView);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), COLS);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


        activity = (MobileActivity) getActivity();

        List<Manufacturer> manufacturers  = activity.getManufacturers();


        activity.setManufacturers(manufacturers);
        activity.LoadBrands();

        CompactFragmentAdapter compactFragmentAdapter = new CompactFragmentAdapter(manufacturers);
        recyclerView.setAdapter(compactFragmentAdapter);

        EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                activity.LoadBrands();
            }
        };
        recyclerView.addOnScrollListener(endlessRecyclerViewScrollListener);
        layoutManager.setMeasuredDimension(Integer.MAX_VALUE, Integer.MAX_VALUE);

        return root;
    }
}