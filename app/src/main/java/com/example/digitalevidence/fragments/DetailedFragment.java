package com.example.digitalevidence.fragments;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.digitalevidence.helpers.OnButtonClickListener;
import com.example.digitalevidence.R;
import com.example.digitalevidence.activities.MobileActivity;
import com.example.digitalevidence.activities.MobileDevicesActivity;
import com.example.digitalevidence.adapters.DetailedFragmentAdapter;
import com.example.digitalevidence.models.Manufacturer;
import com.example.digitalevidence.helpers.EndlessRecyclerViewScrollListener;
import java.util.ArrayList;
import java.util.List;

@TargetApi(23)
public class DetailedFragment extends Fragment {
    private MobileActivity activity;
    private final int COLS = 1;

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
        RecyclerView recyclerView = root.findViewById(R.id.RecyclerView);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), COLS);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


        OnButtonClickListener listener = new OnButtonClickListener() {
            @Override
            public void onButtonClick(String selectedBrand) {
                Log.e("TEST", selectedBrand);
                Toast.makeText(getActivity(), "Got: " + selectedBrand, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), MobileDevicesActivity.class);
                i.putExtra("SELECTEDBRAND", selectedBrand);
                startActivity(i);
            }
        };


        activity=(MobileActivity)getActivity();
        List<Manufacturer> manufacturers  = activity.getManufacturers();


        DetailedFragmentAdapter detailedFragmentAdapter = new DetailedFragmentAdapter(manufacturers, listener);
        recyclerView.setAdapter(detailedFragmentAdapter);

        activity.LoadBrands();
        EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener =  new EndlessRecyclerViewScrollListener(layoutManager){
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