package dietgerpieters.werkstuk.Fragments;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import dietgerpieters.werkstuk.Activities.InschrijvingenActivity;
import dietgerpieters.werkstuk.Activities.WedstrijdDetailActivity;
import dietgerpieters.werkstuk.Adapters.WedstrijdenAdapter;
import dietgerpieters.werkstuk.Database.AppDatabase;
import dietgerpieters.werkstuk.Models.Wedstrijd;
import dietgerpieters.werkstuk.R;

/**
 * Created by Dietger (Pantani) on 28/12/2017.
 */

public class SelectieWedstrijdenFragment extends ListFragment implements AdapterView.OnItemClickListener {
    private AppDatabase mDb;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_wedstrijden_overzicht_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDb = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class, "wedstrijdDB").allowMainThreadQueries().build();

        ListView listview = (ListView) getActivity().findViewById(android.R.id.list);

        ArrayList<Wedstrijd> myList = (ArrayList<Wedstrijd>) mDb.wedstrijdDAO().loadAllWedstrijden();

        WedstrijdenAdapter wAdapter = new WedstrijdenAdapter(getActivity().getApplicationContext(), R.layout.wedstrijd_row, myList);
        listview.setAdapter(wAdapter);
        listview.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getActivity(), "Iiiiiiiiiitem: " + i, Toast.LENGTH_SHORT).show();
        ArrayList<Wedstrijd> myList = (ArrayList<Wedstrijd>) mDb.wedstrijdDAO().loadAllWedstrijden();


        Intent intent = new Intent(getActivity(), WedstrijdDetailActivity.class);
        intent.putExtra("wedstrijd", myList.get(i));
        startActivity(intent);

    }
}
