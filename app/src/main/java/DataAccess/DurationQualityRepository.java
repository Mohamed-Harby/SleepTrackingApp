package DataAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import Models.DurationQuality;

public class DurationQualityRepository {
    SqliteManager sqliteManager;
    SQLiteDatabase sqliteWritableDatabase;
    SQLiteDatabase sqliteReadableDatabase;

    public DurationQualityRepository(Context context) {
        sqliteManager = new SqliteManager(context);
        sqliteWritableDatabase = sqliteManager.getWritableDatabase();
        sqliteReadableDatabase = sqliteManager.getReadableDatabase();
    }
    public void create(DurationQuality durationQuality) {
        ContentValues cv = new ContentValues();
        cv.put(sqliteManager.ColumnHours, durationQuality.hours);
        cv.put(sqliteManager.ColumnMinutes, durationQuality.minutes);
        cv.put(sqliteManager.ColumnQualities, durationQuality.quality);
        sqliteWritableDatabase.insert(sqliteManager.DurationQualityTable, null, cv);
    }

    public List<DurationQuality> readAll(){
        Cursor cursor=sqliteReadableDatabase.rawQuery("SELECT * FROM "+sqliteManager.DurationQualityTable,null);
        List<DurationQuality> durations=new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int hours = cursor.getInt(  1);
                int minutes = cursor.getInt(  2);
                int quality = cursor.getInt(  3);
                DurationQuality duration=new DurationQuality(hours,minutes,quality);
                durations.add(duration);
            } while (cursor.moveToNext());
        }
        return durations;
    }
    public DurationQuality readLast(){
        Cursor cursor=sqliteReadableDatabase.rawQuery("SELECT * FROM "+sqliteManager.DurationQualityTable+" ORDER BY "+sqliteManager.ColumnId+" DESC LIMIT 1",null);
        DurationQuality duration=null;
        if (cursor.moveToFirst()) {
            do {
                int hours = cursor.getInt(  1);
                int minutes = cursor.getInt(  2);
                int quality = cursor.getInt(  3);
                duration=new DurationQuality(hours,minutes,quality);
            } while (cursor.moveToNext());
        }
        return duration;
    }


}
