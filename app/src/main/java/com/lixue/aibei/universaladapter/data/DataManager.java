package com.lixue.aibei.universaladapter.data;

import android.content.Context;

import com.lixue.aibei.universaladapter.R;
import com.lixue.aibei.universaladapter.demo.DemoMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/3/15.
 */
public class DataManager {
    public static List<DemoMode> loadData(Context context) {
        return loadData(context, 200);
    }

    /**
     * 模拟加载数据的操作
     */
    public static List<DemoMode> loadData(Context context, int num) {
        List<String> originList = Arrays.asList(context.getResources().getStringArray(R.array.country_names));
        List<DemoMode> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            DemoMode model = new DemoMode();
            model.type = "ok";
            model.content = "阿 " + i;
            model.age = (int)(Math.random()*20) + "";
            model.job = "police " + i;
            if (i%2 == 0){
                model.sex = "男";
            }else{
                model.sex ="女";
            }

            model.imgId = String.valueOf(R.drawable.xinru);
            list.add(model);
        }
        return list;
    }
}
