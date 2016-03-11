package com.lixue.aibei.universaladapterlib;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/11.
 */
public class UniversalAdapter extends BaseAdapter {
    private static final String TAG = "UniversalAdapter";
    private List dataList;//数据集合
    private List<UniversalItemFactory> itemFactoryList;//itemFactory集合
    private LoadMoreListItemFactory loadMoreListItemFactory;
    private LoadMoreListItemFactory.AbstracLoadMoreListItem loadMoreListItem;
    private boolean itemFactoryLocked;  // 锁定之后就不能再添加ItemFactory了
    private boolean setEnableLoadMore;  // 已经设置过开启加载功能后就不能再添加ItemFactory了

    public UniversalAdapter(List dataList){
        this.dataList = dataList;
    }

    public UniversalAdapter(Object... dataArray){
        if (dataArray != null && dataArray.length > 0){
            this.dataList = new ArrayList(dataArray.length);
            Collections.addAll(dataList,dataArray);
        }
    }

    public void addItemFactory(UniversalItemFactory itemFactory){
        if (itemFactoryLocked)
            throw new IllegalStateException("item factory is locked!");
        if (setEnableLoadMore)
            throw new IllegalStateException("Call a enableLoadMore () method can be not call again after addItemFactory () method");
        itemFactory.setItemType(itemFactoryList.size());
        itemFactory.setUniversalAdapter(this);

        if (itemFactoryList != null){
            itemFactoryList.add(itemFactory);
        }else{
            itemFactoryList = new LinkedList<UniversalItemFactory>();
            itemFactoryList.add(0, itemFactory);
        }
    }

    public void append(List dataList) {
        if (dataList == null || dataList.size() == 0) {
            return;
        }
        if (this.dataList == null) {
            this.dataList = dataList;
        } else {
            this.dataList.addAll(dataList);
        }
        notifyDataSetChanged();
    }

    /**开启加载更多功能**/
    public void setEnableLoadMore(LoadMoreListItemFactory loadMoreFactory){
        if(loadMoreListItemFactory != null){
            if(itemFactoryList == null || itemFactoryList.size() == 0){
                throw new IllegalStateException("You need to configure AssemblyItemFactory use addItemFactory method");
            }
            setEnableLoadMore = true;
            this.loadMoreListItemFactory = loadMoreFactory;
            this.loadMoreListItemFactory.loadMoreRunning = false;
            this.loadMoreListItemFactory.ended = false;
            this.loadMoreListItemFactory.setUniversalAdapter(this);
            this.loadMoreListItemFactory.setItemType(itemFactoryList.size());
            notifyDataSetChanged();
        }
    }

    /**
     * 关闭加载更多功能
     */
    public void disableLoadMore() {
        if(loadMoreListItemFactory != null){
            loadMoreListItemFactory.loadMoreRunning = false;
            loadMoreListItemFactory.ended = false;
            loadMoreListItemFactory = null;
            notifyDataSetChanged();
        }
    }

    /**
     * 加载更多完成，当你一次请求完成后需要调用此方法
     */
    public void loadMoreFinished(){
        if(loadMoreListItemFactory != null){
            loadMoreListItemFactory.loadMoreRunning = false;
        }
    }

    /**
     * 加载更过失败，请求失败的时候需要调用此方法，会显示错误提示，并可点击重新加载
     */
    public void loadMoreFailed(){
        if(loadMoreListItemFactory != null){
            loadMoreListItemFactory.loadMoreRunning = false;
        }
        if(loadMoreListItem != null){
            loadMoreListItem.showErrorRetry();
        }
    }

    /**
     * 加载更多结束，当没有更多内容的时候你需要调用此方法
     */
    public void loadMoreEnd(){
        if(loadMoreListItemFactory != null){
            loadMoreListItemFactory.loadMoreRunning = false;
            loadMoreListItemFactory.ended = true;
        }
        if(loadMoreListItem != null){
            loadMoreListItem.showEnd();
        }
    }

    @Override
    public int getCount() {
        if (dataList == null || dataList.size() <= 0) return 0;
        return dataList.size() + (loadMoreListItemFactory != null ? 1 : 0);
    }

    @Override
    public Object getItem(int i) {
//        return dataList.get(i);他娘的，写的太严谨了
        return dataList != null && i < dataList.size() ? dataList.get(i) : null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getViewTypeCount() {
        if(itemFactoryList == null || itemFactoryList.size() == 0){
            throw new IllegalStateException("You need to configure AssemblyItemFactory use addItemFactory method");
        }
        itemFactoryLocked = true;
        return itemFactoryList.size() + (loadMoreListItemFactory != null ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if(itemFactoryList == null || itemFactoryList.size() == 0){
            throw new IllegalStateException("You need to configure AssemblyItemFactory use addItemFactory method");
        }
        itemFactoryLocked = true;
        if (loadMoreListItemFactory != null && position == getCount() -1){
            return loadMoreListItemFactory.getItemType();
        }
        Object obj = getItem(position);
        for (UniversalItemFactory itemFactory : itemFactoryList){
            itemFactory.setItemType(position);
            if(itemFactory.isTarget(obj)){
                return itemFactory.getItemType();
            }
        }
        Log.e(TAG, "getItemViewType() - Didn't find suitable AssemblyItemFactory. position=" + position + ", itemObject=" + (obj != null ? obj.getClass().getName() : "null"));
        return -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if(itemFactoryList == null || itemFactoryList.size() == 0){
            throw new IllegalStateException("You need to configure AssemblyItemFactory use addItemFactory method");
        }

        // position是最后一位，说明是加载更多尾巴
        if(loadMoreListItemFactory != null && position == getCount()-1){
            if(convertView == null){
                UniversalItem item = loadMoreListItemFactory.createItem(viewGroup);
                if(item == null){
                    Log.e(TAG, "getView() - Create AssemblyItem failed. position="+position+", ItemFactory="+loadMoreListItemFactory.getClass().getName());
                    return null;
                }
                convertView = item.getConvertView();
            }

            this.loadMoreListItem = (LoadMoreListItemFactory.AbstracLoadMoreListItem) convertView.getTag();
            this.loadMoreListItem.setBean(position, null);
            return convertView;
        }

        Object itemObject = getItem(position);
        for(UniversalItemFactory itemFactory : itemFactoryList){
            if(!itemFactory.isTarget(itemObject)){
                continue;
            }

            if (convertView == null) {
                UniversalItem assemblyItem = itemFactory.createItem(viewGroup);
                if(assemblyItem == null){
                    Log.e(TAG, "getView() - Create AssemblyItem failed. position="+position+", ItemFactory"+itemFactory.getClass().getName());
                    return null;
                }
                convertView = assemblyItem.getConvertView();
            }

            ((UniversalItem) convertView.getTag()).setBean(position, itemObject);
            return convertView;
        }

        Log.e(TAG, "getView() - Didn't find suitable AssemblyItemFactory. position="+position+", itemObject="+(itemObject!=null?itemObject.getClass().getName():"null"));
        return null;
    }
}
