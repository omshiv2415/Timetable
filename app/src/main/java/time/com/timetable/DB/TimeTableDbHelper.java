package time.com.timetable.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by VIRALKUMAR on 09/04/2016.
 */
public class TimeTableDbHelper extends SQLiteOpenHelper {

    private static final String CREATE_QUERY =
            "CREATE TABLE " + TimeTableDataBase.NewTimeTable.TABLE_NAME + "(" + TimeTableDataBase.NewTimeTable.MODULE_CODE + " TEXT, " +
                    TimeTableDataBase.NewTimeTable.START_TIME + " TEXT, " + TimeTableDataBase.NewTimeTable.FINISH_TIME + " TEXT);";
    private static final String DATABASE_NAME = "TIMETABLE.DB";
    private static final int VERSION = 1;
    SQLiteDatabase db;

    public TimeTableDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        Log.e("DATABASE OPERATIONS", "Database created / opened..");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_QUERY);

        Log.e("DATABASE OPERATIONS", "Table created / opened..");
    }

    public void addTimeTableDetail(String modulecode, String starttime, String finishtime, SQLiteDatabase db) {


        ContentValues contentValues = new ContentValues();
        contentValues.put(TimeTableDataBase.NewTimeTable.MODULE_CODE, modulecode);
        contentValues.put(TimeTableDataBase.NewTimeTable.START_TIME, starttime);
        contentValues.put(TimeTableDataBase.NewTimeTable.FINISH_TIME, finishtime);
        db.insert(TimeTableDataBase.NewTimeTable.TABLE_NAME, null, contentValues);
        Log.e("DATABASE OPERATIONS", "Data inserted in one raw / opened..");

    }

    public Cursor getTimeTable(SQLiteDatabase db) {


        Cursor cursor;

        String[] projections = {TimeTableDataBase.NewTimeTable.MODULE_CODE, TimeTableDataBase.NewTimeTable.START_TIME,
                TimeTableDataBase.NewTimeTable.FINISH_TIME,};
        cursor = db.query(TimeTableDataBase.NewTimeTable.TABLE_NAME, projections, null, null, null, null, null);
        return cursor;
    }




    public void deleteRowFromTimeTable(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TimeTableDataBase.NewTimeTable.TABLE_NAME, TimeTableDataBase.NewTimeTable.MODULE_CODE + " LIKE?",
                new String[]{id});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
