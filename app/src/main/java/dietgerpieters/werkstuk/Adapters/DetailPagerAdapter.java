package dietgerpieters.werkstuk.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import dietgerpieters.werkstuk.Fragments.DetailTabFragment;
import dietgerpieters.werkstuk.Fragments.MapsTabFragment;
import dietgerpieters.werkstuk.Models.Wedstrijd;

/**
 * Created by User on 1/01/2018.
 */

public class DetailPagerAdapter extends FragmentStatePagerAdapter {
    private Wedstrijd w;

    public DetailPagerAdapter(FragmentManager fm, Wedstrijd w) {
        super(fm);
        this.w = w;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                DetailTabFragment detailTabFragment = new DetailTabFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("Wedstrijd", w);
                detailTabFragment.setArguments(bundle);
                return detailTabFragment;
            case 1:
                MapsTabFragment mapsTabFragment = new MapsTabFragment();
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("Wedstrijd", w);
                mapsTabFragment.setArguments(bundle2);
                return mapsTabFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + (position + 1);
    }
}
