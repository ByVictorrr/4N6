package com.example.digitalevidence.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalevidence.R;
import com.example.digitalevidence.models.Device;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReleaseDateFragmentAdapter extends RecyclerView.Adapter<ReleaseDateFragmentAdapter.ViewHolder> {
    private List<List<Device>> myList;

    public ReleaseDateFragmentAdapter(List<List<Device>> myList) {
        this.myList = myList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.devicelist_fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        List<Device> stringObject = myList.get(position);
        holder.setDevices(stringObject);
    }

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