package DataAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Models.AvgSensorReading;

public class AvgSensorReadingRepository {
    SqliteManager sqliteManager;
    SQLiteDatabase sqliteWritableDatabase;
    SQLiteDatabase sqliteReadableDatabase;

    public AvgSensorReadingRepository(Context context) {
        sqliteManager = new SqliteManager(context);
        sqliteWritableDatabase = sqliteManager.getWritableDatabase();
        sqliteReadableDatabase = sqliteManager.getReadableDatabase();
    }

    public void Create(AvgSensorReading avgSensorReading) {
        ContentValues cv = new ContentValues();
        cv.put(sqliteManager.ColumnReading, avgSensorReading.sensorReading);
        sqliteWritableDatabase.insert(sqliteManager.SensorReadingsTable, null, cv);
    }

    public List<AvgSensorReading> ReadAll() {
        Cursor cursor = sqliteReadableDatabase.rawQuery("SELECT * FROM " + sqliteManager.SensorReadingsTable, null);
        List<AvgSensorReading> readings = new ArrayList<>();

        if (cursor.moveToFirst()) {
// loop through the cursor (result set) and create new customer
            do {
                double readingValue = cursor.getDouble(1);
                String readingTime = cursor.getString(2);
                AvgSensorReading reading = new AvgSensorReading(readingValue, readingTime);
                readings.add(reading);
            } while (cursor.moveToNext());
        }
        return readings;
    }

    public AvgSensorReading getMin() {
        Cursor cursor = sqliteReadableDatabase.rawQuery("SELECT * FROM " + sqliteManager.SensorReadingsTable + " ORDER BY " + sqliteManager.ColumnReading + " ASC LIMIT 1", null);
        AvgSensorReading reading = null;
        if (cursor.moveToFirst()) {
// loop through the cursor (result set) and create new customer
            do {
                double readingValue = cursor.getDouble(1);
                String readingTime = cursor.getString(2);
                reading = new AvgSensorReading(readingValue, readingTime);
            } while (cursor.moveToNext());
        }
        return reading;
    }

    public AvgSensorReading getMax() {
        Cursor cursor = sqliteReadableDatabase.rawQuery("SELECT * FROM " + sqliteManager.SensorReadingsTable + " ORDER BY " + sqliteManager.ColumnReading + " DESC LIMIT 1", null);
        AvgSensorReading reading = null;
        if (cursor.moveToFirst()) {
// loop through the cursor (result set) and create new customer
            do {
                double readingValue = cursor.getDouble(1);
                String readingTime = cursor.getString(2);
                reading = new AvgSensorReading(readingValue, readingTime);
            } while (cursor.moveToNext());
        }
        return reading;
    }
}
