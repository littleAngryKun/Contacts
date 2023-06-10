package com.example.contacts_wb;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.*;
import androidx.sqlite.db.SupportSQLiteDatabase;
@Database(entities = {Contact.class}, version = 1, exportSchema = false)
public abstract class ContactRoomDatabase extends RoomDatabase {
    public abstract ContactDao contactDao();
    private static ContactRoomDatabase INSTANCE;
    static ContactRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ContactRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                            ContactRoomDatabase.class, "contact_database")
                                    .fallbackToDestructiveMigration()
                                    //必须添加回调
                                    .addCallback(sRoomDatabaseCallback)
                                    .build();
                }
            }
        }
        return INSTANCE;
    }
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final ContactDao mDao;
        String [] cons = {"邱汉宸", "叶信托", "刘宇恒","李俊凯","李俊凯2"};
        PopulateDbAsync(ContactRoomDatabase db) {
            mDao = db.contactDao();
        }
        @Override
        protected Void doInBackground(final Void... params) {

            if (mDao.getAnyContact().length < 1) {
                for (int i = 0; i <= cons.length - 1; i++) {
                    Contact contact = new Contact(cons[i]);
                    mDao.insert(contact);
                }
            }
            return null;
        }
    }
    //回调
    private static RoomDatabase.Callback sRoomDatabaseCallback = new
            RoomDatabase.Callback(){
                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
// If you want to keep the data through app restarts,
// comment out the following line.
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };
}
