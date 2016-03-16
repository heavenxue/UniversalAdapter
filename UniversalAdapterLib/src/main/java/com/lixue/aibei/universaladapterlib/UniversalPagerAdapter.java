package com.lixue.aibei.universaladapterlib;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lixue.aibei.universaladapterlib.item.AdapterItem;
import com.lixue.aibei.universaladapterlib.util.IAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * pagerAdapter适配器
 * Created by Administrator on 2016/3/16.
 */
public abstract class UniversalPagerAdapter<T> extends BasePagerAdapter<View> implements IAdapter<T> {
    private List<T> mDatas;
    private LayoutInflater inflater;
    private boolean mIsLazy = false;


    public UniversalPagerAdapter(@NonNull List<T> mDatas){
       this(mDatas,false);
    }

    public UniversalPagerAdapter(@NonNull List<T> mDatas,boolean isLasy){
        if (mDatas == null) this.mDatas = new ArrayList<>();
        this.mDatas = mDatas;
        this.mIsLazy = isLasy;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public Object getItemType(T t) {
        return -1;
    }

    @Override
    public Object getItemType(int position) {
        if (position < mDatas.size()){
            return getItemType(mDatas.get(position));
        }else{
            return null;
        }
    }

    @Override
    public void setData(@NonNull List<T> list) {
        mDatas = list;
    }

    @Override
    public List<T> getData() {
        return mDatas;
    }

    @NonNull
    @Override
    public Object getConvertData(T data, Object type) {
        return data;
    }

    @NonNull
    @Override
    protected View getViewFromItem(View item, int position) {
        return item;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        View view = super.instantiateItem(container,position);
        if (!mIsLazy){
            initItem(position,view);
        }
        return view;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (mIsLazy && object != currentItem){
            initItem(position, ((View) object));
        }
        super.setPrimaryItem(container, position, object);
    }

    @Override
    protected View createItem(ViewPager viewPager, int position) {
        if (inflater == null) {
            inflater = LayoutInflater.from(viewPager.getContext());
        }
        AdapterItem item = createItem(getItemType(position));
        View view = inflater.inflate(item.getLayoutResId(), null);
        view.setTag(R.id.tag_item, item);
        item.bindViews(view);
        item.setViews();
        return view;
    }

    public void setIsLazy(boolean isLazy) {
        mIsLazy = isLazy;
    }

    private void initItem(int position, View view) {
        AdapterItem item = (AdapterItem) view.getTag(R.id.tag_item);
        item.handleData(getConvertData(mDatas.get(position), getItemType(position)), position);
    }
}
