package com.example.ragna.taassistant.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ragna on 29/09/2018.
 */

public class tabAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragmentsList=new ArrayList<>();
    private final List<String> fragmentsTitle=new ArrayList<>();
    public tabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentsList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentsList.size();
    }
    public void addFragment(Fragment fragment,String s){
        fragmentsList.add(fragment);
        fragmentsTitle.add(s);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentsTitle.get(position);
    }
}
