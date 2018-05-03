package geogram.example.galleryforyou.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import geogram.example.galleryforyou.ui.fragments.ImagesListFragment;

/**
 * Created by geogr on 03.05.2018.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {
    private final String CARS = "cars";
    private final String NATURE = "nature";
    private final String ART = "art";
    private final String ARG_TYPE="type";
    private final List<String> titles;

    public MainPagerAdapter(FragmentManager fm, List<String> titles) {
        super(fm);
        this.titles=titles;
    }

    @Override
    public Fragment getItem(int position) {
        ImagesListFragment fragment = new ImagesListFragment();
        Bundle args = new Bundle();
        switch (position) {
            case 0:
                args.putString(ARG_TYPE, ART);
                break;
            case 1:
                args.putString(ARG_TYPE, NATURE);
                break;
            case 2:
                args.putString(ARG_TYPE, CARS);
                break;

        }
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

}