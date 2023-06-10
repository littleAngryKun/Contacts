package com.example.contacts_wb;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import java.util.List;

public class ContactRepository {
    private ContactDao contactDao;
    private LiveData<List<Contact>> mAllContacts;
    ContactRepository(Application application) {
        ContactRoomDatabase db = ContactRoomDatabase.getDatabase(application);
        contactDao = db.contactDao();
        mAllContacts = contactDao.getAlphabetizedWords();
    }
    LiveData<List<Contact>> getmAllContacts() {
        return mAllContacts;
    }
    LiveData<String> getPhoneNumber(String name){
        return contactDao.getPhoneNumberByName(name);
    }
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
}
