package DataAccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class SqliteManager extends SQLiteOpenHelper {
    public static final String SensorReadingsTable="sensor_readings";
    public static final String DurationQualityTable="durations_qualities";

    public static final String ColumnHours="hours";
    public static final String ColumnMinutes="minutes";
    public static final String ColumnQualities="qualities";
    public static final String ColumnId="id";
    public static final String ColumnReading="reading";
    public static final String ColumnDateTime="date_time";

    public SqliteManager(Context context) {
        super(context, "SensorReadsDb",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+SensorReadingsTable+"("
                +ColumnId+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +ColumnReading+" REAL NOT NULL, "
                +ColumnDateTime+" DATETIME DEFAULT CURRENT_TIMESTAMP"+")");
        db.execSQL("CREATE TABLE "+DurationQualityTable+"("
                +ColumnId+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +ColumnHours+" INTEGER NOT NULL, "
                +ColumnMinutes+" INTEGER NOT NULL, "
                +ColumnQualities+" INTEGER NOT NULL"+")");
        db.execSQL("INSERT INTO "+DurationQualityTable+"("+ColumnHours+","+ColumnMinutes+","+ColumnQualities+") VALUES(0,0,0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
