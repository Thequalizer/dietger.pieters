package dietgerpieters.werkstuk.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import dietgerpieters.werkstuk.Dao.WedstrijdDAO;
import dietgerpieters.werkstuk.Models.Wedstrijd;

/**
 * Created by Dietger (Pantani) on 22/12/2017.
 */

@Database(version = 1, entities = {Wedstrijd.class})
public abstract class AppDatabase extends RoomDatabase {


    public abstract WedstrijdDAO wedstrijdDAO();


}