package com.example.digitalevidence.adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalevidence.R;
import com.example.digitalevidence.helpers.OnButtonClickListener;
import com.example.digitalevidence.models.Manufacturer;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CompactFragmentAdapter extends RecyclerView.Adapter<CompactFragmentAdapter.ViewHolder> {
    private static List<String> myList;
    OnButtonClickListener listener;

    public CompactFragmentAdapter(List<String> myList, OnButtonClickListener listener) {
        this.myList = myList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.compact_fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String url_brand = myList.get(position);
        holder.setBrand(url_brand);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedBrand = myList.get(position);
                listener.onButtonClick(selectedBrand);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }

        private void setBrand(String url) {
            Picasso.get().load(url).into(this.imageView);
        }
    }
}