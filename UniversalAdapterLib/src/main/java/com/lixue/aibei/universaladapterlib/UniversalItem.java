package com.lixue.aibei.universaladapterlib;

import android.content.Context;
import android.view.View;

/**
 * item抽象类
 * Created by Administrator on 2016/3/11.
 */
public abstract class UniversalItem<BEAN,ITEM_FACTORY extends UniversalItemFactory> {
    protected BEAN bean;
    protected ITEM_FACTORY item_factory;
    protected int positon;
    protected View convertView;

    protected UniversalItem(View convertView,ITEM_FACTORY item_factory){
        if (convertView == null)
            throw new IllegalArgumentException("param converview is null");
        if (item_factory == null)
            throw new IllegalArgumentException("param item_factory is null");
        this.convertView = convertView;
        this.item_factory = item_factory;
        this.convertView.setTag(this);
        onFindViews(convertView);
        onConfigViews(convertView.getContext());
    }

    protected abstract void onFindViews(View convertView);
    protected abstract void onConfigViews(Context context);
    protected abstract void onSetData(int positon,BEAN bean);

    public ITEM_FACTORY getItem_factory() {
        return item_factory;
    }

    public int getPositon() {
        return positon;
    }

    public void setItem_factory(ITEM_FACTORY item_factory) {
        this.item_factory = item_factory;
    }

    public BEAN getBean() {
        return bean;
    }

    public void setBean(int position,BEAN bean) {
        this.positon = position;
        this.bean = bean;
        onSetData(position,bean);
    }

    public View getConvertView() {
        return convertView;
    }

    public void setConvertView(View convertView) {
        this.convertView = convertView;
    }
}
