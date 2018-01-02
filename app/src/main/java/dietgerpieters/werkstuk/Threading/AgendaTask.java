package dietgerpieters.werkstuk.Threading;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import dietgerpieters.werkstuk.Models.MyAgendaTaskParams;
import dietgerpieters.werkstuk.Models.MyTaskParam;


/**
 * Created by User on 29/11/2017.
 */

public class AgendaTask extends AsyncTask<MyAgendaTaskParams, String, Void> {


    private MyAgendaTaskParams myAgendaTaskParams;
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

 /*  protected void onPreExecute() {
        super.onPreExecute();

        pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("Please wait");
        pd.setCancelable(false);
        pd.show();
    }*/

    protected Void doInBackground(MyAgendaTaskParams... params) {

        myAgendaTaskParams = params[0];

        int permissionCheck = ContextCompat.checkSelfPermission(myAgendaTaskParams.getActivity(),
                Manifest.permission.WRITE_CALENDAR);

        ContentResolver cr = myAgendaTaskParams.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, myAgendaTaskParams.getStartMillis());
        values.put(CalendarContract.Events.DTEND, myAgendaTaskParams.getEndMillis());
        values.put(CalendarContract.Events.TITLE, "RaceDay!");
        values.put(CalendarContract.Events.DESCRIPTION, myAgendaTaskParams.getWedstrijd().getTitel());
        values.put(CalendarContract.Events.CALENDAR_ID, myAgendaTaskParams.getCalID());
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "Belgium/Brussels");
        if (ActivityCompat.checkSelfPermission(myAgendaTaskParams.getActivity().getApplicationContext(), Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(myAgendaTaskParams.getActivity(),
                    new String[]{Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);

// get the event ID that is the last element in the Uri
        long eventID = Long.parseLong(uri.getLastPathSegment());
//
// ... do something with event ID
//
//

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

    }

}
