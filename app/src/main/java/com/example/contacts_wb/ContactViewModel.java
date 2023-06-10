package com.example.contacts_wb;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

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
    public ContactViewModel (Application application) {
        super(application);
        mRepository = new ContactRepository(application);//创建一个 ContactRepository 的实例
        mAllContacts = mRepository.getmAllContacts();//获取数据库中所有的 Contact 数据

    }
    LiveData<List<Contact>> getAllContacts() {
        return mAllContacts;
    }
    //一些公共的方法,都是通过调用 ContactRepository 中相应的方法来实现的
    public void insert(Contact contact) { mRepository.insert(contact); }
    public void DeleteById(String name){
        mRepository.DeleteById(name);
    }
    public LiveData<String> getPhoneNumber(String name){
        return mRepository.getPhoneNumber(name);
    }
}
