package com.example.contacts_wb;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.*;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Database(entities = {Contact.class}, version = 3, exportSchema = false)
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
        int [] sex={0,0,0,1,1};
        List<CallLog> callLog1 = new ArrayList<>(Arrays.asList(
                new CallLog("2022-01-01", true),
                new CallLog("2022-01-02", false),
                new CallLog("2022-01-03", true)
        ));
        Contact p1=new Contact("邱汉宸","18168078784",1,callLog1);
        List<CallLog> callLog2 = new ArrayList<>(Arrays.asList(
                new CallLog("2022-01-01", true),
                new CallLog("2022-01-02", false),
                new CallLog("2022-01-03", true)
        ));
        Contact p2=new Contact("ljk","78784",1,callLog2);
        List<CallLog> callLog3 = new ArrayList<>(Arrays.asList(
                new CallLog("2022-01-01", true),
                new CallLog("2022-01-02", false),
                new CallLog("2022-01-03", true)
        ));
        Contact p3=new Contact("yxt","178578784",1,callLog3);
        PopulateDbAsync(ContactRoomDatabase db) {
            mDao = db.contactDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
                mDao.insert(p1);
                mDao.insert(p2);
                mDao.insert(p3);
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
