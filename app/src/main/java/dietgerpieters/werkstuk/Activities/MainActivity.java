package dietgerpieters.werkstuk.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
import dietgerpieters.werkstuk.Models.Wedstrijd;
import dietgerpieters.werkstuk.R;
import dietgerpieters.werkstuk.Threading.JsonTask;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private TextView datumText;
    private TextView datumTextTot;

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

    private void checkFields(){
        if (date1 && date2 && categorieSpinner.getSelectedItem().toString() != null){
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
    public void toonInschrijvingen(View v){
        Intent intent = new Intent(MainActivity.this, InschrijvingenActivity.class);
        startActivity(intent);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, myToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);










        categorieSpinner = (Spinner) findViewById(R.id.categorie_spinner);

        datumText = (TextView) findViewById(R.id.datumSelectie);
        datumTextTot = (TextView) findViewById(R.id.tijdTot);

        datumButton = (Button) findViewById(R.id.datumVanKnop);

        btnHit = (Button) findViewById(R.id.getFromJson);

        btnHit.setEnabled(false);
        setSupportActionBar(myToolbar);

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

    public void showWedstrijden(View v) {
        String datumVan = datumText.getText().toString();
        String datumTot = datumTextTot.getText().toString();
        Date date1 = new Date();
        Date date2 = new Date();


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            date1 = dateFormat.parse(datumVan);
            date2 = dateFormat.parse(datumTot);


        } catch (Exception e) {
            //java.text.ParseException: Unparseable date: Geting error
            System.out.println("Excep" + e);
        }

        if(date1.before(date2) || date1.getTime() == date2.getTime()) {

            String categorie = categorieSpinner.getSelectedItem().toString();
            String url = "https://api.myjson.com/bins/z5xz3";

            List<Wedstrijd> wedstrijdenProfs = WedstrijdController.getWedstrijdenMetDatum(url, date1, date2, categorie);

            wedstrijdenProfs = (ArrayList<Wedstrijd>) wedstrijdenProfs;

            for (int i = 0; i < wedstrijdenProfs.size(); i++) {


                Wedstrijd w = wedstrijdenProfs.get(i);
                // Pulling items from the array


                System.out.println(w.getAantalDeelnemers());
                System.out.println(w.getTitel());
                System.out.println(w.getVertrekDatum());


            }


            Intent myIntent = new Intent(MainActivity.this, WedstrijdenOverzichtActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("wedstrijdenLijst", (Serializable) wedstrijdenProfs);

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

        if (id == R.id.nav_mijn_ins){
            toonInschrijvingen(new View(this));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }


}
