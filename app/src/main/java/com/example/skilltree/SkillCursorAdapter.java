package com.example.skilltree;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.skilltree.data.SkillContract;

public class SkillCursorAdapter extends CursorAdapter {


    public SkillCursorAdapter(Context context, Cursor c) {
        super(context, c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_view,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView title = view.findViewById(R.id.text_list_item_title);
        TextView difficulty = view.findViewById(R.id.list_item_difficulty);

        String moveName = cursor.getString(cursor.getColumnIndex(SkillContract.SkillEntry.COLUMN_SKILL_NAME));
        int skillDifficulty = cursor.getInt(cursor.getColumnIndex(SkillContract.SkillEntry.COLUMN_DIFFICULTY));
    }
}
