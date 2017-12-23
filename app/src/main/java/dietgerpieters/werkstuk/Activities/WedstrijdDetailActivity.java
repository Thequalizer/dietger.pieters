package dietgerpieters.werkstuk.Activities;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import dietgerpieters.werkstuk.Database.AppDatabase;
import dietgerpieters.werkstuk.Models.Wedstrijd;
import dietgerpieters.werkstuk.R;

public class WedstrijdDetailActivity extends AppCompatActivity {

    private AppDatabase mDb;
    private Button inschrBtn;
    private Button uitschrBtn;
    Wedstrijd w;
    private String parentName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wedstrijd_detail);

        inschrBtn = (Button) findViewById(R.id.inschrijvingBtn);
        uitschrBtn = (Button) findViewById(R.id.uitchrijvingBtn);

        w = (Wedstrijd) getIntent().getSerializableExtra("wedstrijd");
        parentName = getIntent().getStringExtra("naam");
        this.mDb = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "wedstrijdDB").allowMainThreadQueries().build();


        if (mDb.wedstrijdDAO().getWedstrijd(w.getId()) == null) {
            uitschrBtn.setEnabled(false);
            inschrBtn.setEnabled(true);
        } else {
            uitschrBtn.setEnabled(true);
            inschrBtn.setEnabled(false);
        }


        TextView textView = findViewById(R.id.titelValue);
        textView.setText(w.getTitel());

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar2);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);


                if(parentName != null) {
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

    public void inschrijvingWedstrijd(View v){
        if (mDb.wedstrijdDAO().getWedstrijd(w.getId()) == null) {
            mDb.wedstrijdDAO().insertWedstrijd(w);
            inschrBtn.setEnabled(false);
            uitschrBtn.setEnabled(true);
        } else {
            Toast.makeText(WedstrijdDetailActivity.this, "Je  bent al ingeschreven voor deze wedstrijd", Toast.LENGTH_SHORT).show();
        }

    }
    public void uitschrijvingWedstrijd(View v){
        if (mDb.wedstrijdDAO().getWedstrijd(w.getId()) != null) {
            mDb.wedstrijdDAO().deleteWedstrijd(w);
            inschrBtn.setEnabled(true);
            uitschrBtn.setEnabled(false);
        } else {
            Toast.makeText(WedstrijdDetailActivity.this, "Je  bent al uitgeschreven voor deze wedstrijd", Toast.LENGTH_SHORT).show();
        }

    }
}
