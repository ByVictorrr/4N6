package com.example.digitalevidence.adapters;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.digitalevidence.models.Model;
import com.example.digitalevidence.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailedFragmentAdapter extends RecyclerView.Adapter<DetailedFragmentAdapter.ViewHolder> {
    private List<Pair<String, List<Model>>> myList;

    public DetailedFragmentAdapter(List<Pair<String, List<Model>>> myList) {
        this.myList = myList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detailed_fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pair<String, List<Model>> stringListPair = myList.get(position);
        holder.setDevices(stringListPair.second);
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

        private void setDevices(List<Model> list) {
            final int LEFT = 0, MIDDLE = 1, RIGHT = 2;

            String brand = list.get(LEFT).getBrand();
            this.button.setText(brand);
            if (list.size() > 2) {
                Picasso.get().load(list.get(LEFT).getLink()).into(this.imageView0);
                Picasso.get().load(list.get(MIDDLE).getLink()).into(this.imageView1);
                Picasso.get().load(list.get(RIGHT).getLink()).into(this.imageView2);
            }
        }
    }
}