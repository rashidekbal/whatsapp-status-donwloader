package com.rtech.statusdownloaderwa.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.rtech.statusdownloaderwa.fragments.AllFragment;
import com.rtech.statusdownloaderwa.fragments.ImagesFragment;
import com.rtech.statusdownloaderwa.fragments.VideosFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new AllFragment();
        } else if (position==1) {
            return new ImagesFragment();

        }else{
            return new VideosFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0){
            return "ALL";
        } else if (position==1) {
            return "IMAGE";

        }else {
            return "VIDEOS";
        }
    }
}