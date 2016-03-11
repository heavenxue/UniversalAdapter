package com.lixue.aibei.universaladapterlib;

import android.view.ViewGroup;

/**
 * item抽象工厂类
 * Created by Administrator on 2016/3/11.
 */
public abstract class UniversalItemFactory <ITEM extends UniversalItem> {
    protected int itemType;
    protected UniversalAdapter universalAdapter;

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public UniversalAdapter getUniversalAdapter() {
        return universalAdapter;
    }

    public void setUniversalAdapter(UniversalAdapter universalAdapter) {
        this.universalAdapter = universalAdapter;
    }

    public abstract boolean isTarget(Object itemObject);

    public abstract ITEM createItem(ViewGroup parent);
}
