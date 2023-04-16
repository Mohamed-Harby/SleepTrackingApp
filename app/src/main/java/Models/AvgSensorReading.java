package Models;

import java.time.LocalDateTime;
import java.util.Date;

public class AvgSensorReading {
    public AvgSensorReading(double reading,String dateTime){
        this.sensorReading=reading;
        this.dateTime=dateTime;
    }
    public double sensorReading;
    public String dateTime;
}
