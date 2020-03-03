package com.example.digitalevidence.helpers;
import android.annotation.TargetApi;
import com.example.digitalevidence.models.Device;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ComparatorHelper {
    private static ComparatorHelper instance;

    public static ComparatorHelper getInstance() {
        return instance;
    }

    private ComparatorHelper(){}

    /**
     * returns objects of different image
     * @parm size - number of items different or close to different
     */

    @TargetApi(24)
    public static List<Device> getDiffObjects(List<Device> devices){
        Map<String, Device> map = new HashMap<>();

        for (Device d: devices){
            if(map.get(d.getImage()) == null){
                map.put(d.getImage(), d);
            }
        }

        List<Device> dev = map.values().stream().collect(Collectors.toList());

        /*
        If want to show repetitive images instead if less than 3 options
        int size = 3
        while(dev.size() < size){
           dev.add(dev.get(dev.size()-1));
        }*/

        return dev;
    }
}