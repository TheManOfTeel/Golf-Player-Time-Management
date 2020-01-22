package com.example.elijah.golfplayertimemanagement;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PageAdapter extends FragmentStatePagerAdapter {


    int counttab;
    public PageAdapter(FragmentManager fm, int counttab) {
        super(fm);
        this.counttab = counttab;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                PlayerTab playerTab = new PlayerTab();
                return playerTab;
            case 1:
                AdminTab admintab = new AdminTab();
                return admintab;

                default:
                    return null;


        }
    }

    @Override
    public int getCount() {
        return counttab;
    }
}
