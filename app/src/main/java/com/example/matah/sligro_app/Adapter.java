package com.example.matah.sligro_app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by matah on 18/01/11.
 */

public interface Adapter {
     void Adapter();
     Fragment getItem(int position);
     List<Fragment> getListFragment();
     int getCount();
}
