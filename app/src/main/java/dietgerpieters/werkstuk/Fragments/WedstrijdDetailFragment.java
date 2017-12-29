package dietgerpieters.werkstuk.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dietgerpieters.werkstuk.R;

/**
 * Created by Dietger (Pantani) on 28/12/2017.
 */

public class WedstrijdDetailFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detail_wedstrijd, container, false);
    }



}
