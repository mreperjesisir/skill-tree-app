package com.example.skilltree.data;

import android.provider.BaseColumns;

public class SkillContract {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "skilldatabase.db";

    private SkillContract(){
        //empty constructor
    }

    public class SkillEntry implements BaseColumns {

        public static final String TABLE_NAME = "skills";
        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_SPORT = "sport";
        public static final String COLUMN_SKILL_NAME = "name";
        public static final String COLUMN_DIFFICULTY = "difficulty";

        public static final int DIFFICULTY_1 = 1;
        public static final int DIFFICULTY_2 = 2;
        public static final int DIFFICULTY_3 = 3;
        public static final int DIFFICULTY_4 = 4;
        public static final int DIFFICULTY_5 = 5;
        public static final int DIFFICULTY_6 = 6;
        public static final int DIFFICULTY_7 = 7;
        public static final int DIFFICULTY_8 = 8;
        public static final int DIFFICULTY_9 = 9;
        public static final int DIFFICULTY_10 = 10;

        public static final String CREATE_TABLE = "CREATE TABLE";

    }
}
