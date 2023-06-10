package com.example.contacts_wb;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "contact_table")
public class Contact {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "phonenumber")
    private String phonenumber;
    @ColumnInfo(name = "sex")
    private int sex;
    @ColumnInfo(name = "callLog")
    private List<CallLog> callLogs;
    public Contact(@NonNull String name,String phonenumber,int sex,List<CallLog> callLogs) {
        this.name = name;
        this.phonenumber = phonenumber;
        this.sex=sex;
        this.callLogs=callLogs;
    }
    public String getName(){
        return this.name;
    }
    public String getPhonenumber(){
        return this.phonenumber;
    }
    public List<CallLog> getCallLogs() {
        return callLogs;
    }
    public int getSex() {
        return sex;
    }
}
