package com.example.digitalevidence.adapters;

import android.annotation.TargetApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalevidence.R;
import com.example.digitalevidence.models.Device;
import com.example.digitalevidence.models.Manufacturer;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.stream.Collectors;

public class AlphabeticalFragmentAdapter extends RecyclerView.Adapter<AlphabeticalFragmentAdapter.ViewHolder> {
    private List<Device> myList;

    public AlphabeticalFragmentAdapter(List<Device> myList) {
        this.myList = myList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.devicelist_fragment_item, parent, false);
        return new ViewHolder(view);
    }

    // Obtain list of devices within brand
    @TargetApi(24)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Manufacturer manufacturer = myList.get(position);
        //devices = manufacturer.getDevices().stream().collect(Collectors.toList());
        holder.setDevices(myList);
    }

    // Get number of devices within brand
    @Override
    public int getItemCount() {
        return myList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView0;
        private ImageView imageView;
        private TextView textView1;
        private TextView textView2;

        ViewHolder(View itemView) {
            super(itemView);
            textView0 = itemView.findViewById(R.id.textView0);
            imageView = itemView.findViewById(R.id.imageView);
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
        }

        private void setDevices(List<Device> list) {
            final String NAME = list.get(0).getName();
            final String MANUFACTURE = list.get(0).getManufacture();
            final String OS = list.get(0).getOs();

            if (list.size() > 2) {
                this.textView0.setText(NAME);
                Picasso.get().load(list.get(0).getImage()).into(this.imageView);
                this.textView1.append(MANUFACTURE);
                this.textView2.append(OS);
            }
        }
    }
}