package csie.ase.ro;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface AjutorDAO {

    @Insert
    void insertAjutor(Ajutor ajutor);

    @Query("SELECT * FROM Ajutor")
    List<Ajutor> getAjutor();

    @Query("SELECT * FROM Ajutor WHERE id = :id")
    Ajutor getAjutorById(Long id);

    @Update
    void updateAjutor(Ajutor ajutor);

    @Query("DELETE FROM Ajutor WHERE id = :id")
    void deleteAjutorById(Long id);

    @Query("DELETE FROM Ajutor")
    void deleteAllAjutor();
}

