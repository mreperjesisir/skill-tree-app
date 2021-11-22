package com.example.skilltree;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

        ListView list = findViewById(R.id.list_view_moves);
        mAdapter = new SkillCursorAdapter(this, null);
        list.setAdapter(mAdapter);

        // at this point our listview is empty.
        // TODO: time to load a cursor from the database
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
                //does nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        //TODO: add the Uri and projection into the constructor below
        String []projection = {
                SkillContract.SkillEntry.COLUMN_ID,
                SkillContract.SkillEntry.COLUMN_SKILL_NAME,
                SkillContract.SkillEntry.COLUMN_SPORT,
                SkillContract.SkillEntry.COLUMN_DIFFICULTY,
        };

        return new CursorLoader(this);
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