package com.example.skilltree;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.example.skilltree.data.SkillContract;

public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Uri mUri;
    private int LOADER_ID = 2;

    private TextView mEditSkillName;
    private TextView mEditSportName;
    private Spinner mDifficultySpinner;



    private int mDifficulty;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        //TODO: Add a menu and implement the save and delete functions

        //pull layout views
        mEditSkillName = findViewById(R.id.edit_skill_name);
        mEditSportName = findViewById(R.id.edit_sport_name);
        mDifficultySpinner = findViewById(R.id.spinner_difficulty);
        setupSpinner();

        //retrieve intent
        Intent intent = getIntent();
        mUri = intent.getData();

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    private void setupSpinner(){

        ArrayAdapter difficultySpinnerAdapter =ArrayAdapter.createFromResource(this,
                R.array.array_difficulty_options,
                android.R.layout.simple_spinner_item);

        difficultySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mDifficultySpinner.setAdapter(difficultySpinnerAdapter);
        mDifficultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selection = (int) parent.getItemIdAtPosition(position);
                mDifficulty = selection;
                Log.d("EDITORTAG", "Difficulty selection id is " + selection);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mDifficulty = SkillContract.SkillEntry.DIFFICULTY_1;
            }
        });

    }

    @NonNull
    @Override
    public Loader onCreateLoader(int id, @Nullable Bundle args) {

        String[] projection = {
                SkillContract.SkillEntry.COLUMN_SKILL_NAME,
                SkillContract.SkillEntry.COLUMN_SPORT,
                SkillContract.SkillEntry.COLUMN_DIFFICULTY,
        };

        CursorLoader cLoader = new CursorLoader(this, mUri, projection,
                null, null, null);

        return cLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        data.moveToFirst();

        String skillName = data.getString(data.getColumnIndex(SkillContract.SkillEntry.COLUMN_SKILL_NAME));
        String sportName = data.getString(data.getColumnIndex(SkillContract.SkillEntry.COLUMN_SPORT));
        int difficulty = data.getInt(data.getColumnIndex(SkillContract.SkillEntry.COLUMN_DIFFICULTY));

        mEditSkillName.setText(skillName);
        mEditSportName.setText(sportName);
        mDifficultySpinner.setSelection(difficulty);
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {
        mEditSportName.setText("");
        mEditSportName.setText("");
        mDifficultySpinner.setSelection(0);
    }
}
