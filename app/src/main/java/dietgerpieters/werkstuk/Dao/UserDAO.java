package dietgerpieters.werkstuk.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import java.util.List;

import dietgerpieters.werkstuk.Models.User;
import dietgerpieters.werkstuk.Models.Wedstrijd;
import dietgerpieters.werkstuk.TypeConverters.CategorieConverter;
import dietgerpieters.werkstuk.TypeConverters.DateConverter;

/**
 * Created by Dietger (Pantani) on 22/12/2017.
 */

@Dao
public interface UserDAO {
    @Insert
    public void insertUser(User user);
    @Delete
    public void deleteUser(User user);
    @Query("SELECT * FROM users")
    public List<User> loadAllUsers();
    @Query("SELECT * FROM users WHERE id = :id")
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


}
