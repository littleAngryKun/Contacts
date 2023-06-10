package com.example.contacts_wb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

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
    @Query("DELETE FROM contact_table WHERE name = :name")
    void DeleteById(String name);
    @Query("SELECT phonenumber FROM contact_table WHERE name = :name")
    LiveData<String> getPhoneNumberByName(String name);
}