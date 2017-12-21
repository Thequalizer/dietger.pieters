package dietgerpieters.werkstuk.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import dietgerpieters.werkstuk.Controllers.WedstrijdController;
import dietgerpieters.werkstuk.Models.Wedstrijd;
import dietgerpieters.werkstuk.R;
import dietgerpieters.werkstuk.Threading.JsonTask;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";
    private TextView datumText;
    private DatePickerDialog.OnDateSetListener mDateListener;
    private Button datumButton;
    private Button btnHit;
    private JsonTask jsonTask;

   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koers_overzicht);
        datumText = (TextView) findViewById(R.id.datumSelectie);
        datumButton = (Button) findViewById(R.id.datumVanKnop);
        btnHit = (Button) findViewById(R.id.getFromJson);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        mDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Log.d(TAG, "onDateSet: " + i + "/" + i1 + "/" + i2);
                i1 = i1 + 1;
                datumText.setText(i2 + "/" + i1 + "/" + i);
            }
        };

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public void showDialogCalender(View v){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateListener, year, month, day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();

    }

    public void showWedstrijden(View v){
        List<Wedstrijd> wedstrijdenProfs = WedstrijdController.getWedstrijdenProfs("https://api.myjson.com/bins/10hi0f");

        wedstrijdenProfs = (ArrayList<Wedstrijd>) wedstrijdenProfs;

        for (int i=0; i < wedstrijdenProfs.size(); i++)
        {


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
    }















}
