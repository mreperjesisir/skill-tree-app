package com.example.skilltree;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.example.skilltree.data.SkillContract;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private CursorAdapter mAdapter;
    private final int SKILL_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: Hook up the FAB button to open EditorActivity

        ListView list = findViewById(R.id.list_view_moves);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, EditorActivity.class);
                Uri currentSkillId = ContentUris.withAppendedId(SkillContract.SkillEntry.CONTENT_URI, id);
                i.setData(currentSkillId);
                startActivity(i);
            }
        });

        mAdapter = new SkillCursorAdapter(this, null);
        list.setAdapter(mAdapter);

        // at this point our listview is empty.
        getSupportLoaderManager().initLoader(SKILL_LOADER_ID, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //instead of super, just inflate the new menu on the existing menu
        //return true to be visible
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // We get the menu item ID we defined in the XML
        switch (item.getItemId()) {
            case R.id.delete_all:
                //does nothing for now
                return true;
            case R.id.add_dummy_data:

                ContentValues values = new ContentValues();

                values.put(SkillContract.SkillEntry.COLUMN_SPORT, "Boxing");
                values.put(SkillContract.SkillEntry.COLUMN_SKILL_NAME, "jab");
                values.put(SkillContract.SkillEntry.COLUMN_DIFFICULTY, SkillContract.SkillEntry.DIFFICULTY_1);

                Uri newRowUri = getContentResolver().insert(SkillContract.SkillEntry.CONTENT_URI, values);
                Log.v("CatalogActivity", "The new row's ID is: " + newRowUri);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String []projection = {
                SkillContract.SkillEntry.COLUMN_ID,
                SkillContract.SkillEntry.COLUMN_SKILL_NAME,
                SkillContract.SkillEntry.COLUMN_SPORT,
                SkillContract.SkillEntry.COLUMN_DIFFICULTY,
        };

        return new CursorLoader(this,
                SkillContract.SkillEntry.CONTENT_URI, projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}