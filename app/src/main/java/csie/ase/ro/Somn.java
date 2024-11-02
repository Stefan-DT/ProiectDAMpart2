package csie.ase.ro;

import java.io.Serializable;
import java.util.Date;

public class Somn implements Serializable {
    private Date dataSomnului;
    private Date oraTrezirii;
    private int durataSomnului;
    private int calitateSomnului;
    private String note;

    public Somn(Date dataSomnului, Date oraTrezirii, int durataSomnului, int calitateSomnului, String note) {
        this.dataSomnului = dataSomnului;
        this.oraTrezirii = oraTrezirii;
        this.durataSomnului = durataSomnului;
        this.calitateSomnului = calitateSomnului;
        this.note = note;
    }

    // Getters
    public Date getDataSomnului() {
        return dataSomnului;
    }

    public Date getOraTrezirii() {
        return oraTrezirii;
    }

    public int getDurataSomnului() {
        return durataSomnului;
    }

    public int getCalitateSomnului() {
        return calitateSomnului;
    }

    public String getNote() {
        return note;
    }

    @Override
    public String toString() {
        return "Somn{" +
                "dataSomnului=" + dataSomnului +
                ", oraTrezirii=" + oraTrezirii +
                ", durataSomnului=" + durataSomnului +
                ", calitateSomnului=" + calitateSomnului +
                ", note='" + note + '\'' +
                '}';
    }
}
