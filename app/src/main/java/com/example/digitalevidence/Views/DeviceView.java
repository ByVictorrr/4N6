package com.example.digitalevidence.Views;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.digitalevidence.Models.MobileDO;
import com.example.digitalevidence.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class DeviceView extends RelativeLayout{

    private View rootView;
    private TextView name;
    private ImageView image;


    private void init(Context context) {
        rootView = inflate(context, R.layout.view_device_info_layout, this);
        name = rootView.findViewById(R.id.name);
        image = rootView.findViewById(R.id.image);
    }

    public DeviceView(Context context) {
            super(context);
            init(context);
    }


    public DeviceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public void setDevice(MobileDO mobileDO) {
        name.setText(mobileDO.getName());
        Picasso.get().load(mobileDO.getLink()).into(image);
    }
    public ImageView getImageView(){return image;}
    public TextView getTextView(){return name;}

    public void setName(String name){
        this.name.setText(name);
    }

    public void setImage(String url){
        Handler uiHandler = new Handler(Looper.getMainLooper());
        uiHandler.post(new Runnable(){
            DeviceView dv;
            @Override
            public void run() {
                    Picasso.get().load(url).into(image);
            }
        });
    }
}

