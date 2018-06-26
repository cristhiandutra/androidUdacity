package com.android.habittracker;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.habittracker.data.HabitTrackerContract.HabitEntry;
import com.android.habittracker.data.HabitTrakerDbHelper;

public class HabitActivity extends AppCompatActivity {

    private HabitTrakerDbHelper mDbHeper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);

        mDbHeper = new HabitTrakerDbHelper(this);

        long newRowId = mDbHeper.insertHabit("Drink Coffee", "08:00", HabitEntry.ALARM_YES);

        if (newRowId == -1) {
            Log.e("HabitActivity", "Error with saving Habit ");
        } else {
            Log.v("HabitActivity", "New row ID "+newRowId);
        }

        Cursor cursor = mDbHeper.readHabit();
        if (cursor != null) {

            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_NAME);
            int hourColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HOUR);
            int alarmColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_ALARM);

            while (cursor.moveToNext()) {

                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentHour = cursor.getString(hourColumnIndex);
                int currentAlarm = cursor.getInt(alarmColumnIndex);

                Log.v("HabitActivity", "Habit("+currentID+", "+currentName+", "+currentHour+", "+currentAlarm+")");
            }
        }

    }
}
