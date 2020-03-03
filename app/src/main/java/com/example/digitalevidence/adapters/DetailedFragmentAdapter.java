package com.example.digitalevidence.adapters;
import android.annotation.TargetApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalevidence.helpers.ComparatorHelper;
import com.example.digitalevidence.helpers.OnButtonClickListener;
import com.example.digitalevidence.models.Manufacturer;
import com.example.digitalevidence.models.Device;
import com.example.digitalevidence.R;
import com.squareup.picasso.Picasso;
import java.util.List;
import java.util.stream.Collectors;

public class DetailedFragmentAdapter extends RecyclerView.Adapter<DetailedFragmentAdapter.ViewHolder> {
    private List<Manufacturer> myList;
    OnButtonClickListener listener;

    public DetailedFragmentAdapter(List<Manufacturer> myList, OnButtonClickListener listener) {
        this.myList = myList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detailed_fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @TargetApi(24)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Manufacturer manufacturer = myList.get(position);
        List<Device> devices = manufacturer.getDevices().stream().collect(Collectors.toList());
        holder.setDevices(manufacturer.getName(), devices);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Manufacturer manufacturer = myList.get(position);
                String selectedBrand = manufacturer.getName();
                listener.onButtonClick(selectedBrand);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private Button button;
        private ImageView imageView0;
        private ImageView imageView1;
        private ImageView imageView2;

        ViewHolder(View itemView) {
            super(itemView);

            button = itemView.findViewById(R.id.button);

            imageView0 = itemView.findViewById(R.id.imageView0);
            imageView1 = itemView.findViewById(R.id.imageView1);
            imageView2 = itemView.findViewById(R.id.imageView2);
        }

        @TargetApi(24)
        private void setDevices(String brand, List<Device> list) {
            final int LEFT = 0, MIDDLE = 1, RIGHT = 2;
            this.button.setText(brand);

            List<String> unique_images = ComparatorHelper.getDiffObjects(list).stream().map(d->d.getImage()).collect(Collectors.toList());
            int size = unique_images.size();

            if (size > 0) {
                Log.e("TEST", "ONE");
                Picasso.get().load(unique_images.get(0)).into(this.imageView0);

                if (size > 1) {
                    Log.e("TEST", "TWO");
                    Picasso.get().load(unique_images.get(1)).into(this.imageView1);

                    if (size > 2) {
                        Log.e("TEST", "THREE");
                        Picasso.get().load(unique_images.get(2)).into(this.imageView2);
                    }
                }
            }
            if (size > 2) {
                Picasso.get().load(unique_images.get(LEFT)).into(this.imageView0);
                Picasso.get().load(unique_images.get(MIDDLE)).into(this.imageView1);
                Picasso.get().load(unique_images.get(RIGHT)).into(this.imageView2);
            }
            else if (size > 1) {
                Picasso.get().load(unique_images.get(LEFT)).into(this.imageView0);
                Picasso.get().load(unique_images.get(MIDDLE)).into(this.imageView1);
                Picasso.get().load("@drawable/blank").into(this.imageView2);
            }
            else if (size > 0) {
                Picasso.get().load(unique_images.get(LEFT)).into(this.imageView0);
                Picasso.get().load("@drawable/blank").into(this.imageView1);
                Picasso.get().load("@drawable/blank").into(this.imageView2);
            }
            else {
                Log.e("TEST1", brand);
                Picasso.get().load("@drawable/blank").into(this.imageView0);
                Picasso.get().load("@drawable/blank").into(this.imageView1);
                Picasso.get().load("@drawable/blank").into(this.imageView2);
            }

            /*if (unique_images.size() > 2) {
                Picasso.get().load(unique_images.get(LEFT)).into(this.imageView0);
                Picasso.get().load(unique_images.get(MIDDLE)).into(this.imageView1);
                Picasso.get().load(unique_images.get(RIGHT)).into(this.imageView2);
            }
            else {
                Log.e("TEST", brand);
                Picasso.get().load(unique_images.get(RIGHT)).into(this.imageView0);
                Picasso.get().load(unique_images.get(MIDDLE)).into(this.imageView1);
                Picasso.get().load(unique_images.get(LEFT)).into(this.imageView2);
            }*/
        }
    }
}