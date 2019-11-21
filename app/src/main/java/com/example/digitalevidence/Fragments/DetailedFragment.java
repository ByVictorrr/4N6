package com.example.digitalevidence.Fragments;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalevidence.Activities.BaseActivity;
import com.example.digitalevidence.Activities.MobileActivity;
import com.example.digitalevidence.Adapters.DetailedFragmentAdapter;
import com.example.digitalevidence.LazyLoaders.EndlessRecyclerViewScrollListener;
import com.example.digitalevidence.Models.Model;
import com.example.digitalevidence.R;

import java.util.ArrayList;
import java.util.List;

@TargetApi(23)
public class DetailedFragment extends Fragment{
    private static final String TAG = "Detailed";
    //======================================================\\
    private RecyclerView recyclerView;
    private DetailedFragmentAdapter detailedFragmentAdapter;
    private List<Model> models;
    private BaseActivity activity;
    private final int COLS = 3;


    public DetailedFragment() {
        // Required empty public constructor
    }
    public static DetailedFragment newInstance() {
        return new DetailedFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detailed, container, false);
        recyclerView = root.findViewById(R.id.RecyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), COLS);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        activity = (BaseActivity) getActivity();
        models = new ArrayList<>();
        // set for our main activity
        detailedFragmentAdapter = new DetailedFragmentAdapter(models);
        recyclerView.setAdapter(detailedFragmentAdapter);

        activity.setModels(models);

        activity.loadAndSet(6);
        EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener =  new EndlessRecyclerViewScrollListener(layoutManager){
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                activity.loadAndSet(6);
            }
        };

        recyclerView.addOnScrollListener(endlessRecyclerViewScrollListener);

        layoutManager.setMeasuredDimension(Integer.MAX_VALUE, Integer.MAX_VALUE);


        return root;
    }
}