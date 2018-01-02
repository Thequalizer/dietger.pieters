package dietgerpieters.werkstuk.Models;

import android.app.Activity;
import android.content.ContentResolver;

import java.util.Calendar;

/**
 * Created by User on 2/01/2018.
 */

public class MyAgendaTaskParams {
    private ContentResolver contentResolver;
    private long calID;
    private long startMillis;
    private long endMillis;
    private Calendar endTime;
    private Calendar beginTime;
    private Wedstrijd wedstrijd;
    private Activity activity;

    public MyAgendaTaskParams(Activity activity, Wedstrijd wedstrijd, ContentResolver contentResolver, long calID, long startMillis, long endMillis, Calendar endTime, Calendar beginTime) {
        this.contentResolver = contentResolver;
        this.calID = calID;
        this.startMillis = startMillis;
        this.endMillis = endMillis;
        this.endTime = endTime;
        this.beginTime = beginTime;
        this.wedstrijd = wedstrijd;
        this.activity = activity;
    }

    public ContentResolver getContentResolver() {
        return contentResolver;
    }

    public void setContentResolver(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public long getCalID() {
        return calID;
    }

    public void setCalID(long calID) {
        this.calID = calID;
    }

    public long getStartMillis() {
        return startMillis;
    }

    public void setStartMillis(long startMillis) {
        this.startMillis = startMillis;
    }

    public long getEndMillis() {
        return endMillis;
    }

    public void setEndMillis(long endMillis) {
        this.endMillis = endMillis;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public Calendar getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Calendar beginTime) {
        this.beginTime = beginTime;
    }

    public Wedstrijd getWedstrijd() {
        return wedstrijd;
    }

    public void setWedstrijd(Wedstrijd wedstrijd) {
        this.wedstrijd = wedstrijd;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
