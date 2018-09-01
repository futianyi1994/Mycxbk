package org.mobiletrain.mycxbk.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by 天一 on 2016/10/10.
 */
public class TTHeaderPagerAdapter extends PagerAdapter {
    List<ImageView> imgList;

    public TTHeaderPagerAdapter(List<ImageView> imgList) {
        this.imgList = imgList;
    }

    @Override
    public int getCount() {
        return imgList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(imgList.get(position));
        return imgList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView(imgList.get(position));
    }
}
