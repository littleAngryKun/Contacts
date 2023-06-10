package com.example.contacts_wb;

import android.provider.ContactsContract;

import java.util.List;

public class NewContact {
    List<SortModel> Contacts;
    public NewContact(){
        this.Contacts= Contacts;
    }
    public List<SortModel> Add(SortModel contact){
        Contacts.add(contact);
        return Contacts;
    }
    public List<SortModel> Delete(String Name){
//        Contacts.removeIf(sortModel -> sortModel.getName().equals(Name));
        for (SortModel sortModel : Contacts) { // 遍历 List 中的每个 SortModel 对象
            if (sortModel.getName().equals(Name)) { // 如果当前 SortModel 对象的 name 属性等于 "Alex"
                Contacts.remove(sortModel); // 删除当前 SortModel 对象
                break; // 跳出循环，只删除第一个遇到的对象
            }
        }
        return Contacts;
    }
    public List<SortModel> Update(String Name){
//        String Name=contact.getName();
        SortModel target=null;
        for (SortModel sortModel : Contacts) { // 遍历 List 中的每个 SortModel 对象
            if (sortModel.getName().equals(Name)) { // 如果当前 SortModel 对象的 name 属性等于 "Alex"
                target = sortModel;
                Contacts.remove(sortModel); // 删除当前 SortModel 对象
                break; // 跳出循环，只删除第一个遇到的对象
            }
        }
        Contacts.add(target);
        return Contacts;
    }
    public List<SortModel> Search(String Name){
        List<SortModel> target=null;
        for (SortModel sortModel : Contacts) { // 遍历 List 中的每个 SortModel 对象
            if (sortModel.getName().contains(Name)) { // 如果当前 SortModel 对象的 name 属性等于 "Alex"
                target.add(sortModel);
            }
        }
        //需要对返回值的长度进行判断
        //若长度为0，则搜索结果为空
        //不为零，则返回所有包含“搜索内容”的联系人
        return target;
    }
}
