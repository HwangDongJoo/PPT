package ppt.ppt.controller.market;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ppt.ppt.view.MarketChartFragment;

/**
 * Created by dj_hwang on 2017. 9. 20..
 */

public class MarketTabPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public MarketTabPagerAdapter(FragmentManager fm, int tabCount){
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        MarketChartFragment fragment = new MarketChartFragment();
        Bundle bundle = new Bundle();

        switch (position) {

            case 0:
                bundle.putInt("market", position);
                fragment.setArguments(bundle);
                return fragment;
            case 1:
                bundle.putInt("market", position);
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
