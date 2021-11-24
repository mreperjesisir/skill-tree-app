package com.example.skilltree.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        switch (matchCode){
            case SKILLS:
                cursor = db.query(SkillContract.SkillEntry.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            case SKILL_ID:
                selection = SkillContract.SkillEntry.COLUMN_ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(SkillContract.SkillEntry.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            default:
                return null;
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    //TODO: implement all CRUD methods

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final int match = sUriMatcher.match(uri);

        switch (match) {
            case SKILLS:
                //TODO: Sanity check data here, or in a helper method,
                // throw exceptions if data isn't valid
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            long id = db.insert(SkillContract.SkillEntry.TABLE_NAME, null, values);
            getContext().getContentResolver().notifyChange(uri, null);
            return ContentUris.withAppendedId(uri, id);

            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
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
