package dietgerpieters.werkstuk.Activities;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import dietgerpieters.werkstuk.Adapters.WedstrijdenAdapter;
import dietgerpieters.werkstuk.Database.AppDatabase;
import dietgerpieters.werkstuk.Models.Wedstrijd;
import dietgerpieters.werkstuk.R;

public class InschrijvingenActivity extends AppCompatActivity {

    private AppDatabase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inschrijvingen);


        mDb = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "wedstrijdDB").allowMainThreadQueries().build();





                final ListView listview = (ListView) findViewById(R.id.listview);

                final ArrayList<Wedstrijd> myList = (ArrayList<Wedstrijd>) mDb.wedstrijdDAO().loadAllWedstrijden();

                final WedstrijdenAdapter wAdapter = new WedstrijdenAdapter(getApplicationContext(), R.layout.wedstrijd_row, myList);
                listview.setAdapter(wAdapter);
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(getApplicationContext(),
                                "Click ListItem Number " + i, Toast.LENGTH_SHORT)
                                .show();

                        Intent intent = new Intent(InschrijvingenActivity.this, WedstrijdDetailActivity.class);
                        intent.putExtra("wedstrijd", myList.get(i));
                        startActivity(intent);


                    }
                });

            }


}

