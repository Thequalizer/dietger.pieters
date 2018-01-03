package dietgerpieters.werkstuk.Activities;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import dietgerpieters.werkstuk.Controllers.WedstrijdController;
import dietgerpieters.werkstuk.Database.AppDatabase;
import dietgerpieters.werkstuk.Fragments.ChooseLoginRegisterFragment;
import dietgerpieters.werkstuk.Models.Wedstrijd;
import dietgerpieters.werkstuk.R;

public class LauncherActivity extends FragmentActivity {

    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        this.mDb = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "wedstrijdDB").allowMainThreadQueries().build();


        checkForActiveLogin(savedInstanceState);
    }

    private void checkForActiveLogin(Bundle savedInstanceState) {

        if (mDb.userDAO().loadActiveUser() != null) {
            Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            // Check that the activity is using the layout version with
            // the fragment_container FrameLayout
            if (findViewById(R.id.fragment_container) != null) {

                // However, if we're being restored from a previous state,
                // then we don't need to do anything and should return or else
                // we could end up with overlapping fragments.
                if (savedInstanceState != null) {
                    return;
                }

                // Create a new Fragment to be placed in the activity layout
                ChooseLoginRegisterFragment firstFragment = new ChooseLoginRegisterFragment();


                // Add the fragment to the 'fragment_container' FrameLayout
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, firstFragment).commit();
            }
        }
    }

    public void doLogin(View v) {
        Intent intent = new Intent(LauncherActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void doRegister(View v) {
        Intent intent = new Intent(LauncherActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

}
