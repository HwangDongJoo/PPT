package ppt.ppt.controller.news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ppt.ppt.view.NewsListFragment;

/**
 * Created by dj_hwang on 2017. 9. 20..
 */

public class NewsTabPagerAdapter extends FragmentPagerAdapter {

    private int tabCount;

    public NewsTabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        switch (position){
            case 0:
                bundle.putInt("position", position);
                fragment.setArguments(bundle);
                return fragment;
            case 1:
                bundle.putInt("position", position);
                fragment.setArguments(bundle);
                return fragment;
            case 2:
                bundle.putInt("position", position);
                fragment.setArguments(bundle);
                return fragment;
            case 3:
                bundle.putInt("position", position);
                fragment.setArguments(bundle);
                return fragment;
            case 4:
                bundle.putInt("position", position);
                fragment.setArguments(bundle);
                return fragment;
            case 5:
                bundle.putInt("position", position);
                fragment.setArguments(bundle);
                return fragment;
            case 6:
                bundle.putInt("position", position);
                fragment.setArguments(bundle);
                return fragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
