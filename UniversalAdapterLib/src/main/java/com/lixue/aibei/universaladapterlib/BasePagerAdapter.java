package com.lixue.aibei.universaladapterlib;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * 如果调用{@link #notifyDataSetChanged()}来更新，
 * 它会自动调用{@link #instantiateItem(ViewGroup, int)}重新new出需要的item，算是完全初始化一次。
 */
public abstract class BasePagerAdapter<T> extends PagerAdapter {
    private static final String TAG = "BasePagerAdapter";
    protected T currentItem = null;
    private boolean isNotifing;//是否正在刷新
    private final PageCache<T> mCache;//这里的最大的cache是：type*pageSize

    public BasePagerAdapter(){
        mCache = new PageCache<>();
    }

    /**判断是否由对象生成界面,这里必须是view和view的比较**/
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == getViewFromItem((T)object,0);
    }

    @Override
    public T instantiateItem(ViewGroup container, int position) {
        //先从缓存里面取
        Object type = getItemType(position);
        T item = mCache.getItem(type);
        //如果缓存中没有就创建item
        if (item == null){
            item = createItem((ViewPager)container,position);
        }
        //通过item得到将要被add到viewpager中的view
        View view = getViewFromItem(item,position);
        if (view.getParent() != null){
            ((ViewGroup)view.getParent()).removeView(view);
        }
        container.addView(view);
        return item;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        if (object != currentItem){
            currentItem = (T)object;//可能是currentItem不等于null,可能是两者不同
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        T item = (T) object;
        container.removeView(getViewFromItem(item, position));
        if (!isNotifing){
            Object type = getItemType(position);
            mCache.putItem(type,item);
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void notifyDataSetChanged() {
        isNotifing = true;
        super.notifyDataSetChanged();
        isNotifing = false;
    }

    public Object getItemType(int position) {
        return -1; // default
    }

    public T getCurrentItem() {
        return currentItem;
    }

    protected PageCache<T> getCache() {
        return mCache;
    }

    /**
     * 这里要实现一个从item拿到view的规则
     *
     * @param item     包含view的item对象
     * @param position item所处的位置
     * @return item中的view对象
     */
    protected abstract
    @NonNull
    View getViewFromItem(T item, int position);

    /**
     * 当缓存中无法得到所需item时才会调用
     *
     * @return 需要放入容器的view
     */
    protected abstract T createItem(ViewPager viewPager, int position);

    private static class PageCache<T>{
        private Map<Object,Queue<T>> mCacheMap;

        public PageCache(){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mCacheMap = new ArrayMap<>();
            }else{
                mCacheMap = new HashMap<>();
            }
        }

        /**返回cache中的item**/
        public T getItem(Object obj){
            Queue<T> queue = mCacheMap.get(obj);
            return queue != null ? queue.poll() : null;
        }

        public void putItem(Object obj,T type){
            Queue<T> queue;
            if ((queue = mCacheMap.get(obj)) == null) {
                queue = new LinkedList<>();
                mCacheMap.put(obj, queue);
            }
            queue.offer(type);
        }
    }
}
