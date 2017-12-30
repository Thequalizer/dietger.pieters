package dietgerpieters.werkstuk.TypeConverters;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

import dietgerpieters.werkstuk.Models.Wedstrijd;

/**
 * Created by Dietger (Pantani) on 22/12/2017.
 */

public class CategorieConverter {
    @TypeConverter
    public static Wedstrijd.Categorie toCategorie(String categorie){
        switch (categorie){
            case "PROFS":
                return Wedstrijd.Categorie.PROFS;
            case "ELITEZC":
                return Wedstrijd.Categorie.ELITEZC;
            case "BELOFTEN":
                return Wedstrijd.Categorie.BELOFTEN;
            case "JUNIOREN":
                return Wedstrijd.Categorie.JUNIOREN;
            case "NIEUWELINGEN":
                return Wedstrijd.Categorie.NIEUWELINGEN;
            case "ASPIRANTEN":
                return Wedstrijd.Categorie.ASPIRANTEN;
            default:
                return Wedstrijd.Categorie.PROFS;
        }
    }

    @TypeConverter
    public static String toCategorieDbValue(Wedstrijd.Categorie categorie) {
        switch (categorie){
            case PROFS:
                return "PROFS";
            case ELITEZC:
                return "ELITEZC";
            case BELOFTEN:
                return "BELOFTEN";
            case JUNIOREN:
                return "JUNIOREN";
            case NIEUWELINGEN:
                return "NIEUWELINGEN";
            case ASPIRANTEN:
                return "ASPIRANTEN";
            default:
                return "PROFS";
        }
    }
}
