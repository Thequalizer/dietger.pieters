package dietgerpieters.werkstuk.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import dietgerpieters.werkstuk.TypeConverters.CategorieConverter;
import dietgerpieters.werkstuk.TypeConverters.DateConverter;

/**
 * Created by User on 6/12/2017.
 */

@Entity(tableName = "wedstrijden")
@TypeConverters({DateConverter.class, CategorieConverter.class})
public class Wedstrijd implements Serializable {

    private String titel;
    private double afstand;
    private int maxAantalDeelnemers;
    private int aantalDeelnemers;
    private String vertrekAdres;
    private String aankomstAdres;
    private Categorie categorie;

    @PrimaryKey
    private int id;

    public String getVertrekAdres() {
        return vertrekAdres;
    }

    public void setVertrekAdres(String vertrekAdres) {
        this.vertrekAdres = vertrekAdres;
    }

    public String getAankomstAdres() {
        return aankomstAdres;
    }

    public void setAankomstAdres(String aankomstAdres) {
        this.aankomstAdres = aankomstAdres;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }


    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public double getAfstand() {
        return afstand;
    }

    public void setAfstand(double afstand) {
        this.afstand = afstand;
    }

    public int getAantalDeelnemers() {
        return aantalDeelnemers;
    }

    public void setAantalDeelnemers(int aantalDeelnemers) {
        this.aantalDeelnemers = aantalDeelnemers;
    }

    public Date getVertrekDatum() {
        return vertrekDatum;
    }

    public void setVertrekDatum(Date vertrekDatum) {
        this.vertrekDatum = vertrekDatum;
    }

    public boolean isAfgelopen() {
        return afgelopen;
    }

    public void setAfgelopen(boolean afgelopen) {
        this.afgelopen = afgelopen;
    }
/*
    public HashMap getUitslag() {
        return uitslag;
    }

    public void setUitslag(HashMap uitslag) {
        this.uitslag = uitslag;
    }*/

    private Date vertrekDatum;
    private boolean afgelopen;
    //private HashMap uitslag;

    public Wedstrijd() {

    }

    public Wedstrijd(String wTitel, double wAfstand, int wWantalDeelnemers, Date wVertrekUur, Categorie categorie, int id) {
        this.id = id;
        this.titel = wTitel;
        this.aantalDeelnemers = wWantalDeelnemers;
        this.afstand = wAfstand;
        this.vertrekDatum = wVertrekUur;
        this.afgelopen = false;
        this.categorie = categorie;
    }

    public void stopWedstrijd() {
        this.afgelopen = true;
    }

    public int getMaxAantalDeelnemers() {
        return maxAantalDeelnemers;
    }

    public void setMaxAantalDeelnemers(int maxAantalDeelnemers) {
        this.maxAantalDeelnemers = maxAantalDeelnemers;
    }


    public enum Categorie {PROFS, BELOFTEN, JUNIOREN, ELITEZC, NIEUWELINGEN, ASPIRANTEN}
}

