package dietgerpieters.werkstuk.Activities;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import dietgerpieters.werkstuk.Database.AppDatabase;
import dietgerpieters.werkstuk.Fragments.PreviewRegisterInputFragment;
import dietgerpieters.werkstuk.Fragments.RegisterInputFragment;
import dietgerpieters.werkstuk.Models.User;
import dietgerpieters.werkstuk.R;

public class RegisterActivity extends AppCompatActivity implements RegisterInputFragment.OnUserRegisterListener{

    private AppDatabase mDb;
    private NumberPicker np;
    private Button registerBtn;
    private EditText naamEdit;
    private EditText achternaamEdit;
    private Spinner geslachtSpnr;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initComponents();


    }

    private void initComponents(){

        this.mDb = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "wedstrijdDB").allowMainThreadQueries().build();

        np = (NumberPicker) findViewById(R.id.input_age);

        //Populate NumberPicker values from minimum and maximum value range
        //Set the minimum value of NumberPicker
        np.setMinValue(5);
        //Specify the maximum value/number of NumberPicker
        np.setMaxValue(100);

        //Gets whether the selector wheel wraps when reaching the min/max value.
        np.setWrapSelectorWheel(true);

        //Set a value change listener for NumberPicker
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //doe er iets mee
            }
        });



        registerBtn = (Button) findViewById(R.id.send_register);
        naamEdit = (EditText) findViewById(R.id.input_naam);
        achternaamEdit = (EditText) findViewById(R.id.input_achternaam);
        geslachtSpnr = (Spinner) findViewById(R.id.input_gender);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.geslacht_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        geslachtSpnr.setAdapter(adapter);



    }

    @Override
    public void onRegisterButtonListener(View v) {
        PreviewRegisterInputFragment pFragment = (PreviewRegisterInputFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_input_preview);

        if (np.getValue() == 0 || naamEdit.getText().toString().trim().length() == 0|| geslachtSpnr.getSelectedItem().toString() == null || achternaamEdit.getText().toString().trim().length() == 0){
            Toast.makeText(RegisterActivity.this, "Niet alle velden zijn juist ingevuld", Toast.LENGTH_SHORT).show();
            pFragment.disableButton();
        } else {
            String naam = naamEdit.getText().toString();
            String achternaam = achternaamEdit.getText().toString();
            int leeftijd = np.getValue();
            String geslacht = geslachtSpnr.getSelectedItem().toString();

            pFragment.updateValues(naam, achternaam, leeftijd, geslacht);


        }
    }

    public void doRegister(View v){
        TextView naam = (TextView) findViewById(R.id.naam_info);
        String tNaam = naam.getText().toString();
        TextView aNaam = (TextView) findViewById(R.id.achternaam_info);
        String tANaam = aNaam.getText().toString();
        TextView leeftijd = (TextView) findViewById(R.id.leeftijd_info);
        int tLeeftijd = Integer.parseInt(leeftijd.getText().toString());
        TextView geslacht = (TextView) findViewById(R.id.geslacht_info);
        String tGeslacht = geslacht.getText().toString();

        User user = new User(tNaam, tANaam, tLeeftijd, tGeslacht);


        if (mDb.userDAO().getUserByName(user.getNaam()) == null){
            mDb.userDAO().insertUser(user);

            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            intent.putExtra("Username", user.getNaam());
            startActivity(intent);

        } else {
            Toast.makeText(RegisterActivity.this, "Een user met deze naam bestaat al", Toast.LENGTH_SHORT).show();
        }
    }
}
