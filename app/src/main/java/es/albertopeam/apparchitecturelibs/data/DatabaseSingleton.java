package es.albertopeam.apparchitecturelibs.data;

import android.arch.persistence.room.Room;
import android.content.Context;

/**
 * Created by Alberto Penas Amor on 28/05/2017.
 */

public class DatabaseSingleton {


    private static AppDatabase appAppDatabase;


    public static void init(Context context){
        if (appAppDatabase == null) {
            appAppDatabase = Room.databaseBuilder(context, AppDatabase.class, "database").build();
        }
    }


    static AppDatabase instance(){
        if (appAppDatabase == null){
            throw new NullPointerException("Invoke first: init(Context context)");
        }
        return appAppDatabase;
    }
}
