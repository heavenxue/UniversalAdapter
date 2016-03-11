package com.lixue.aibei.universaladapterlib;

import android.content.Context;
import android.view.View;

/**
 * Created by Administrator on 2016/3/11.
 */
public abstract class LoadMoreListItemFactory extends UniversalItemFactory<LoadMoreListItemFactory.AbstracLoadMoreListItem>{
    protected boolean loadMoreRunning;//加载更多运行中
    protected boolean ended;//加载更多结束
    protected OnLoadMoreListener onLoadMoreListener;//加载更多监听器

    public LoadMoreListItemFactory(OnLoadMoreListener onLoadMoreListener){
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public boolean isTarget(Object itemObject) {
        return false;
    }

    public abstract class AbstracLoadMoreListItem extends UniversalItem<String,LoadMoreListItemFactory>{

        protected AbstracLoadMoreListItem(View convertView, LoadMoreListItemFactory loadMoreListItemFactory) {
            super(convertView, loadMoreListItemFactory);
        }

        public abstract View getErrorRetryView();
        public abstract void showLoading();
        public abstract void showErrorRetry();
        public abstract void showEnd();

        @Override
        protected void onConfigViews(Context context) {
            getErrorRetryView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getItem_factory().onLoadMoreListener != null){
                        getItem_factory().loadMoreRunning = false;
                        setBean(getPositon(),getBean());
                    }
                }
            });
        }

        @Override
        protected void onSetData(int positon, String s) {
            if (item_factory.ended){
                showEnd();
            }else{
                showLoading();
                if (item_factory.onLoadMoreListener != null && item_factory.loadMoreRunning){
                    item_factory.loadMoreRunning = true;
                    item_factory.onLoadMoreListener.onLoadMore(item_factory.universalAdapter);
                }
            }
        }
    }
}
