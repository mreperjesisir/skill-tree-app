package com.example.skilltree.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.skilltree.data.SkillContract.SkillEntry;

public class SkillDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "skilldatabase.db";
    public static final int DATABASE_VERSION = 1;

    public static final String SQL_CREATE_ENTRIES= "CREATE TABLE " +
            SkillEntry.TABLE_NAME +
            "(" + SkillEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" +
            ", " + SkillEntry.COLUMN_SPORT + " TEXT NOT NULL" +
            ", " + SkillEntry.COLUMN_SKILL_NAME + " TEXT NOT NULL" +
            ", " + SkillEntry.COLUMN_DIFFICULTY + " INTEGER NOT NULL" +
            ")" + ";";

    public static final String SQL_DELETE_ENTRIES= "DROP TABLE IF EXISTS " +
            SkillEntry.TABLE_NAME + ";";


    public SkillDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
    }
}
