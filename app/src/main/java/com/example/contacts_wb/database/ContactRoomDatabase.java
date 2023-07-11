package com.example.contacts_wb.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.*;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * entities 属性指定了这个数据库中的实体类，这里只有一个 Contact 类；
 * exportSchema 属性指定了是否导出数据库的模式信息，这里设置为 false。
 */
@Database(entities = {Contact.class ,CallLog.class}, version = 8, exportSchema = false)
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

        PopulateDbAsync(ContactRoomDatabase db) {
            mDao = db.contactDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            List<Contact> contacts = new ArrayList<>();
            mDao.deleteAll();
            contacts.add(new Contact("邱汉宸","18168078784",0));
            contacts.add(new Contact("刘宇恒","18287416749",0));
            contacts.add(new Contact("叶信托","1885119620",0));
            contacts.add(new Contact("李俊凯","18852090080",1));
            contacts.add(new Contact("张晓宇", "18812345678", 0));
            contacts.add(new Contact("王晨阳", "18787654321", 0));
            contacts.add(new Contact("陈美丽", "18698765432", 1));
            contacts.add(new Contact("赵伟杰", "18587654321", 0));
            contacts.add(new Contact("刘芳", "18312345678", 1));
            contacts.add(new Contact("陈晓明", "18898765432", 0));
            contacts.add(new Contact("李婷婷", "18712345678", 1));
            contacts.add(new Contact("王伟", "18687654321", 0));
            contacts.add(new Contact("张丽", "18598765432", 1));
            contacts.add(new Contact("刘鑫", "18387654321", 0));
            contacts.add(new Contact("王敏", "18212345678", 1));
            contacts.add(new Contact("赵宇", "18198765432", 0));
            contacts.add(new Contact("孙美丽", "18087654321", 1));
            contacts.add(new Contact("李强", "18822345678", 0));
            contacts.add(new Contact("王璐", "18798765432", 1));
            contacts.add(new Contact("李明", "18322345678", 0));
            contacts.add(new Contact("周静", "18298765432", 1));
            contacts.add(new Contact("张伟", "18187654321", 0));
            contacts.add(new Contact("陈玉", "18022345678", 1));
            contacts.add(new Contact("刘军", "18898765432", 0));
            contacts.add(new Contact("王阳", "18712345678", 0));
            contacts.add(new Contact("张丹", "18687654321", 1));
            contacts.add(new Contact("李小宇", "18598765432", 0));
            contacts.add(new Contact("陈明", "18387654321", 0));
            contacts.add(new Contact("王梦", "18212345678", 1));
            contacts.add(new Contact("张飞", "18198765432", 0));
            contacts.add(new Contact("刘备", "18087654321", 1));
            contacts.add(new Contact("关羽", "18822345678", 0));
            contacts.add(new Contact("曹操", "18798765432", 1));
            contacts.add(new Contact("孙权", "18612345678", 0));
            contacts.add(new Contact("周瑜", "18587654321", 1));
            contacts.add(new Contact("诸葛亮", "18312345678", 0));
            contacts.add(new Contact("黄忠", "18298765432", 1));
            contacts.add(new Contact("赵云", "18187654321", 0));
            contacts.add(new Contact("马超", "18022345678", 1));
            contacts.add(new Contact("张无忌", "18898765432", 0));
            contacts.add(new Contact("赵敏", "18712345678", 1));
            contacts.add(new Contact("周芷若", "18687654321", 1));
            contacts.add(new Contact("小龙女", "18598765432", 1));
            contacts.add(new Contact("杨过", "18387654321", 0));
            contacts.add(new Contact("郭靖", "18212345678", 0));
            contacts.add(new Contact("黄蓉", "18187654321", 1));
            contacts.add(new Contact("欧阳林", "18022345678", 0));
            contacts.add(new Contact("洪七公", "18898765432", 1));
            contacts.add(new Contact("段誉", "18712345678", 0));
            contacts.add(new Contact("虚竹", "18687654321", 1));
            contacts.add(new Contact("慕容复", "18598765432", 0));
            contacts.add(new Contact("华山老祖", "18387654321", 1));
            contacts.add(new Contact("令狐冲", "18212345678", 0));
            contacts.add(new Contact("岳不群", "18187654321", 1));
            contacts.add(new Contact("张三丰", "18022345678", 0));
            contacts.add(new Contact("李莫愁", "18898765432", 1));
            contacts.add(new Contact("周伯通", "18712345678", 0));
            contacts.add(new Contact("全真七子", "18687654321", 1));
            contacts.add(new Contact("玄慈大师", "18598765432", 1));
            contacts.add(new Contact("段正淳", "18387654321", 0));
            contacts.add(new Contact("张翠山", "18212345678", 0));
            contacts.add(new Contact("殷素素", "18187654321", 1));
            contacts.add(new Contact("乔峰", "18022345678", 0));
            contacts.add(new Contact("段誉", "18898765432", 1));
            contacts.add(new Contact("虚竹", "18712345678", 0));
            contacts.add(new Contact("慕容复", "18687654321", 1));
            contacts.add(new Contact("华山老妪", "18598765432", 1));
            contacts.add(new Contact("令狐冲", "18387654321", 0));
            contacts.add(new Contact("岳灵珊", "18212345678", 1));
            contacts.add(new Contact("林平之", "18187654321", 0));
            contacts.add(new Contact("刘正风", "18022345678", 1));
            contacts.add(new Contact("黄药师", "18898765432", 0));
            contacts.add(new Contact("欧阳锋", "18712345678", 1));
            contacts.add(new Contact("杨康", "18687654321", 0));
            contacts.add(new Contact("穆念慈", "18598765432", 1));
            contacts.add(new Contact("郭靖", "18387654321", 0));
            contacts.add(new Contact("黄蓉", "18212345678", 1));
            contacts.add(new Contact("杨过", "18187654321", 0));
            contacts.add(new Contact("小龙女", "18022345678", 1));
            contacts.add(new Contact("张无忌", "18898765432", 0));
            contacts.add(new Contact("赵敏", "18712345678", 1));
            contacts.add(new Contact("周芷若", "18687654321", 1));
            contacts.add(new Contact("武当七侠", "18598765432", 0));
            contacts.add(new Contact("胡斐", "18387654321", 1));
            contacts.add(new Contact("苗人凤", "18212345678", 0));
            contacts.add(new Contact("袁承志", "18187654321", 1));
            contacts.add(new Contact("成吉思汗", "18022345678", 0));
            contacts.add(new Contact("忽必烈", "18898765432", 1));
            contacts.add(new Contact("哪吒", "18712345678", 0));
            contacts.add(new Contact("杨戬", "18687654321", 1));
            contacts.add(new Contact("太乙真人", "18598765432", 0));
            contacts.add(new Contact("红孩儿", "18387654321", 1));
            contacts.add(new Contact("二郎神", "18212345678", 0));
            contacts.add(new Contact("雷震子", "18187654321", 1));
            contacts.add(new Contact("金角大王", "18022345678", 0));
            contacts.add(new Contact("木妖", "18898765432", 1));
            contacts.add(new Contact("水妖", "18712345678", 0));
            contacts.add(new Contact("土地公公", "18687654321", 1));
            contacts.add(new Contact("玉皇大帝", "18598765432", 0));
            contacts.add(new Contact("观音菩萨", "18387654321", 1));
            contacts.add(new Contact("毗湿奴", "18212345678", 0));
            contacts.add(new Contact("文殊菩萨", "18187654321", 1));
            contacts.add(new Contact("普贤菩萨", "18022345678", 0));
            for (int i=0;i<contacts.size();i++){
                mDao.insert(contacts.get(i));
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
