package com.example.contacts_wb.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.*;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * entities 属性指定了这个数据库中的实体类，这里只有一个 Contact 类；
 * exportSchema 属性指定了是否导出数据库的模式信息，这里设置为 false。
 */
@Database(entities = {Contact.class ,CallLog.class}, version = 5, exportSchema = false)
public abstract class ContactRoomDatabase extends RoomDatabase {

    /**这个抽象方法 contactDao() 实际上是由 Room 数据库框架来实现的。
     * 我们可以通过调用 ContactRoomDatabase 的 contactDao() 方法来获取 ContactDao 的实例，
     * 然后通过这个实例来访问和操作数据库。这个抽象方法的作用是告诉 Room 数据库框架需要实例化哪个 DAO 接口，并将其与数据库实例关联起来。
     * @return
     */
    public abstract ContactDao contactDao();

    private static ContactRoomDatabase INSTANCE;

    /**
     * @param context
     * @return
     * 获取数据库实例。如果实例不存在，它会使用 Room.databaseBuilder() 方法创建一个新的实例，
     * 并在创建时添加一个回调 sRoomDatabaseCallback。这个回调将在数据库打开时调用，并使用 PopulateDbAsync 异步任务进行初始化。
     */
    static ContactRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ContactRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                            ContactRoomDatabase.class, "contact_database")
                                    //设置数据库迁移时出现错误时的行为，即删除原有的数据库并重新创建新的数据库。
                                    .fallbackToDestructiveMigration()
                                    //必须添加回调,目的是在数据库创建和打开时执行一些初始化操作,加入一些初始数据
                                    .addCallback(sRoomDatabaseCallback)
                                    .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * PopulateDbAsync异步类
     * 用于在后台线程中执行数据库初始化任务，即向数据库中插入一些测试数据。
     * 具体来说，它使用 mDao 对象访问 ContactDao 中定义的一些操作，并在数据库中插入一些测试数据。
     */
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
        Contact p1=new Contact("邱汉宸","18168078784",1);
        Contact p2=new Contact("刘宇恒","78784",1);
        Contact p3=new Contact("叶信托","178578784",1);
        Contact p4 = new Contact("李俊凯","18852090080",1);

        CallLog c1 = new CallLog("邱汉宸","18168078784",120,System.currentTimeMillis(), false);
        PopulateDbAsync(ContactRoomDatabase db) {
            mDao = db.contactDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            mDao.insert(p1);//插入三个测试数据 p1、p2 和 p3
            mDao.insert(p2);
            mDao.insert(p3);
            mDao.insert(p4);
            mDao.insert(c1);
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

    /**
     * sRoomDatabaseCallback回调类
     * 重写了其中的 onOpen() 方法。这个方法在数据库打开时调用，并使用 PopulateDbAsync 异步任务进行初始化。
     * 如果你想在每次应用启动时都使用一个干净的数据库，可以将 new PopulateDbAsync(INSTANCE).execute(); 这行代码注释掉。
     * 异步任务会向数据库中插入一些测试数据
     * 这个回调的作用是确保数据库在打开时包含了一些测试数据，这些数据可以用于测试应用程序的不同功能，
     * 并确保应用程序的正常运行。在应用程序启动时，如果数据库中没有任何数据，可能会导致应用程序崩溃或出现异常情况。
     */
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
