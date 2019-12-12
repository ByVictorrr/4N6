package com.example.digitalevidence.fragments;

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
import com.example.digitalevidence.adapters.CompactFragmentAdapter;
import com.example.digitalevidence.helpers.EndlessRecyclerViewScrollListener;
import com.example.digitalevidence.models.PageViewModel;

import java.util.Arrays;
import java.util.List;

public class ReleaseDateFragment extends Fragment {
    private static final String TAG = "Compact";
    private final List<String> BRANDS = Arrays.asList(
            "https://digitalforensics-userfiles-mobilehub-1666815180.s3.us-east-2.amazonaws.com/public/Mobile/Brands/apple.png",
            "https://digitalforensics-userfiles-mobilehub-1666815180.s3.us-east-2.amazonaws.com/public/Mobile/Brands/Samsung_Logo.png",
            "https://digitalforensics-userfiles-mobilehub-1666815180.s3.us-east-2.amazonaws.com/public/Mobile/Brands/htc_logo.png",
            "https://digitalforensics-userfiles-mobilehub-1666815180.s3.us-east-2.amazonaws.com/public/Mobile/Brands/oneplus.jpg",
            "https://1000logos.net/wp-content/uploads/2017/03/LG-Symbol.jpg",
            "https://upload.wikimedia.org/wikipedia/en/thumb/0/04/Huawei_Standard_logo.svg/1008px-Huawei_Standard_logo.svg.png",
            "https://o.aolcdn.com/images/dims?quality=85&image_uri=http%3A%2F%2Fwww.blogcdn.com%2Fwww.engadget.com%2Fmedia%2F2010%2F12%2F10x1210ibn534moto.jpg&client=amp-blogside-v2&signature=a3c63194a3cec037f2615a2d981aa77994adec94",
            "https://datatransmission.co/radio/wp-content/uploads/sites/2/2019/09/sony-logo.png",
            "https://digitalforensics-userfiles-mobilehub-1666815180.s3.us-east-2.amazonaws.com/public/Mobile/Brands/nokia.png"
    );

    public ReleaseDateFragment() {
        // Required empty public constructor
    }

    public static ReleaseDateFragment newInstance() {
        return new ReleaseDateFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PageViewModel pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        pageViewModel.setIndex(TAG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_compact, container, false);
        //final TextView textView = root.findViewById(R.id.section_label);
        final int COLS = 3;

        RecyclerView recyclerView = root.findViewById(R.id.RecyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), COLS);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);

        CompactFragmentAdapter compactFragmentAdapter = new CompactFragmentAdapter(BRANDS);

        recyclerView.setAdapter(compactFragmentAdapter);

        EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
            }
        };

        return root;
    }
}