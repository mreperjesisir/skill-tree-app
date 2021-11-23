package com.example.skilltree.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SkillProvider extends ContentProvider {

    private static final int SKILLS = 100;
    private static final int SKILL_ID = 101;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(SkillContract.CONTENT_AUTHORITY, SkillContract.PATH_SKILLS, SKILLS);
        sUriMatcher.addURI(SkillContract.CONTENT_AUTHORITY, SkillContract.PATH_SKILLS + "/#", SKILL_ID);
    }

    public SkillDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new SkillDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        int matchCode = sUriMatcher.match(uri);
        Cursor cursor;
        switch (matchCode){
            case SKILLS:
                //TODO: here make a query that returns the whole database
            case SKILL_ID:
                //TODO: here make a query that returns one line with the given ID
            default:
                return null;
        }
    }

    //TODO: Make Crud methods, add provider to in manifest



    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        //TODO: delete this TODO last
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int matchCode = sUriMatcher.match(uri);
        switch (matchCode){
            case SKILLS:
                return SkillContract.SkillEntry.CONTENT_LIST_TYPE;
            case SKILL_ID:
                return SkillContract.SkillEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknow URI " + uri + "with match " + matchCode);
        }
    }
}
