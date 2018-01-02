package dietgerpieters.werkstuk.Fragments;

import android.arch.persistence.room.Room;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import dietgerpieters.werkstuk.Activities.WedstrijdDetailActivity;
import dietgerpieters.werkstuk.Controllers.WedstrijdController;
import dietgerpieters.werkstuk.Database.AppDatabase;
import dietgerpieters.werkstuk.Models.MyAgendaTaskParams;
import dietgerpieters.werkstuk.Models.TussenTabel;
import dietgerpieters.werkstuk.Models.Wedstrijd;
import dietgerpieters.werkstuk.R;
import dietgerpieters.werkstuk.Threading.AgendaTask;

/**
 * Created by Dietger (Pantani) on 31/12/2017.
 */

public class DetailTabFragment extends Fragment {

    private ContentResolver mCr;
    private static AppDatabase mDb;
    private static Button inschrBtn;
    private static Button uitschrBtn;
    private static Wedstrijd w;
    private static String parentName;
    private ViewGroup viewGroup;
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    private OnFragmentInteractionListener mListener;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.viewGroup = container;
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_tab, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        initComponents();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public void uitschrijvingWedstrijd(View v){

        if(mDb.usersRacesDAO().loadRelation(mDb.userDAO().loadActiveUser().getId(), w.getId()) != null) {

            mDb.usersRacesDAO().deleteRelation2(mDb.userDAO().loadActiveUser().getId(), w.getId());
            mDb.userDAO().loadActiveUser().getIngeschrevenWedstrijden().remove(w);

            inschrBtn.setEnabled(true);
            uitschrBtn.setEnabled(false);
        } else {
            Toast.makeText(getActivity(), "Je  bent al uitgeschreven voor deze wedstrijd", Toast.LENGTH_SHORT).show();
        }

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    //agenda toevoegen/////














                } else {



                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    public  void inschrijvingWedstrijd(View v){

        TussenTabel t = null;


        if(mDb.usersRacesDAO().loadRelation(mDb.userDAO().loadActiveUser().getId(), w.getId()) == null){

            t = new TussenTabel(mDb.userDAO().loadActiveUser().getId(), w.getId());

            mDb.usersRacesDAO().insertRelation(t);
            mDb.userDAO().loadActiveUser().getIngeschrevenWedstrijden().add(w);
            inschrBtn.setEnabled(false);
            uitschrBtn.setEnabled(true);

            if (WedstrijdController.isInternetAvailable()) {
                AgendaTask agendaTask = new AgendaTask();


                agendaTask.execute(new MyAgendaTaskParams(getActivity(), w, getActivity().getApplicationContext().getContentResolver(), 1, w.getVertrekDatum().getTime(), w.getVertrekDatum().getTime(), Calendar.getInstance(), Calendar.getInstance()));
            }





        } else {
            Toast.makeText(getActivity(), "Je  bent al ingeschreven voor deze wedstrijd", Toast.LENGTH_SHORT).show();

        }

     /*   if (mDb.wedstrijdDAO().getWedstrijd(w.getId()) == null) {



            mDb.wedstrijdDAO().insertWedstrijd(w);




            inschrBtn.setEnabled(false);
            uitschrBtn.setEnabled(true);
        } else {
            Toast.makeText(WedstrijdDetailActivity.this, "Je  bent al ingeschreven voor deze wedstrijd", Toast.LENGTH_SHORT).show();
        }*/

    }
    private void initComponents(){



        mDb = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class, "wedstrijdDB").allowMainThreadQueries().build();

        Bundle extras = getActivity().getIntent().getExtras();
        w = (Wedstrijd) extras.getSerializable("wedstrijd");
        parentName = extras.getString("naam");


        TextView textView = getActivity().findViewById(R.id.titelValue);
        textView.setText(w.getTitel());

        this.inschrBtn = (Button) getActivity().findViewById(R.id.inschrijvingBtn);
        this.uitschrBtn = (Button) getActivity().findViewById(R.id.uitchrijvingBtn);




        if (mDb.usersRacesDAO().loadRelation(mDb.userDAO().loadActiveUser().getId(), w.getId()) == null) {
            inschrBtn.setEnabled(true);
            uitschrBtn.setEnabled(false);
        } else {
            inschrBtn.setEnabled(false);
            uitschrBtn.setEnabled(true);
            Toast.makeText(getActivity(), "Je  bent al ingeschreven voor deze wedstrijd", Toast.LENGTH_SHORT).show();
        }

    }

}
