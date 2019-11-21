package com.example.digitalevidence.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalevidence.Models.Model;
import com.example.digitalevidence.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CompactFragmentAdapter extends RecyclerView.Adapter<CompactFragmentAdapter.ViewHolder> {
    private static List<String> myList;

    public CompactFragmentAdapter(List<String> myList) {
        this.myList = myList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.compact_fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String url_brand = myList.get(position);
        holder.setBrand(url_brand);
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


        public void setBrand(String url) {
            Picasso.get().load(url).into(this.imageView);
        }
    }
}

