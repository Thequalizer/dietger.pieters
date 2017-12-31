package dietgerpieters.werkstuk.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import dietgerpieters.werkstuk.Adapters.WedstrijdenAdapter;
import dietgerpieters.werkstuk.Models.Wedstrijd;
import dietgerpieters.werkstuk.R;

public class WedstrijdenOverzichtActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wedstrijden_overzicht);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar1);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final ArrayList<Wedstrijd> myList = (ArrayList<Wedstrijd>) getIntent().getSerializableExtra("wedstrijdenLijst");

        for (int i=0; i < myList.size(); i++)
        {


            Wedstrijd w = myList.get(i);
            // Pulling items from the array


            System.out.println(w.getAantalDeelnemers());
            System.out.println(w.getTitel());
            System.out.println(w.getVertrekDatum());
            System.out.println("New intent");



        }




        ListView listview = (ListView) findViewById(R.id.listview);


        WedstrijdenAdapter wAdapter = new WedstrijdenAdapter(getApplicationContext(), R.layout.wedstrijd_row, myList);
        listview.setAdapter(wAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              /*  Toast.makeText(getApplicationContext(),
                        "Click ListItem Number " + i, Toast.LENGTH_SHORT)
                        .show();*/

                Intent intent = new Intent(WedstrijdenOverzichtActivity.this, WedstrijdDetailActivity.class);
                Bundle extras = new Bundle();
                extras.putSerializable("wedstrijd",myList.get(i));
                extras.putString("naam", "WedstrijdenOverzichtActivity");
                intent.putExtras(extras);
                startActivity(intent);


            }
        });

    }

    private void initLayout(){
        if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            Toast.makeText(this, "Large screen",Toast.LENGTH_LONG).show();

        }
        else{

            
        }


    }

}
