package com.android.habittracker.data;

import android.provider.BaseColumns;

public class HabitTrackerContract {

    public class HabitEntry implements BaseColumns {

        public static final String TABLE_NAME = "habit";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_HOUR = "hour";
        public static final String COLUMN_ALARM = "alarm";

        public static final int ALARM_NO = 0;
        public static final int ALARM_YES = 1;
    }
}
