package com.example.contacts_wb.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 *应用程序与数据源（即 ContactDao）之间的中介，用于调用数据源的方法来操作数据库中的数据。
 *
 */
public class ContactRepository {
    private ContactDao contactDao;//一个接口类型的对象，用于访问数据库中的数据。
    private LiveData<List<Contact>> mAllContacts;//一个 LiveData 对象，用于存储数据库中的所有联系人信息。
    private LiveData<List<CallLog>> mAllCallLogs;
    public ContactRepository(Application application) {
        ContactRoomDatabase db = ContactRoomDatabase.getDatabase(application); //获取一个 ContactRoomDatabase 数据库实例。
        contactDao = db.contactDao();//获取数据库实例中的 ContactDao 对象，用于访问数据库中的数据。
        mAllContacts = contactDao.getAlphabetizedWords();//从 ContactDao 中获取所有联系人信息，并将其存储在 mAllContacts 中。
        mAllCallLogs = contactDao.getAllCallLogs();

    }
    public LiveData<List<Contact>> getmAllContacts() {
        return mAllContacts;//返回 mAllContacts 成员变量。
    }
    public LiveData<String> getPhoneNumber(String name){
        return contactDao.getPhoneNumberByName(name);//查询指定姓名对应的电话号码，并返回一个 LiveData 对象。
    }
    public LiveData<Contact> getContactByName(String name){ return contactDao.getContactByName(name);}
    public LiveData<List<CallLog>> getmAllCallLogs(){return  mAllCallLogs;}
    //通过一个用户的电话查询和他相关的通话记录
    public LiveData<List<CallLog>> getCallLogsByPhoneNumber(String number){
        return contactDao.getCallLogsByPhoneNumber(number);
    }
    //通过一个用户的用户名查询和他相关的通话记录
    public LiveData<List<CallLog>> getCallLogsByContactName(String Name){
        return contactDao.getCallLogsByContactName(Name);
    }

    /**
     * 向数据库中插入一个联系人信息。用异步任务去执行
     * @param contact
     */
    public void insert (Contact contact) {
        new insertAsyncTask(contactDao).execute(contact);
    }
    //插入操作需要异步
    private static class insertAsyncTask extends AsyncTask<Contact, Void, Void> {
        private ContactDao mAsyncTaskDao;
        insertAsyncTask(ContactDao dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final Contact... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }


    //根据姓名从数据库中删除一个联系人信息,通过异步去执行
    public void DeleteById (String name) {
        new deleteAsyncTask(contactDao).execute(name);
    }
    //插入操作需要异步
    private static class deleteAsyncTask extends AsyncTask<String, Void, Void> {
        private ContactDao mAsyncTaskDao;
        deleteAsyncTask(ContactDao dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final String... params) {
            mAsyncTaskDao.DeleteById(params[0]);
            return null;
        }
    }

    public void insert (CallLog callLog){
        new insertAsyncTask_calllog(contactDao).execute(callLog);
    }
    //插入通话记录操作需要异步
    private static class  insertAsyncTask_calllog extends AsyncTask<CallLog,Void,Void>{
        private ContactDao mAsyncTaskDao;
        insertAsyncTask_calllog(ContactDao contactDao){mAsyncTaskDao = contactDao;}
        @Override
        protected Void doInBackground(CallLog... callLogs) {
            mAsyncTaskDao.insert(callLogs[0]);
            return null;
        }
    }
    //todo 根据通话记录的id删除这条通话记录
}
