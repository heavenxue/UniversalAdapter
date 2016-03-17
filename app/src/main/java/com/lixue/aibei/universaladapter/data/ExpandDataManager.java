package com.lixue.aibei.universaladapter.data;

import android.content.Context;

import com.lixue.aibei.universaladapter.demo.DemoMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/17.
 */
public class ExpandDataManager {
    public static List<DemoMode> loadGroupData(Context context) {
        return loadGroupData(context, 10);
    }

    public static List<List<DemoMode>> loadChildData(Context context){
        return LoadChildData(context, 5);
    }

    public static List<DemoMode> loadGroupData(Context context, int num) {
        List<DemoMode> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            DemoMode model = new DemoMode();
            model.content = "GroupTitle" + i;
            list.add(model);
        }
        return list;
    }

    public static List<List<DemoMode>> LoadChildData(Context context,int num){
        List<List<DemoMode>> childList = new ArrayList<>();
        List<DemoMode> childItemList = new ArrayList<>();
        for (int w =0;w < 10;w++){
            for (int j = 0;j < num;j++){
                DemoMode model = new DemoMode();
                model.content = "ChildName" + j;
                childItemList.add(model);
            }
            childList.add(childItemList);
        }
        return childList;
    }
}
