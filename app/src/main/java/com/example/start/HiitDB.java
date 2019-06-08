package com.example.start;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;


@Database(entities = {layoutTableDB.class , ProfileTableDb.class , PerformanceTableDB.class} ,version = 7)
@TypeConverters(listConverter.class)
public abstract class HiitDB extends RoomDatabase {
    //this variable is created to turn the class singelton
    private static HiitDB instance ;
    //this let us access our layout dao
    public abstract layoutDAO layoutdao();
    //this let us access our profile dao
    public abstract profileDAO profiledao();
    //this let us access our performance dao
    public abstract PerformanceDAO performancedao();
    //create a database
    public static synchronized HiitDB getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    HiitDB.class, "hiit_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private layoutDAO layoutdao;
        private profileDAO profiledao ;
        private PerformanceDAO performancedao ;
        private PopulateDbAsyncTask(HiitDB db) {
            layoutdao = db.layoutdao();
            profiledao = db.profiledao();
            performancedao = db.performancedao() ;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }

}
