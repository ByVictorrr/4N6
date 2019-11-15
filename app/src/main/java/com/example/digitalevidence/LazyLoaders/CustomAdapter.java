package  com.example.digitalevidence.LazyLoaders;

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
        Model model = myList.get(position);
        holder.setDevice(model);
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView image;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.image);
        }

        public void setDevice(Model mobileDO) {
            name.setText(mobileDO.getName());
            Picasso.get().load(mobileDO.getLink()).into(image);
        }
        public void setName(String name){
            this.name.setText(name);
        }

    }
}
