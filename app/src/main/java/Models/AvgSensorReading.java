package Models;

import androidx.annotation.Nullable;

import java.time.LocalDateTime;
import java.util.Date;

public class AvgSensorReading {
    public AvgSensorReading(double reading) {
        this.sensorReading = reading;
        this.dateTime = null;
    }

    public AvgSensorReading(double reading, String dateTime) {
        this.sensorReading = reading;
        this.dateTime = dateTime;
    }

    public double sensorReading;
    @Nullable
    public String dateTime;
}
