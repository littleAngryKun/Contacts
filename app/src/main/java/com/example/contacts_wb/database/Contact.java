package com.example.contacts_wb.database;

import androidx.annotation.ColorLong;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "contact_table")
public class Contact {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @NonNull
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "phonenumber")
    private String phonenumber;
    @ColumnInfo(name = "sex")
    private int sex;
//    @ColumnInfo(name = "callLog")
//    private List<CallLog> callLogs;
    public Contact(@NonNull String name,String phonenumber,int sex) {
        this.name = name;
        this.phonenumber = phonenumber;
        this.sex=sex;
//        this.callLogs=callLogs;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName(){
        return this.name;
    }
    public String getPhonenumber(){
        return this.phonenumber;
    }
//    public List<CallLog> getCallLogs() {
//        return callLogs;
//    }
    public void setName(String name){this.name =name;}
    public void setPhonenumber(String phonenumber){this.phonenumber = phonenumber;}
    public void setSex(int sex){this.sex = sex;}
    public int getSex() {
        return sex;
    }
}
