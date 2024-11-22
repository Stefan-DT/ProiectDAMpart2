package csie.ase.ro;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "Somn")
public class Somn implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @TypeConverters(DateConverter.class)  // Adaugă TypeConverter pentru date
    private Date dataSomnului;

    @TypeConverters(DateConverter.class)  // Adaugă TypeConverter pentru ora trezirii
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataSomnului() {
        return dataSomnului;
    }

    public void setDataSomnului(Date dataSomnului) {
        this.dataSomnului = dataSomnului;
    }

    public Date getOraTrezirii() {
        return oraTrezirii;
    }

    public void setOraTrezirii(Date oraTrezirii) {
        this.oraTrezirii = oraTrezirii;
    }

    public int getDurataSomnului() {
        return durataSomnului;
    }

    public void setDurataSomnului(int durataSomnului) {
        this.durataSomnului = durataSomnului;
    }

    public int getCalitateSomnului() {
        return calitateSomnului;
    }

    public void setCalitateSomnului(int calitateSomnului) {
        this.calitateSomnului = calitateSomnului;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    // Metodele parseDate și parseTime trebuie să fie statice
    public static Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date parseTime(String timeString) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        try {
            return timeFormat.parse(timeString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
