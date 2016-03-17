package com.lixue.aibei.universaladapterlib.item;

import android.view.View;

/**
 * Created by Administrator on 2016/3/17.
 */
public interface ExpandableApdaterItem<T> {
    /**获取到布局的资源id**/
    int getLayoutResId();
    /**将布局资源设置到ListView或是RecylerView或是ViewPager上**/
    void bindViews(View root);
    /**设置view的参数**/
    void setViews();
    /**给布局添加上数据**/
    void handleData(T t,int postion);
    //以下是用来给expandableListView中的group设置参数的
    int getLayoutGroupResId();
    void bindGroupViews(View root);
    void handleGroupData(T t,int position);
}
