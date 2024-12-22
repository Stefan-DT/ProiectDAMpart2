package csie.ase.ro;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Ajutor")
public class Ajutor implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String numeAjutor;
    private String descriereAjutor;


    public Ajutor(Long id, String numeAjutor, String descriereAjutor) {
        this.numeAjutor = numeAjutor;
        this.descriereAjutor = descriereAjutor;
    }

    @Override
    public String toString() {
        return "Ajutor{" +
                "id=" + id +
                ", numeAjutor='" + numeAjutor + '\'' +
                ", descriereAjutor='" + descriereAjutor + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeAjutor() {
        return numeAjutor;
    }

    public void setNumeAjutor(String numeAjutor) {
        this.numeAjutor = numeAjutor;
    }

    public String getDescriereAjutor() {
        return descriereAjutor;
    }

    public void setDescriereAjutor(String descriereAjutor) {
        this.descriereAjutor = descriereAjutor;
    }


}