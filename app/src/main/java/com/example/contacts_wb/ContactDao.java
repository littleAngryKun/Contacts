package com.example.contacts_wb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

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
}