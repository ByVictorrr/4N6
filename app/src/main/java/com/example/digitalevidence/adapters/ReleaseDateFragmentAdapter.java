package com.example.digitalevidence.adapters;
import android.annotation.TargetApi;
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
    List<Device> myList;

    public ReleaseDateFragmentAdapter(List<Device> myList) {
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
        holder.setDevices(myList, position);
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

        private void setDevices(List<Device> list, int position) {
            final String NAME = list.get(position).getName();
            final String MANUFACTURE = "Release Date: " + list.get(position).getManufacture();
            final String OS = "Dimensions: " + list.get(position).getOs();

            if (list.size() > 2) {
                this.textView0.setText(NAME);
                Picasso.get().load(list.get(position).getImage()).into(this.imageView);
                this.textView1.setText(MANUFACTURE);
                this.textView2.setText(OS);
            }
        }
    }
}