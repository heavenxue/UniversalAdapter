package com.lixue.aibei.universaladapterlib;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lixue.aibei.universaladapterlib.item.AdapterItem;
import com.lixue.aibei.universaladapterlib.util.IAdapter;
import com.lixue.aibei.universaladapterlib.util.ItemTypeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/16.
 */
public abstract class UniversalRcvAdapter<T> extends RecyclerView.Adapter implements IAdapter<T>{
    private List<T> mdatas;
    private ItemTypeUtil mitemTypeUtil = new ItemTypeUtil();
    private Object mItemType;

    protected UniversalRcvAdapter(List<T> list){
        if (list == null) this.mdatas = new ArrayList<>();
        this.mdatas = list;
    }

    @Override
    public Object getItemType(T t) {
        return -1;//default
    }

    @Override
    public int getItemViewType(int position) {
        mItemType = getItemType(mdatas.get(position));
        return mitemTypeUtil.getIntType(mItemType);
    }

    @Override
    public int getItemCount() {
        return mdatas != null ? mdatas.size() : 0;
    }

    @Override
    public void setData(@NonNull List<T> list) {
        mdatas = list;
    }

    @Override
    public List<T> getData() {
        return mdatas;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RcvAdapterItem(parent.getContext(), parent, createItem(mItemType));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        debug((RcvAdapterItem) holder);
        ((RcvAdapterItem) holder).item.handleData(getConvertData(mdatas.get(position),mItemType),position);
    }

    @NonNull
    @Override
    public Object getConvertData(T data, Object type) {
        return data;
    }

    private static class RcvAdapterItem extends RecyclerView.ViewHolder{
        protected AdapterItem item;

        public boolean isNew = true; // debug中才用到

        public RcvAdapterItem(Context context,ViewGroup viewGroup,AdapterItem item) {
            super(LayoutInflater.from(context).inflate(item.getLayoutResId(),viewGroup,false));
            this.item = item;
            this.item.bindViews(itemView);
            this.item.setViews();
        }
    }

    private void debug(RcvAdapterItem holder) {
        boolean debug = false;
        if (debug) {
            holder.itemView.setBackgroundColor(holder.isNew ? 0xffff0000 : 0xff00ff00);
            holder.isNew = false;
        }
    }

}
