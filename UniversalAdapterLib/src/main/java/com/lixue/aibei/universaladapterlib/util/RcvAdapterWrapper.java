package com.lixue.aibei.universaladapterlib.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/3/15.
 */
public class RcvAdapterWrapper<T extends RecyclerView.Adapter> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**VIEW的基本类型，这里只有头/底部/普通，在子类可以扩展**/
    private static final int TYPE_HEADER = 10001;
    private static final int TYPE_FOOTER = 10002;

    private RecyclerView.LayoutManager mLayoutManager;
    private T adapterWrapper;
    private View mHeader = null;
    private View mFooter = null;

    public RcvAdapterWrapper(T adapterWrapper,RecyclerView.LayoutManager layoutManager){
        this.adapterWrapper = adapterWrapper;
        adapterWrapper.registerAdapterDataObserver(getObserver());
        this.mLayoutManager = layoutManager;
        if (mLayoutManager instanceof GridLayoutManager){
            setSpanSizeLookup();//设置头部和尾部都是跨列的
        }
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @NonNull
    private RecyclerView.AdapterDataObserver getObserver(){
        return new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
                notifyItemRangeChanged(positionStart + getHeaderCount(),itemCount);
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                notifyItemRangeInserted(positionStart + getHeaderCount(), itemCount);
                notifyItemRangeChanged(positionStart + getHeaderCount() + itemCount - 1, getItemCount() - itemCount - positionStart - getFooterCount());
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                notifyItemRangeRemoved(positionStart, itemCount);
                notifyItemRangeChanged(getHeaderCount() + positionStart, getItemCount() - getHeaderCount() - positionStart - getFooterCount());
            }

            @Override
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount);
                //还没支持转移的操作
            }
        };
    }

    public int getHeaderCount(){
        return mHeader != null ? 1 : 0;
    }

    public int getFooterCount(){
        return mFooter != null ? 1 : 0;
    }

    /**设置头部和尾部都是跨列的**/
    public void setSpanSizeLookup(){

    }

}
