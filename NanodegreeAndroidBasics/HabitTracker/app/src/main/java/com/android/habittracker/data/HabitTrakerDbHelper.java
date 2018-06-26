package com.android.habittracker.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.habittracker.data.HabitTrackerContract.HabitEntry;

public class HabitTrakerDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "habitTraker.db";
    private static final int DATABASE_VERSION = 1;

    public HabitTrakerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL to create the habit table
        String SQL_CREATE_HABIT_TABLE = "CREATE TABLE " + HabitEntry.TABLE_NAME + " ("
                + HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + HabitEntry.COLUMN_HOUR + " TEXT, "
                + HabitEntry.COLUMN_ALARM + " INTEGER NOT NULL DEFAULT 0)";

        // Execute the SQL
        db.execSQL(SQL_CREATE_HABIT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Inserts a Habit in database.
     * Example of data:
     *  name = "Drink Coffee"
     *  hour = "08:00"
     *  alarm = 1 (means HabitEntry.ALARM_YES)
     *
     * @param name
     * @param hour
     * @param alarm
     * @return long The new rowId.
     */
    public long insertHabit(String name, String hour, int alarm) {

        // Habit values to insert
        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_NAME, name);
        values.put(HabitEntry.COLUMN_HOUR, hour);
        values.put(HabitEntry.COLUMN_ALARM, alarm);

        // Get DataBase connection to write
        SQLiteDatabase db = getWritableDatabase();

        // Return the new rowId or -1 for error
        return db.insert(HabitEntry.TABLE_NAME, null, values);
    }

    /**
     * Read all habits from dataBase
     * @return Cursor Return the Habit Cursos
     */
    public Cursor readHabit() {
        Cursor cursor = null;

        // Get DataBase connection to read
        SQLiteDatabase db = getReadableDatabase();

        // Create a projection with Habit columns
        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_NAME,
                HabitEntry.COLUMN_HOUR,
                HabitEntry.COLUMN_ALARM
        };

        // Execute read query
        cursor = db.query(HabitEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        // Return Habit Cursor
        return cursor;
    }
}
