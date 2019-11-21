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

public class DetailedFragmentAdapter extends RecyclerView.Adapter<DetailedFragmentAdapter.ViewHolder> {
    private static List<Model> myList;

    public DetailedFragmentAdapter(List<Model> myList) {
        this.myList = myList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detailed_fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Model model = myList.get(position);
        holder.setDevices(model);
    }


    @Override
    public int getItemCount() {
        return myList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {


        private TextView textView;
        private ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);

        }


        public void setDevices(Model model) {
            this.textView.setText(model.getName());
            Picasso.get().load(model.getLink()).into(this.imageView);
        }
    }
}
