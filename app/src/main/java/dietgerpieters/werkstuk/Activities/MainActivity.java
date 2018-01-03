package dietgerpieters.werkstuk.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dietgerpieters.werkstuk.Controllers.WedstrijdController;
import dietgerpieters.werkstuk.Database.AppDatabase;
import dietgerpieters.werkstuk.Fragments.ChooseLoginRegisterFragment;
import dietgerpieters.werkstuk.Models.Wedstrijd;
import dietgerpieters.werkstuk.R;
import dietgerpieters.werkstuk.Threading.JsonTask;
import dietgerpieters.werkstuk.TypeConverters.CategorieConverter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private TextView datumText;
    private TextView datumTextTot;

    private AppDatabase mDb;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ArrayAdapter mStringAdapter;
    private String[] mStringOfPlanets = new String[5];


    boolean date1, date2 = false;
    private DatePickerDialog.OnDateSetListener mDateListener;
    private DatePickerDialog.OnDateSetListener mDateListener2;

    private Button datumButton;
    private Button btnHit;
    private JsonTask jsonTask;
    private Spinner categorieSpinner;

    private ActionBarDrawerToggle mDrawerToggle;

    private void checkFields() {
        if (date1 && date2 && categorieSpinner.getSelectedItem().toString() != null) {
            btnHit.setEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        switch (item.getItemId()) {
            case R.id.aboutus:
                Intent launchIntent = new Intent(MainActivity.this, InformatieActivity.class);
                startActivityForResult(launchIntent, 0);

                // User chose the "Settings" item, show the app settings UI...
                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }


    }

    public void toonInschrijvingen(View v) {
        Intent intent = new Intent(MainActivity.this, InschrijvingenActivity.class);
        startActivity(intent);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        }


        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbarmain);


        this.mDb = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "wedstrijdDB").allowMainThreadQueries().build();


        if (WedstrijdController.isInternetAvailable()) {


            try {
                for (Wedstrijd wed1 : WedstrijdController.initWedstrijdDB("https://api.myjson.com/bins/17jwf7")) {
                    if (mDb.wedstrijdDAO().getWedstrijd(wed1.getId()) == null) {
                        mDb.wedstrijdDAO().insertWedstrijd(wed1);
                    }

                }
            } catch (NullPointerException e) {
                System.out.println("No Wedstrijden found");
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, myToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();


        setTitle("CycleDroid");


        initNavigationDrawer();


        categorieSpinner = (Spinner) findViewById(R.id.categorie_spinner);

        datumText = (TextView) findViewById(R.id.datumSelectie);
        datumTextTot = (TextView) findViewById(R.id.tijdTot);

        datumButton = (Button) findViewById(R.id.datumVanKnop);

        btnHit = (Button) findViewById(R.id.getFromJson);

        btnHit.setEnabled(false);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setIcon(R.drawable.ic_actionbarlogo);


        Spinner spinner = (Spinner) findViewById(R.id.categorie_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        mDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {


                date1 = true;


                Log.d(TAG, "onDateSet: " + i + "/" + i1 + "/" + i2);
                i1 = i1 + 1;
                datumText.setText(i2 + "/" + i1 + "/" + i);
                checkFields();
            }
        };
        mDateListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                date2 = true;

                Log.d(TAG, "onDateSet: " + i + "/" + i1 + "/" + i2);
                i1 = i1 + 1;
                datumTextTot.setText(i2 + "/" + i1 + "/" + i);
                checkFields();
            }

        };


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public void showDialogCalender(View v) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateListener, year, month, day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();

    }

    public void showDialogCalender2(View v) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateListener2, year, month, day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();

    }

    private void initNavigationDrawer() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        String naam = mDb.userDAO().loadActiveUser().getNaam();
        naam += " " + mDb.userDAO().loadActiveUser().getAchternaam();


        View headerView = navigationView.getHeaderView(0);
        TextView textView_naam_menu = (TextView) headerView.findViewById(R.id.username_menu);


        textView_naam_menu.setText(naam);


    }

    public void showWedstrijden(View v) {
        String datumVan = datumText.getText().toString();
        String datumTot = datumTextTot.getText().toString();
        Date date1 = new Date();
        Date date2 = new Date();
        String categorie = categorieSpinner.getSelectedItem().toString();


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            date1 = dateFormat.parse(datumVan);
            date2 = dateFormat.parse(datumTot);


        } catch (Exception e) {
            //java.text.ParseException: Unparseable date: Geting error
            System.out.println("Excep" + e);
        }

        if (date1.before(date2) || date1.getTime() == date2.getTime()) {

            categorie = categorieSpinner.getSelectedItem().toString();
            String url = "https://api.myjson.com/bins/17jwf7";

            List<Wedstrijd> wedstrijden = new ArrayList<>();

            if (WedstrijdController.isInternetAvailable())
                wedstrijden = WedstrijdController.getWedstrijdenMetDatum(url, date1, date2, categorie);
            else
                wedstrijden = mDb.wedstrijdDAO().loadAllOnCategorie(CategorieConverter.toCategorie(categorie));


            Intent myIntent = new Intent(MainActivity.this, WedstrijdenOverzichtActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("wedstrijdenLijst", (Serializable) wedstrijden);

            myIntent.putExtras(bundle); //Optional parameters

            startActivity(myIntent);
        } else {

            Toast.makeText(MainActivity.this, "Ongeldige datum selectie", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        switch (id) {
            case R.id.nav_mijn_ins:
                toonInschrijvingen(new View(this));
                break;
            case R.id.nav_mijn_logout:
                checkSureLogout();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    private void checkSureLogout() {

        // 1. Instantiate an AlertDialog.Builder with its constructor
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Chain together various setter methods to set the dialog characteristics
        //R.string.dialog_title
        builder.setMessage("Ben je zeker dat je wilt afmelden")
                .setTitle("Afmelden");

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();


        // Add the buttons
        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mDb.userDAO().logoutUser(mDb.userDAO().loadActiveUser().getNaam());
                Intent intent = new Intent(MainActivity.this, LauncherActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Neen", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.show();
    }

}
