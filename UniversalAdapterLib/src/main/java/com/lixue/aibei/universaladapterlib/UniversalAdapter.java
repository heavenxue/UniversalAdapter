package com.lixue.aibei.universaladapterlib;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lixue.aibei.universaladapterlib.item.AdapterItem;
import com.lixue.aibei.universaladapterlib.util.IAdapter;
import com.lixue.aibei.universaladapterlib.util.ItemTypeUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/3/15.
 */
public abstract class UniversalAdapter<T> extends BaseAdapter implements IAdapter<T> {
    private static final String TAG = "UniversalAdapter";
    public List<T> mDataList;
    public int mViewTypecount = 1;
    public Object mType;//每个itemType,会根据每一条的数据产生

    private LayoutInflater mInflater;
    private ItemTypeUtil typeUtil;

    protected UniversalAdapter(@NonNull List<T> list){
        this(list, 1);
    }

    protected UniversalAdapter(@Nullable List<T> data, int viewTypeCount) {
        if (data == null) {
            data = new ArrayList<>();
        }
        mDataList = data;
        mViewTypecount = viewTypeCount;
        typeUtil = new ItemTypeUtil();
    }

    @Override
    public int getCount() {
        return mDataList != null ? mDataList.size() : 0;
    }

    @Override
    public void setData(@NonNull List<T> list) {
        mDataList = list;
    }

    @Override
    public List<T> getData() {
        return mDataList;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * 通过数据得到obj的类型的type
     * 然后，通过{@link ItemTypeUtil}来转换位int类型的type
     **/
    @Override
    public int getItemViewType(int position) {
        mType = getItemType(mDataList.get(position));
        return typeUtil.getIntType(mType);
    }

    @Override
    public Object getItemType(T t) {
        return -1;//default
    }

    @Override
    public int getViewTypeCount() {
        return mViewTypecount;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (mInflater == null){
            mInflater = LayoutInflater.from(viewGroup.getContext());
        }
        AdapterItem item;
        if (view == null){
            item = createItem(mType);
            Log.i(TAG,"item is :" + item.getClass().getSimpleName());
            view = mInflater.inflate(item.getLayoutResId(), viewGroup,false);
            view.setTag(R.id.tag_item,item);//get item
            item.bindViews(view);
            item.setViews();
        }else{
            item = (AdapterItem)view.getTag(R.id.tag_item);
        }
        item.handleData(getConvertData(mDataList.get(i),mType),i);
        return view;
    }

    @NonNull
    @Override
    public Object getConvertData(T data, Object type) {
        return data;
    }

    @Override
    public Object getItem(int i) {
        return mDataList.get(i);
    }

}

