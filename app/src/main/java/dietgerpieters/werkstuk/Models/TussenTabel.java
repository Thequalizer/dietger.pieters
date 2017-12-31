package dietgerpieters.werkstuk.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by User on 6/12/2017.
 */

@Entity(tableName = "usersraces")
public class TussenTabel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userID;
    private int wedstrijdID;

    public TussenTabel(int userID, int wedstrijdID) {
        this.userID = userID;
        this.wedstrijdID = wedstrijdID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWedstrijdID() {
        return wedstrijdID;
    }

    public void setWedstrijdID(int wedstrijdID) {
        this.wedstrijdID = wedstrijdID;
    }


}

