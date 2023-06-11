package com.example.contacts_wb.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "call_log_table") // 表示该类是数据库表格“call_log_table”中的实体
public class CallLog {
    @PrimaryKey(autoGenerate = true) // 表示“id”属性是实体的主键，并且它是自动生成的
    @ColumnInfo(name = "id") // 定义“id”属性在数据库表中的列名为“id”
    private int id;
    @ColumnInfo(name = "caller_name") // 定义“callerName”属性在数据库表中的列名为“caller_name”，通话对象的姓名
    private String callerName;
    @ColumnInfo(name = "caller_number") // 定义“callerNumber”属性在数据库表中的列名为“caller_number”，通话对象的电话号码
    private String callerNumber;
    @ColumnInfo(name = "call_duration") // 定义“callDuration”属性在数据库表中的列名为“call_duration”，通话时长，暂时不实现
    private int callDuration;
    @ColumnInfo(name = "call_time") // 定义“callTime”属性在数据库表中的列名为“call_time”，通话时间
    private long callTime;
    @ColumnInfo(name = "is_outgoing") //拨入或是播出，1是播出，0是拨入
    private boolean isOutgoing;

    public CallLog(String callerName, String callerNumber, int callDuration, long callTime,boolean isOutgoing) {
        this.callerName = callerName;
        this.callerNumber = callerNumber;
        this.callDuration = callDuration;
        this.callTime = callTime;
        this.isOutgoing = isOutgoing;
    }

    public int getId() {
        return id;
    }

    public String getCallerName() {
        return callerName;
    }

    public String getCallerNumber() {
        return callerNumber;
    }

    public int getCallDuration() {
        return callDuration;
    }

    public long getCallTime() {
        return callTime;
    }
    public boolean isOutgoing() {
        return isOutgoing;
    }

    public void setId(int id) {
        this.id = id;
    }
}
