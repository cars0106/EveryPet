package com.everypet.everypet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION=1;

    public DBHelper(Context context){
        super(context,"petdb",null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String petSQL = "create table tb_pet (" +
                "_id integer primary key autoincrement, " +
                "name, " + "kind, " + "birthDay, "+ "gender, "+
                "weight, " + "height, " + "symptom, " + "careful, "+"picture)";
        sqLiteDatabase.execSQL(petSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if(i1==DATABASE_VERSION){
            sqLiteDatabase.execSQL("drop table tb_pet");
            onCreate(sqLiteDatabase);
        }
    }
}
