package com.lixue.aibei.universaladapterlib;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.lixue.aibei.universaladapterlib.item.ExpandableApdaterItem;
import com.lixue.aibei.universaladapterlib.util.IAdapter;
import com.lixue.aibei.universaladapterlib.util.IExpandableAdapter;
import com.lixue.aibei.universaladapterlib.util.ItemTypeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于ExpandableListView的一款适配器
 * Created by Administrator on 2016/3/17.
 */
public abstract class UniversalExpandaleAdapter<T> extends BaseExpandableListAdapter implements IAdapter<T>,IExpandableAdapter{
    private List<T> groupList;
    private List<List<T>> childList;
    public Object mType;//每个itemType,会根据每一条的数据产生
    public Object mChildType;//每个itemType,会根据每一条的数据产生
    private LayoutInflater mInflater;
    private ItemTypeUtil typeUtil;

    public UniversalExpandaleAdapter(List<T> groupList,List<List<T>> childList){
        if (groupList == null) groupList = new ArrayList<>();
        if (childList == null) childList = new ArrayList<List<T>>();
        this.groupList = groupList;
        this.childList = childList;
    }

    @Override
    public int getGroupCount() {
        return groupList != null ? groupList.size() : 0;
    }

    @Override
    public int getChildrenCount(int i) {
        return childList != null ? childList.size() : 0;
    }

    @Override
    public Object getGroup(int i) {
        mType = groupList.get(i);
        return groupList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        mChildType = childList.get(i).get(i1);
        return childList.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public Object getItemType(T t) {
        return -1;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if (mInflater == null){
            mInflater = LayoutInflater.from(viewGroup.getContext());
        }
        ExpandableApdaterItem item;
        if (view == null){
            item = createItems(mType);
            view = mInflater.inflate(item.getLayoutGroupResId(), viewGroup,false);
            view.setTag(R.id.tag_item,item);//get item
            item.bindGroupViews(view);
            item.setViews();
        }else{
            item = (ExpandableApdaterItem)view.getTag(R.id.tag_item);
        }
        item.handleGroupData(getConvertData(groupList.get(i), mType), i);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        mInflater = LayoutInflater.from(viewGroup.getContext());
        ExpandableApdaterItem item;
        if (view == null){
            item = createItems(mChildType);
            view = mInflater.inflate(item.getLayoutResId(), viewGroup,false);
            view.setTag(R.id.tag_item_child,item);//get item
            item.bindViews(view);
            item.setViews();
        }else{
            item = (ExpandableApdaterItem)view.getTag(R.id.tag_item_child);
        }
        item.handleData(getConvertData(childList.get(i).get(i1),mChildType),i1);
        return view;
    }

    @NonNull
    @Override
    public Object getConvertData(T data, Object type) {
        return data;
    }

    @Override
    public List<T> getData() {
        return null;
    }

    @Override
    public void setData(@NonNull List<T> list) {

    }
    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
