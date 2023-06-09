package com.example.contacts_wb;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contact_table")
public class Contact {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    private String name;
    public Contact(@NonNull String name) {
        this.name = name;}
    public String getName(){
        return this.name;
    }
}
