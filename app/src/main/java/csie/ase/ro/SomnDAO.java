package csie.ase.ro;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SomnDAO {
    @Insert
    void insertSomn(Somn somn);

    @Query("SELECT * FROM Somn")
    List<Somn> getAllSomn();

    @Query("SELECT * FROM Somn WHERE id = :id")
    Somn getSomnById(Long id);

    @Update
    void updateSomn(Somn somn);

    @Query("DELETE FROM Somn WHERE id = :id")
    void deleteSomnById(Long id);

    @Query("DELETE FROM somn")
    void deleteAllSomn();
}
