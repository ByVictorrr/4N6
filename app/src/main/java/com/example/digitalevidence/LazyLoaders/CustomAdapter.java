package com.example.digitalevidence.LazyLoaders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalevidence.Models.Model;
import com.example.digitalevidence.R;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private List<Model> myList;

    public CustomAdapter(List<Model> myList) {
        this.myList = myList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Model left = myList.get(position);
        Model middle = myList.get(position+1);
        Model right = myList.get(position+2);
        holder.setLMDevice(left);
        holder.setMDevice(middle);
        holder.setRMDevice(right);
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private final int LEFTMOST = 0
                , MIDDLE = 1
                , RIGHMOST = 2;

        private List<TextView> textViews = new ArrayList<>();
        private List<ImageView> imageViews = new ArrayList<>();

        ViewHolder(View itemView) {
            super(itemView);
            textViews.add(itemView.findViewById(R.id.name0));
            imageViews.add(itemView.findViewById(R.id.imageView0));
            textViews.add(itemView.findViewById(R.id.name1));
            imageViews.add(itemView.findViewById(R.id.imageView1));
            textViews.add(itemView.findViewById(R.id.name2));
            imageViews.add(itemView.findViewById(R.id.imageView2));

        }


        public void setLMDevice(Model mobileDO) {
            textViews.get(LEFTMOST).setText(mobileDO.getName());
            Picasso.get().load(mobileDO.getLink()).into(imageViews.get(LEFTMOST));
        }

        public void setMDevice(Model mobileDO) {
            textViews.get(MIDDLE).setText(mobileDO.getName());
            Picasso.get().load(mobileDO.getLink()).into(imageViews.get(MIDDLE));
        }
        public void setRMDevice(Model mobileDO) {
            textViews.get(RIGHMOST).setText(mobileDO.getName());
            Picasso.get().load(mobileDO.getLink()).into(imageViews.get(RIGHMOST));
        }

        /*
        public void setName(String name){
            this.name[0].setText(name);
        }

         */


    }
}
