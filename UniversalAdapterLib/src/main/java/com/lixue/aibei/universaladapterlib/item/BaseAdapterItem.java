package com.lixue.aibei.universaladapterlib.item;

import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2016/3/15.
 */
public abstract class BaseAdapterItem<T> implements AdapterItem<T> {
    protected View parent;

    @Override
    public void bindViews(View root) {
        this.parent = root;
        bindViews();
    }

    /**
     * 方法中可以使用{@link#getView(int)}来代替{@link View#findViewById(int)}
     */
    public final <E extends View> E getView(int id) {
        try {
            return (E) parent.findViewById(id);
        } catch (ClassCastException ex) {
            Log.e("BaseAdapterItem", "Could not cast View to concrete class.", ex);
            throw ex;
        }
    }

    protected abstract void bindViews();
}
