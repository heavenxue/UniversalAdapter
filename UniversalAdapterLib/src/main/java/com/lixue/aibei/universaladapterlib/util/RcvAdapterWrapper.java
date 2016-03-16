package com.lixue.aibei.universaladapterlib.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
        if (viewType == TYPE_FOOTER){
            return new SimpleViewHolder(mHeader);
        }else if(viewType == TYPE_HEADER){
            return new SimpleViewHolder(mFooter);
        }else{
            return adapterWrapper.onCreateViewHolder(parent,viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type != TYPE_FOOTER && type != TYPE_HEADER){
            adapterWrapper.onBindViewHolder(holder,position - getHeaderCount());
        }
    }

    @Override
    public int getItemCount() {
        int headerorfooter = 0;
        if(mHeader != null) headerorfooter++;
        if (mFooter != null) headerorfooter++;
        return headerorfooter + adapterWrapper.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (mFooter != null && position == 0) return TYPE_HEADER;
        if (mHeader != null && position == getItemCount() - 1) return TYPE_FOOTER;
        return adapterWrapper.getItemViewType(position - getHeaderCount());
    }

    ///////////////////////////////////////////////////////////////////////////
    // 添加/移除头部、尾部的操作
    ///////////////////////////////////////////////////////////////////////////

    public void setHeaderView(@NonNull View headerView) {
        mHeader = headerView;
        if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            setFulSpanLayoutParams(mHeader);
        }
        notifyDataSetChanged();
    }

    public void setFooterView(@NonNull View footerView) {
        mFooter = footerView;
        if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            setFulSpanLayoutParams(mFooter);
        }
        notifyDataSetChanged();
    }

    public View getHeaderView() {
        return mHeader;
    }

    public View getFooterView() {
        return mFooter;
    }

    public void removeHeaderView() {
        mHeader = null;
        // notifyItemRemoved(0);如果这里需要做头部的删除动画，
        // 可以复写这个方法，然后进行改写
        notifyDataSetChanged();
    }

    public void removeFooterView() {
        mFooter = null;
        notifyItemRemoved(getItemCount());
        // 这里因为删除尾部不会影响到前面的pos的改变，所以不用刷新了。
    }

    public T getWrappedAdapter() {
        return adapterWrapper;
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return mLayoutManager;
    }

    /**数据更新后的操作**/
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
        GridLayoutManager gridLayoutManager = (GridLayoutManager) mLayoutManager;
        final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
        final int count = gridLayoutManager.getSpanCount();
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int type = getItemViewType(position);
                if (type == TYPE_HEADER || type == TYPE_FOOTER){
                    return count;
                }else{
                    return spanSizeLookup.getSpanSize(position - getHeaderCount());
                }
            }
        });
    }

    /**设置头部和尾部都是跨列的**/
    public void setFulSpanLayoutParams(View v){
        StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setFullSpan(true);
        v.setLayoutParams(layoutParams);
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder{

        public SimpleViewHolder(View itemView) {
            super(itemView);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            itemView.setLayoutParams(lp);

        }
    }
}
