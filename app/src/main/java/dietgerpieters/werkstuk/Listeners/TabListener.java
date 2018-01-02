package dietgerpieters.werkstuk.Listeners;

import android.support.v7.app.ActionBar.Tab;
import android.support.v4.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBar;

import dietgerpieters.werkstuk.R;

/**
 * Created by User on 1/01/2018.
 */


public class TabListener implements ActionBar.TabListener {

    Fragment fragment;

    public TabListener(Fragment fragment) {
        // TODO Auto-generated constructor stub
        this.fragment = fragment;
    }


    @Override
    public void onTabSelected(Tab tab, android.support.v4.app.FragmentTransaction ft) {
        ft.replace(R.id.pager, fragment);
    }

    @Override
    public void onTabUnselected(Tab tab, android.support.v4.app.FragmentTransaction ft) {
        ft.remove(fragment);
    }

    @Override
    public void onTabReselected(Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }
}