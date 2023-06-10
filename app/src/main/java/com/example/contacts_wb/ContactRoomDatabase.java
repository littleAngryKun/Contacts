package com.example.contacts_wb;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.*;
import androidx.sqlite.db.SupportSQLiteDatabase;
@Database(entities = {Contact.class}, version = 2, exportSchema = false)
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
        String[] name = {"邱汉宸", "叶信托", "刘宇恒", "李俊凯", "李俊凯2"};
        String[] phonenumber = {
                "123456",
                "110",
                "120",
                "119",
                "121"
        };

        PopulateDbAsync(ContactRoomDatabase db) {
            mDao = db.contactDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();

                for (int i = 0; i <= name.length - 1; i++) {
                    Contact contact = new Contact(name[i],phonenumber[i]);
                    mDao.insert(contact);
                }

            return null;
        }
            // Start the app with a clean database every time.
// Not needed if you only populate on creation.
//            mDao.deleteAll();
//            for (int i = 0; i <= name.length - 1; i++) {
//                Contact contact = new Contact(name[i], phonenumber[i]);
//                mDao.insert(contact);
//            }
//            return null;
        }

        private static RoomDatabase.Callback sRoomDatabaseCallback = new
                RoomDatabase.Callback() {
                    @Override
                    public void onOpen(@NonNull SupportSQLiteDatabase db) {
                        super.onOpen(db);
// If you want to keep the data through app restarts,
// comment out the following line.
                        new PopulateDbAsync(INSTANCE).execute();
                    }
                };
}
