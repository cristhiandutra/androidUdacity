package com.android.petropolistourguide;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CategoryPageAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private final int ATTRACTION = 0;
    private final int MUSEUM = 1;
    private final int PARK = 2;
    private final int RESTAURANT = 3;

    public CategoryPageAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case ATTRACTION:
                return new AttractionFragment();
            case MUSEUM:
                return new MuseumFragment();
            case PARK:
                return new ParkFragment();
            case RESTAURANT:
                return new RestaurantFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case ATTRACTION:
                return mContext.getString(R.string.attractions);
            case MUSEUM:
                return mContext.getString(R.string.museums);
            case PARK:
                return mContext.getString(R.string.parks);
            case RESTAURANT:
                return mContext.getString(R.string.restaurants);
            default:
                return null;
        }
    }
}
