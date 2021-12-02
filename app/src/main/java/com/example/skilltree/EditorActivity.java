package com.example.skilltree;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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

//TODO: Don't forget to add it to the manifest

public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Uri mUri;
    private int LOADER_ID = 2;

    private TextView mEditSkillName;
    private TextView mEditSportName;
    private Spinner mDifficultySpinner;

    private int mDifficulty = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        //pull layout views
        mEditSkillName = findViewById(R.id.edit_skill_name);
        mEditSportName = findViewById(R.id.edit_sport_name);
        mDifficultySpinner = findViewById(R.id.spinner_difficulty);

        //retrieve intent
        Intent intent = getIntent();
        mUri = intent.getData();
    }

    private void setupSpinner(){

        ArrayAdapter difficultySpinnerAdapter =ArrayAdapter.createFromResource(this,
                R.array.array_difficulty_options,
                android.R.layout.simple_spinner_item);

        difficultySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mDifficultySpinner.setAdapter(difficultySpinnerAdapter);
        //TODO: setting up the spinner is still tricky to me, figure it out!
        mDifficultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!selection.isEmpty()){
                    if (selection.equals(SkillContract.SkillEntry.DIFFICULTY_1)){
                        mDifficulty = selection
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

        mEditSportName.setText(skillName);
        mEditSportName.setText(sportName);
        mDifficultySpinner.setSelection(difficulty);
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {
        mEditSportName;
    }
}
