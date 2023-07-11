package com.example.contacts_wb;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.contacts_wb.database.CallLog;
import com.example.contacts_wb.database.Contact;
import com.example.contacts_wb.database.ContactRepository;

import java.util.List;
import com.example.contacts_wb.database.ContactRepository;

/**
 * 继承了 AndroidViewModel 的 ContactViewModel 类,用于管理 Contact 数据实体类的数据和操作。
 *
 */
public class ContactViewModel extends AndroidViewModel {
    private ContactRepository mRepository;//仓库

    /**
     * mAllContacts 成员变量的类型是 LiveData<List<Contact>>，
     * 它支持数据观察者模式，当数据库中的数据发生变化时，可以自动将变化的数据发送给已经注册的观察者。
     */
    private LiveData<List<Contact>> mAllContacts;
    //    List<Word> wordList = mAllWords.getValue(); // 获取 LiveData 对象的值
//    int len=wordList.size();
//    String[] words = new String[len]; // 创建一个字符串数组，长度为 List<Word> 对象的元素个数
//    for (int i = 0; i < len ;i++)
//    {
//        words[i] = wordList.get(i).getWord(); // 将 List<Word> 对象的每个元素的 word 属性转换为字符串，并保存到字符串数组中
//    }
    private LiveData<List<CallLog>> mAllCallLogs;
    private  LiveData<Contact> mContact ;

    public ContactViewModel (Application application) {
        super(application);
        mRepository = new ContactRepository(application);//创建一个 ContactRepository 的实例
        mAllContacts = mRepository.getmAllContacts();//获取数据库中所有的 Contact 数据
        mAllCallLogs = mRepository.getmAllCallLogs();//获取数据库所有的CallLog数据
    }

    /**
     * 对联系人的操作
     * @return
     */
    LiveData<List<Contact>> getAllContacts() {
        return mAllContacts;
    }
    //一些公共的方法,都是通过调用 ContactRepository 中相应的方法来实现的
    public void insert(Contact contact) { mRepository.insert(contact); }
    public void DeleteById(int id){
        mRepository.DeleteById(id);
    }
    public LiveData<String> getPhoneNumber(String name){
        return mRepository.getPhoneNumber(name);
    }

    public void setContactByName(String name){mContact = mRepository.getContactByName(name);}
    public LiveData<Contact> getContactById(int id){return mRepository.getContactById(id);}
    public LiveData<Contact> getContactByName (){ return mContact;}

    public void updateContact(Contact contact){
        mRepository.updateContact(contact);
    }
    public void updateNameById(int id, String name){
        mRepository.updateNameById(id,name);
    }
    /**
     * 对通话记录的操作
     * @return
     */
    LiveData<List<CallLog>> getAllCallLogs(){ return mAllCallLogs;}
    //
    public void insert(CallLog callLog){ mRepository.insert(callLog);}
    //通过一个用户的电话查询和他相关的通话记录
    public LiveData<List<CallLog>> getCallLogsByPhoneNumber(String number){
        return mRepository.getCallLogsByPhoneNumber(number);
    }
    //通过一个用户的用户名查询和他相关的通话记录
    public LiveData<List<CallLog>> getCallLogsByContactName(String Name){
        return mRepository.getCallLogsByContactName(Name);
    }
    //通过一个联系人的id外键来查询
    public LiveData<List<CallLog>> getCallLogsByContactId(int id){
        return mRepository.getCallLogsByPhoneId(id);
    }
}
