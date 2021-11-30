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

        final int match = sUriMatcher.match(uri);
        int deletedRows;
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        switch (match){
            case SKILLS:
                deletedRows = db.delete(SkillContract.SkillEntry.TABLE_NAME, selection, selectionArgs);
                return deletedRows;

            case SKILL_ID:
                selection = SkillContract.SkillEntry.COLUMN_ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                deletedRows = db.delete(SkillContract.SkillEntry.TABLE_NAME, selection, selectionArgs);
                return deletedRows;
            default:
                throw new IllegalArgumentException("Delete is not supported at this uri: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);

        switch (match){
            case SKILLS:
                //the app won't actually offer multiple chance for
                //updates at the same time, but just in case...
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                int updateRows = db.update(SkillContract.SkillEntry.TABLE_NAME, values, selection, selectionArgs);
                return updateRows;

            case SKILL_ID:
                SQLiteDatabase db2 = mDbHelper.getWritableDatabase();
                selection = SkillContract.SkillEntry.COLUMN_ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                int updatedRows= db2.update(SkillContract.SkillEntry.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);
                return updatedRows;

            default:
                throw new IllegalArgumentException("Update is not possible with the following uri: " + uri);

        }
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
                throw new IllegalStateException("Unknown URI " + uri + "with match " + matchCode);
        }
    }
}
