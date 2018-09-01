package org.mobiletrain.mycxbk.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.mobiletrain.mycxbk.fragment.BaseFragment;

import java.util.List;

/**
 * Created by 天一 on 2016/10/10.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> fragments;

    public MyFragmentPagerAdapter(FragmentManager fm,List<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
