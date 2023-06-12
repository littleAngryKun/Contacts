package com.example.contacts_wb.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.contacts_wb.database.Contact;

import java.util.List;

/**
 * 数据访问对象（DAO）接口 ContactDao，它用于访问和操作 contact_table 表格中的数据。
 * 虽然 ContactDao 是一个接口，但是这些方法的具体实现是由 Room 数据库框架自动生成的，不需要我们来手动实现。
 * 我们只需要在应用程序中定义这些方法，然后在需要使用的地方调用这些方法即可。
 * Room 数据库框架会根据这些方法中的注解生成相应的 SQL 语句，并执行相应的数据库操作。
 * 因此，我们可以将精力集中在应用程序的业务逻辑上，而不需要编写大量的数据库操作代码。
 */
@Dao
public interface ContactDao {
    /**
     * 对Conatct操作
     * @return
     */
    @Query("SELECT * from contact_table ORDER BY name ASC")
    LiveData<List<Contact>> getAlphabetizedWords();
    @Query("SELECT * from contact_table ORDER BY name ASC")
    List<Contact> getAlphabetizedWord();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Contact contact);
    @Query("DELETE FROM contact_table")
    void deleteAll();
    @Query("SELECT * from contact_table LIMIT 1")
    Contact[] getAnyContact();
    //通过名字来查询
    @Query("SELECT * FROM contact_table WHERE name = :name")
    LiveData<Contact> getContactByName(String name);
    //通过名字来删除
    @Query("DELETE FROM contact_table WHERE name = :name")
    void DeleteByName(String name);
    //更新联系人
    @Update
    void updateContact(Contact contact);
    //根据id来改变姓名
    @Query("UPDATE contact_table SET name = :newName WHERE id = :id")
    void updateNameById(int id, String newName);

    //通过id来查询
    @Query("SELECT * FROM contact_table WHERE id = :id")
    LiveData<Contact> getContactById(int id);
    @Query("DELETE FROM contact_table WHERE id = :id")
    void DeleteById(int id);

    //通过名字来获取联系人的号码
    @Query("SELECT phonenumber FROM contact_table WHERE name = :name")
    LiveData<String> getPhoneNumberByName(String name);


    /**
     * 对CallLog操作
     * @return
     */
    // 获取所有通话记录，并按通话时间倒序排序
    @Query("SELECT * FROM call_log_table ORDER BY call_time DESC")
    LiveData<List<CallLog>> getAllCallLogs();
    // 根据根据联系人id来查询通话记录（这样查询，即便是修改了名字，也可以准确查询之前的通话记录）
    @Transaction
    @Query("SELECT * FROM call_log_table WHERE caller_id = :callerId ORDER BY call_time DESC")
    LiveData<List<CallLog>> getCallLogsByCallerId(long callerId);
    // 根据电话号码查询与该号码相关的所有通话记录，并按通话时间倒序排序
    @Query("SELECT * FROM call_log_table WHERE caller_number = :phoneNumber ORDER BY call_time DESC")
    LiveData<List<CallLog>> getCallLogsByPhoneNumber(String phoneNumber);
    // 根据联系人名字查询与该联系人相关的所有通话记录，并按通话时间倒序排序
    @Query("SELECT * FROM call_log_table WHERE caller_name = :contactName ORDER BY call_time DESC")
    LiveData<List<CallLog>> getCallLogsByContactName(String contactName);
    // 向通话记录表格中插入一条新的通话记录
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CallLog callLog);
    // 更新一条通话记录的信息
    @Update
    void update(CallLog callLog);
    // 删除一条通话记录
    @Delete
    void delete(CallLog callLog);
    // 删除整个通话记录表格中的所有记录
    @Query("DELETE FROM call_log_table")
    void deleteAllCallLog();
}