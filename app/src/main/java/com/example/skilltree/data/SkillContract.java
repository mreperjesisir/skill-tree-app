package com.example.skilltree.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class SkillContract {

    public static final String CONTENT_AUTHORITY = "com.example.skilltree";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_SKILLS = "skills";

    private SkillContract(){
        //empty constructor
    }

    public static class SkillEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_SKILLS);

        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                CONTENT_AUTHORITY + "/" + PATH_SKILLS;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +
                CONTENT_AUTHORITY + "/" + PATH_SKILLS;

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
    }
}
