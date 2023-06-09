package com.example.contacts_wb;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    private ContactRepository mRepository;
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
        mRepository = new ContactRepository(application);
        mAllContacts = mRepository.getmAllContacts();

    }
    LiveData<List<Contact>> getAllContacts() {
        return mAllContacts;
    }
    public void insert(Contact contact) { mRepository.insert(contact); }
}
