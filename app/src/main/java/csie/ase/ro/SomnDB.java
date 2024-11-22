package csie.ase.ro;

import android.content.Context;

import androidx.databinding.adapters.Converters;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.Date;

@Database(entities = {Somn.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class SomnDB extends RoomDatabase {

    private static SomnDB instance;

    public static final String DATABASE_NAME = "somn.db";

    public static SomnDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, SomnDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract SomnDAO getSomnDAO();
}
