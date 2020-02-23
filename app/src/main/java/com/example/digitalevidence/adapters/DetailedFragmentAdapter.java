package com.example.digitalevidence.adapters;
import android.annotation.TargetApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalevidence.models.Manufacturer;
import com.example.digitalevidence.models.Device;
import com.example.digitalevidence.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.stream.Collectors;

public class DetailedFragmentAdapter extends RecyclerView.Adapter<DetailedFragmentAdapter.ViewHolder> {
    private List<Manufacturer> myList;

    public DetailedFragmentAdapter(List<Manufacturer> myList) {
        this.myList = myList;
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
        holder.setDevices(manufacturer.getName(),devices);
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

        private void setDevices(String brand, List<Device> list) {
            final int LEFT = 0, MIDDLE = 1, RIGHT = 2;

            this.button.setText(brand);

            if (list.size() > 2) {
                Picasso.get().load(list.get(LEFT).getImage()).into(this.imageView0);
                Picasso.get().load(list.get(MIDDLE).getImage()).into(this.imageView1);
                Picasso.get().load(list.get(RIGHT).getImage()).into(this.imageView2);
            }else{
                Picasso.get().load(list.get(LEFT).getImage()).into(this.imageView0);
                Picasso.get().load(list.get(LEFT).getImage()).into(this.imageView1);
                Picasso.get().load(list.get(LEFT).getImage()).into(this.imageView2);
            }
        }
    }
}