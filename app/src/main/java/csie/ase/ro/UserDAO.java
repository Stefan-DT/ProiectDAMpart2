package csie.ase.ro;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    User getUser(String email, String password);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();
}
