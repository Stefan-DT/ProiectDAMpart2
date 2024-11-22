package csie.ase.ro;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Ajutor.class},version = 1,exportSchema = false)
public abstract class AjutorDB extends RoomDatabase {

    private static AjutorDB instance;
    public static final String dataBaseName = "ajutor.db";

    public static AjutorDB getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context,AjutorDB.class,dataBaseName)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

    public abstract AjutorDAO getAjutorDAO();

}
