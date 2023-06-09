package com.example.contacts_wb;

import android.provider.ContactsContract;

import java.util.List;

public class NewContact {
    List<SortModel> Contacts;
    public NewContact(List<SortModel> contacts){
        this.Contacts= Contacts;
    }
    public List<SortModel> Add(SortModel contact){
        Contacts.add(contact);
        return Contacts;
    }
    public List<SortModel> Delete(SortModel contact){
        String Name=contact.getName();
//        Contacts.removeIf(sortModel -> sortModel.getName().equals(Name));
        for (SortModel sortModel : Contacts) { // 遍历 List 中的每个 SortModel 对象
            if (sortModel.getName().equals(Name)) { // 如果当前 SortModel 对象的 name 属性等于 "Alex"
                Contacts.remove(sortModel); // 删除当前 SortModel 对象
                break; // 跳出循环，只删除第一个遇到的对象
            }
        }
        return Contacts;
    }
    public List<SortModel> Update(SortModel contact){
        String Name=contact.getName();
        for (SortModel sortModel : Contacts) { // 遍历 List 中的每个 SortModel 对象
            if (sortModel.getName().equals(Name)) { // 如果当前 SortModel 对象的 name 属性等于 "Alex"
                Contacts.remove(sortModel); // 删除当前 SortModel 对象
                break; // 跳出循环，只删除第一个遇到的对象
            }
        }
        Contacts.add(contact);
        return Contacts;
    }
}
