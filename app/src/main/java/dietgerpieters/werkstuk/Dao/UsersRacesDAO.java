package dietgerpieters.werkstuk.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import dietgerpieters.werkstuk.Models.TussenTabel;
import dietgerpieters.werkstuk.Models.User;

/**
 * Created by Dietger (Pantani) on 22/12/2017.
 */

@Dao
public interface UsersRacesDAO {
    @Insert
    public void insertRelation(TussenTabel tussenTabel);
    @Delete
    public void deleteRelation(TussenTabel tussenTabel);
    @Query("SELECT * FROM usersraces")
    public List<TussenTabel> loadAllUsersRacesRelations();

    @Query("SELECT * FROM usersraces WHERE userID = :id")
    public List<TussenTabel> loadAllByUserID(int id);

    @Query("SELECT * FROM usersraces WHERE wedstrijdID = :id AND userID = :id2")
    public TussenTabel loadRelation(int id, int id2);

    @Query("DELETE FROM usersraces WHERE wedstrijdID = :id AND userID = :id2")
    public void deleteRelation2(int id, int id2);




/*
    public User getUser(int id);
    @Query("SELECT * FROM users WHERE naam = :naam")
    public User getUserByName(String naam);
    @Query("SELECT ingelogd FROM users WHERE naam = :naam")
    public int checkLoginStatus(String naam);
    @Query("UPDATE users SET ingelogd = 1 WHERE naam = :naam")
    public void loginUser(String naam);
    @Query("UPDATE users SET ingelogd = 0 WHERE naam = :naam")
    public void logoutUser(String naam);
    @Query("SELECT * FROM users WHERE ingelogd = 1")
    public User loadActiveUser();
   */



}
