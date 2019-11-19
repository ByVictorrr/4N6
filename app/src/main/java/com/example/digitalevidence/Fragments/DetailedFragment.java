package com.example.digitalevidence.Fragments;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalevidence.Activities.BaseActivity;
import com.example.digitalevidence.Activities.MobileActivity;
import com.example.digitalevidence.LazyLoaders.CustomAdapter;
import com.example.digitalevidence.LazyLoaders.EndlessRecyclerViewScrollListener;
import com.example.digitalevidence.Models.Model;
import com.example.digitalevidence.Models.PageViewModel;
import com.example.digitalevidence.R;
import com.example.digitalevidence.databinding.ActivityMobileBinding;

import java.util.ArrayList;
import java.util.List;

@TargetApi(23)
public class DetailedFragment extends Fragment{
    private static final String TAG = "Detailed";
    //======================================================\\
    private RecyclerView recyclerView;
    private CustomAdapter customAdapter;
    private List<Model> models;
    private BaseActivity activity = (MobileActivity)getActivity();


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

        MobileActivity mobileActivity = (MobileActivity)getActivity();

        recyclerView = root.findViewById(R.id.RecyclerView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        models = new ArrayList<>();
        // set for our main activity
        customAdapter = new CustomAdapter(models);
        recyclerView.setAdapter(customAdapter);
        mobileActivity.setModels(models);



        mobileActivity.loadAndSet(6);
        recyclerView.addOnScrollListener( new EndlessRecyclerViewScrollListener(layoutManager){
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mobileActivity.loadAndSet(6);
            }
        });

        return root;
    }
}