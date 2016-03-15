package com.lixue.aibei.universaladapterlib.item;

import android.view.View;

/**
 * 通过{@link #getLayoutResId()}初始化view;<br>
 * 在{@link #bindViews(View)}中就初始化item的内部视图<br>
 * 在{@link #handleData(Object, int)}中处理每一行的数据<p>
 */
public interface AdapterItem<T> {
    /**获取到布局的资源id**/
    int getLayoutResId();
    /**将布局资源设置到ListView或是RecylerView或是ViewPager上**/
    void bindViews(View root);
    /**设置view的参数**/
    void setViews();
    /**给布局添加上数据**/
    void handleData(T t,int postion);
}
