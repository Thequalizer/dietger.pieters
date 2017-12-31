package dietgerpieters.werkstuk.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

import dietgerpieters.werkstuk.TypeConverters.CategorieConverter;
import dietgerpieters.werkstuk.TypeConverters.DateConverter;

/**
 * Created by User on 6/12/2017.
 */

@Entity(tableName = "users")
public class User implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String naam;
    private String achternaam;
    private int leeftijd;
    private String geslacht;

    private boolean ingelogd;

    public User(String naam, String achternaam, int leeftijd, String geslacht) {
        this.naam = naam;
        this.achternaam = achternaam;
        this.leeftijd = leeftijd;
        this.geslacht = geslacht;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public int getLeeftijd() {
        return leeftijd;
    }

    public void setLeeftijd(int leeftijd) {
        this.leeftijd = leeftijd;
    }

    public String getGeslacht() {
        return geslacht;
    }

    public void setGeslacht(String geslacht) {
        this.geslacht = geslacht;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIngelogd() {
        return ingelogd;
    }

    public void setIngelogd(boolean ingelogd) {
        this.ingelogd = ingelogd;
    }

}

