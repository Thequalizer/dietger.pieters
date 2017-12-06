package dietgerpieters.werkstuk;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";
    private TextView datumText;
    private DatePickerDialog.OnDateSetListener mDateListener;
    private Button datumButton;
    private Button btnHit;
    private JsonTask jsonTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koers_overzicht);
        datumText = (TextView) findViewById(R.id.datumSelectie);
        datumButton = (Button) findViewById(R.id.datumVanKnop);
        btnHit = (Button) findViewById(R.id.getFromJson);


        mDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Log.d(TAG, "onDateSet: " + i + "/" + i1 + "/" + i2);
                i1 = i1 + 1;
                datumText.setText(i2 + "/" + i1 + "/" + i);
            }
        };

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
        JSONArray jsonArray = WedstrijdController.getWedstrijdenProfs("https://api.myjson.com/bins/10hi0f");

        for (int i=0; i < jsonArray.length(); i++)
        {
            try {
                JSONObject oneObject = jsonArray.getJSONObject(i);
                // Pulling items from the array
                String oneObjectsItem = oneObject.getString("wedstrijdNaam");
                String oneObjectsItem2 = oneObject.getString("afstand");

                System.out.println(oneObjectsItem);
                System.out.println(oneObjectsItem2);

            } catch (JSONException e) {
                // Oops
            }
        }
    }















}
