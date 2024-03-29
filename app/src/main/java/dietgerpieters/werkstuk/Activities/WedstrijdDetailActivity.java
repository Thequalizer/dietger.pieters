package dietgerpieters.werkstuk.Activities;

import android.arch.persistence.room.Room;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.concurrent.ExecutionException;

import dietgerpieters.werkstuk.Adapters.DetailPagerAdapter;
import dietgerpieters.werkstuk.Database.AppDatabase;
import dietgerpieters.werkstuk.Fragments.DetailTabFragment;
import dietgerpieters.werkstuk.Fragments.MapsTabFragment;
import dietgerpieters.werkstuk.Listeners.TabListener;
import dietgerpieters.werkstuk.Models.TussenTabel;
import dietgerpieters.werkstuk.Models.Wedstrijd;
import dietgerpieters.werkstuk.R;
import dietgerpieters.werkstuk.Threading.InitMapTask;

public class WedstrijdDetailActivity extends AppCompatActivity implements DetailTabFragment.OnFragmentInteractionListener, MapsTabFragment.OnFragmentInteractionListener {

    private AppDatabase mDb;
    Wedstrijd w;
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    ActionBar.Tab Tab1, Tab2;
    Fragment fragmentMapsTab = new MapsTabFragment();
    DetailTabFragment fragmentDetailTab = new DetailTabFragment();


    private String parentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);


        Bundle extras = getIntent().getExtras();
        w = (Wedstrijd) extras.getSerializable("wedstrijd");


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Details"));
        tabLayout.addTab(tabLayout.newTab().setText("Locatie"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final DetailPagerAdapter detailPagerAdapter = new DetailPagerAdapter(getSupportFragmentManager(), w);

        viewPager.setAdapter(detailPagerAdapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);

        setSupportActionBar(myToolbar);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

/*

        inschrBtn = (Button) findViewById(R.id.inschrijvingBtn);
        uitschrBtn = (Button) findViewById(R.id.uitchrijvingBtn);

*/


        extras.getSerializable("wedstrijd");
        parentName = extras.getString("naam");




        /*w = (Wedstrijd) getIntent().getSerializableExtra("wedstrijd");
        parentName = getIntent().getStringExtra("naam");*/
        this.mDb = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "wedstrijdDB").allowMainThreadQueries().build();

        //initButtons();


        /*TextView textView = findViewById(R.id.titelValue);
        textView.setText(w.getTitel());
*/
       /* Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar2);
        setSupportActionBar(myToolbar);*/

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);


                if (parentName != null) {
                    upIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);


                    if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                        // This activity is NOT part of this app's task, so create a new task
                        // when navigating up, with a synthesized back stack.
                        TaskStackBuilder.create(this)
                                // Add all of this activity's parents to the back stack
                                .addNextIntentWithParentStack(upIntent)
                                // Navigate up to the closest parent
                                .startActivities();
                    } else {
                        // This activity is part of this app's task, so simply
                        // navigate up to the logical parent activity.

                        NavUtils.navigateUpTo(this, upIntent);
                    }
                    return true;
                } else {
                    Intent h = new Intent(this, InschrijvingenActivity.class);
                    h.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(h);
                    return true;
                }
        }
        return super.onOptionsItemSelected(item);
    }


    public void inschrijvingWedstrijd(View v) {

        fragmentDetailTab.inschrijvingWedstrijd(v);
      /*  TussenTabel t = null;

        if(mDb.usersRacesDAO().loadRelation(mDb.userDAO().loadActiveUser().getId(), w.getId()) == null){

            t = new TussenTabel(mDb.userDAO().loadActiveUser().getId(), w.getId());

            mDb.usersRacesDAO().insertRelation(t);
            mDb.userDAO().loadActiveUser().getIngeschrevenWedstrijden().add(w);
            inschrBtn.setEnabled(false);
            uitschrBtn.setEnabled(true);
        } else {
            Toast.makeText(WedstrijdDetailActivity.this, "Je  bent al ingeschreven voor deze wedstrijd", Toast.LENGTH_SHORT).show();

        }
*/
     /*   if (mDb.wedstrijdDAO().getWedstrijd(w.getId()) == null) {



            mDb.wedstrijdDAO().insertWedstrijd(w);




            inschrBtn.setEnabled(false);
            uitschrBtn.setEnabled(true);
        } else {
            Toast.makeText(WedstrijdDetailActivity.this, "Je  bent al ingeschreven voor deze wedstrijd", Toast.LENGTH_SHORT).show();
        }*/

    }

    public void uitschrijvingWedstrijd(View v) {
        fragmentDetailTab.uitschrijvingWedstrijd(v);

        /*if(mDb.usersRacesDAO().loadRelation(mDb.userDAO().loadActiveUser().getId(), w.getId()) != null) {

            mDb.usersRacesDAO().deleteRelation2(mDb.userDAO().loadActiveUser().getId(), w.getId());
            mDb.userDAO().loadActiveUser().getIngeschrevenWedstrijden().remove(w);


            inschrBtn.setEnabled(true);
            uitschrBtn.setEnabled(false);
        } else {
            Toast.makeText(WedstrijdDetailActivity.this, "Je  bent al uitgeschreven voor deze wedstrijd", Toast.LENGTH_SHORT).show();
        }
*/
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
