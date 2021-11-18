package com.ngahuynh.myapplication.room;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.os.HandlerCompat;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {WeatherTable.class, HikeTable.class, ProfileTable.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase mInstance;
    public abstract WeatherDAO weatherDao();
    public abstract HikeDAO hikeDao();
    public abstract ProfileDAO profileDao();
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(4);

    static synchronized AppDatabase getDatabase(final Context context){
        if(mInstance == null){
            mInstance = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"myApp.db").addCallback(sRoomDatabaseCallback).build();
        }
        return mInstance;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            databaseExecutor.execute(()->{
                WeatherDAO wDAO = mInstance.weatherDao();
                ProfileDAO pDAO = mInstance.profileDao();
                HikeDAO hDAO = mInstance.hikeDao();
                wDAO.deleteAll();
                pDAO.deleteAll();
                hDAO.deleteAll();
                // Need to implement TableBuilders
            });
        }
    };

    private static RoomDatabase.Callback sRoomDatabaseCallback2 = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            new PopulateDbTask(mInstance).execute();
        }
    };

    private static class PopulateDbTask{
        private final WeatherDAO wDao;
        private final HikeDAO hDao;
        private final ProfileDAO pDao;
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
        PopulateDbTask(AppDatabase db){
            wDao = db.weatherDao();
            hDao = db.hikeDao();
            pDao = db.profileDao();
        }

        public void execute(){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    wDao.deleteAll();
                    hDao.deleteAll();
                    pDao.deleteAll();

                    // Also need to implement TableBuilders
                }
            });
        }
    }
}
