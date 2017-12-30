package dietgerpieters.werkstuk.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import java.util.ArrayList;
import java.util.List;

import dietgerpieters.werkstuk.Models.Wedstrijd;
import dietgerpieters.werkstuk.TypeConverters.CategorieConverter;
import dietgerpieters.werkstuk.TypeConverters.DateConverter;

/**
 * Created by Dietger (Pantani) on 22/12/2017.
 */

@Dao
@TypeConverters({DateConverter.class, CategorieConverter.class})
public interface WedstrijdDAO {
    @Insert
    public void insertWedstrijd(Wedstrijd wedstrijd);
    @Delete
    public void deleteWedstrijd(Wedstrijd wedstrijd);
    @Query("SELECT * FROM wedstrijden")
    public List<Wedstrijd> loadAllWedstrijden();
    @Query("SELECT * FROM wedstrijden WHERE id = :id")
    public Wedstrijd getWedstrijd(int id);
    @Query("SELECT * FROM wedstrijden WHERE categorie = :categorie")
    public List<Wedstrijd> loadAllOnCategorie(Wedstrijd.Categorie categorie);




}
