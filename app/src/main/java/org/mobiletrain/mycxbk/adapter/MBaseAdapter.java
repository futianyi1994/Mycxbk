package org.mobiletrain.mycxbk.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/4.
 */
public abstract class MBaseAdapter<T> extends BaseAdapter {
    private List<T> datas;
    private LayoutInflater inflater;
    private LruCache<String, Bitmap> lruCache;
    private int maxSize = (int) (Runtime.getRuntime().maxMemory() / 8);

    public LayoutInflater getInflater() {
        return inflater;
    }
    public MBaseAdapter(Context context) {
        this.datas = new ArrayList<T>();
        this.inflater = LayoutInflater.from(context);
        this.lruCache = new LruCache<String, Bitmap>(maxSize) {
            // 返回指定缓存的大小，该方法用系统自动调用
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public T getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 添加一个集合
     * @param dd
     */
    public void addAll(List<T> dd)
    {
        datas.addAll(dd);
        notifyDataSetChanged();
    }

    /**
     * 添加一个缓存
     * @param lruCache
     */
    public void addLruCache(LruCache<String, Bitmap> lruCache){
        this.lruCache = lruCache;
    }

    /**
     * 得到一个缓存
     * @return
     */
    public LruCache<String, Bitmap> getLruCache(){
        return lruCache;
    }

    /**
     * 清空数据源
     */
    public void clear()
    {
        datas.clear();
        notifyDataSetChanged();
    }
}
