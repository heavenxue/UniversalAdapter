package com.lixue.aibei.universaladapterlib.util;

import android.support.annotation.NonNull;

import com.lixue.aibei.universaladapterlib.item.AdapterItem;

import java.util.List;

/**
 * Created by Administrator on 2016/3/15.
 *  通用的adapter必须实现的接口，作为方法名统一的一个规范
 */
public interface IAdapter<T> {
    /**设置数据源**/
    void setData(@NonNull List<T> list);
    /**得到数据**/
    List<T> getData();
    /**强烈建议返回string, int, bool类似的基础对象做type**/
    Object getItemType(T t);

    /**当缓存中无法得到所需的item时才会调用
     * @param type 通过{@link #getItemType(Object)}得到的type**/
    @NonNull
    AdapterItem createItem(Object type);

    /**如果放入item的最终数据和list中的每一条数据类型是不同的，可以通过此方法进行转换**/
    @NonNull
    Object getConvertData(T data,Object type);

    /**刷新数据**/
    void notifyDataSetChanged();
}
