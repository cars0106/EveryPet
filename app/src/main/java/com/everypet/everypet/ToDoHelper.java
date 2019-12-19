package com.everypet.everypet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToDoHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION=1;

    public ToDoHelper(Context context){
        super(context,"tododb",null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String toDoSQL = "create table tb_todo (" +
                "_id integer primary key autoincrement, " +
                "name, " + "kind, " + "date, " + "time, "+ "what, "+
                "notice)";
        sqLiteDatabase.execSQL(toDoSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if(i1==DATABASE_VERSION){
            sqLiteDatabase.execSQL("drop table tb_todo");
            onCreate(sqLiteDatabase);
        }
    }
}
