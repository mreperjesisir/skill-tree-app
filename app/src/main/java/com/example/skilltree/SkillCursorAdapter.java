package com.example.skilltree;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.skilltree.data.SkillContract;

public class SkillCursorAdapter extends CursorAdapter {

    Context mContext;

    public SkillCursorAdapter(Context context, Cursor c) {
        super(context, c,0);
        mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_view,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView title = view.findViewById(R.id.text_list_item_title);
        TextView difficulty = view.findViewById(R.id.list_item_difficulty);
        GradientDrawable difficultyCircle = (GradientDrawable) difficulty.getBackground();

        String moveName = cursor.getString(cursor.getColumnIndex(SkillContract.SkillEntry.COLUMN_SKILL_NAME));
        int skillDifficulty = cursor.getInt(cursor.getColumnIndex(SkillContract.SkillEntry.COLUMN_DIFFICULTY));
        title.setText(moveName);

        switch (skillDifficulty) {
            case SkillContract.SkillEntry.DIFFICULTY_1:
                difficultyCircle.setColor(ContextCompat.getColor(context, R.color.difficulty1));
                break;

            case SkillContract.SkillEntry.DIFFICULTY_2:
                difficultyCircle.setColor(ContextCompat.getColor(context, R.color.difficulty2));
                break;

            case SkillContract.SkillEntry.DIFFICULTY_3:
                difficultyCircle.setColor(ContextCompat.getColor(context, R.color.difficulty3));
                break;

            case SkillContract.SkillEntry.DIFFICULTY_4:
                difficultyCircle.setColor(ContextCompat.getColor(context, R.color.difficulty4));
                break;

            case SkillContract.SkillEntry.DIFFICULTY_5:
                difficultyCircle.setColor(ContextCompat.getColor(context, R.color.difficulty5));
                break;

            case SkillContract.SkillEntry.DIFFICULTY_6:
                difficultyCircle.setColor(ContextCompat.getColor(context, R.color.difficulty6));
                break;

            case SkillContract.SkillEntry.DIFFICULTY_7:
                difficultyCircle.setColor(ContextCompat.getColor(context, R.color.difficulty7));
                break;

            case SkillContract.SkillEntry.DIFFICULTY_8:
                difficultyCircle.setColor(ContextCompat.getColor(context, R.color.difficulty8));
                break;

            case SkillContract.SkillEntry.DIFFICULTY_9:
                difficultyCircle.setColor(ContextCompat.getColor(context, R.color.difficulty9));
                break;

            case SkillContract.SkillEntry.DIFFICULTY_10:
                difficultyCircle.setColor(ContextCompat.getColor(context, R.color.difficulty10));
                break;

            default:
                throw new IllegalArgumentException("The difficulty is out of range");
        }

    }
}
