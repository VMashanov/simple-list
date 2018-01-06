package com.example.vmashanov.simplelist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "listDb";
    public static final String TABLE_NAME = "lists";

    public static  final String KEY_ID = "_id";
    public static  final String KEY_TITLE = "title";
    public static  final String KEY_DESCRIPTION = "description";
    public static  final String KEY_DONE = "done";
    public static  final String KEY_PARENT = "parent";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table lists (_id integer primary key, title text, description text, done boolean, parent integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists lists");
        onCreate(db);
    }
}