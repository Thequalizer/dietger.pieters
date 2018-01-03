package dietgerpieters.werkstuk.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dietgerpieters.werkstuk.R;

/**
 * Created by Dietger (Pantani) on 31/12/2017.
 */

public class RegisterInputFragment extends Fragment {

    OnUserRegisterListener mCallback;


    // Container Activity must implement this interface
    public interface OnUserRegisterListener {
        public void onRegisterButtonListener(View v);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnUserRegisterListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_input, container, false);
    }


}
