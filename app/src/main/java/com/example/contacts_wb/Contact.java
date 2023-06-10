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
    @ColumnInfo(name = "phonenumber")
    private String phonenumber;
    public Contact(@NonNull String name,String phonenumber) {
        this.name = name;
        this.phonenumber = phonenumber;}
    public String getName(){
        return this.name;
    }
    public String getPhonenumber(){
        return this.phonenumber;
    }
}
