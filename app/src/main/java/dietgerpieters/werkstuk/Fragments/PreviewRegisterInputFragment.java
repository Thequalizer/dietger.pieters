package dietgerpieters.werkstuk.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import dietgerpieters.werkstuk.R;

/**
 * Created by Dietger (Pantani) on 31/12/2017.
 */

public class PreviewRegisterInputFragment extends Fragment {

    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_preview, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        button = (Button) getActivity().findViewById(R.id.register_now);
        button.setEnabled(false);


    }

    public void disableButton() {
        button.setEnabled(false);
    }


    public void updateValues(String n, String a, int l, String g) {
        TextView t = (TextView) getActivity().findViewById(R.id.naam_info);
        t.setText(n);
        TextView t2 = (TextView) getActivity().findViewById(R.id.achternaam_info);
        t2.setText(a);
        TextView t3 = (TextView) getActivity().findViewById(R.id.leeftijd_info);
        t3.setText(String.valueOf(l));
        TextView t4 = (TextView) getActivity().findViewById(R.id.geslacht_info);
        t4.setText(g);
        button.setEnabled(true);

    }

}
