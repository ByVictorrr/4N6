package com.example.digitalevidence.LazyLoaders;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalevidence.Models.Model;
import com.example.digitalevidence.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private List<Model> myList;
    private static int counter = 0;

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
        final int ROWS = 2,
                COLS = 3;

        if(position != 0){

            counter = 0;
        }else{
            counter = position+6;
        }
        // TODO: FIX 
        Model model;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if(counter < myList.size()) {
                    model = myList.get(counter + i + j);
                    holder.setDevice(model, i, j);
                }
            }
        }
    }


    @Override
    public int getItemCount() {
        return myList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private final int LEFTMOST = 0
                , MIDDLE = 1
                , RIGHMOST = 2;

        private final int ROW0 = 0
                    , ROW1=1;

        private TextView [][]textViews = new TextView[2][3];
        private ImageView [][]imageViews = new ImageView[2][3];

        ViewHolder(View itemView) {
            super(itemView);


            //=====================ROW0====================================\\
            imageViews[ROW0][LEFTMOST] = itemView.findViewById(R.id.imageView0);
            textViews[ROW0][LEFTMOST] = itemView.findViewById(R.id.name0);

            imageViews[ROW0][MIDDLE] = itemView.findViewById(R.id.imageView1);
            textViews[ROW0][MIDDLE] = itemView.findViewById(R.id.name1);

            imageViews[ROW0][RIGHMOST] = itemView.findViewById(R.id.imageView2);
            textViews[ROW0][RIGHMOST] = itemView.findViewById(R.id.name2);
            //=====================ROW1====================================\\
            imageViews[ROW1][LEFTMOST] = itemView.findViewById(R.id.imageView3);
            textViews[ROW1][LEFTMOST] = itemView.findViewById(R.id.name3);
            imageViews[ROW1][MIDDLE] = itemView.findViewById(R.id.imageView4);
            textViews[ROW1][MIDDLE] = itemView.findViewById(R.id.name4);

            imageViews[ROW1][RIGHMOST] = itemView.findViewById(R.id.imageView5);
            textViews[ROW1][RIGHMOST] = itemView.findViewById(R.id.name5);


        }



        public void setDevice(Model model, int row, int col){
           textViews[row][col].setText(model.getName());
            Picasso.get().load(model.getLink()).into(imageViews[row][col]);
        }
    }
}
