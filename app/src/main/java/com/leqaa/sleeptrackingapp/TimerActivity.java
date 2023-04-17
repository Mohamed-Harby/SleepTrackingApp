package com.leqaa.sleeptrackingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import DataAccess.AvgSensorReadingRepository;
import DataAccess.DurationQualityRepository;
import Models.AvgSensorReading;
import Models.DurationQuality;

public class TimerActivity extends AppCompatActivity {
    private TextView timerValue;
    private DataAccess.AvgSensorReadingRepository avgSensorReadingRepository;
    private DataAccess.DurationQualityRepository durationQualityRepository;
    private Timer timer, sensorTimer;
    private int hours, minutes, seconds;

    private TimerTask timerTask, sensorTimerTask;
    private Button stopTrackingButton;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    List<Float> sensorReadings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_timer);

        timerValue = findViewById(R.id.time_value);
        durationQualityRepository=new DurationQualityRepository(this);
        avgSensorReadingRepository = new AvgSensorReadingRepository(this);
        sensorTimer = new Timer();
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                seconds++;
                if (seconds == 60) {
                    seconds = 0;
                    minutes++;
                }
                if (minutes == 60) {
                    minutes = 0;
                    hours++;
                }
                runOnUiThread(() -> {
                    String time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
                    timerValue.setText(time);
                });
            }
        };
        sensorTimerTask = new TimerTask() {
            @Override
            public void run() {
                float sum = 0;
                for (Float sensorReading : sensorReadings) {
                    sum += sensorReading;
                }
                float avg = sum / sensorReadings.size();
                avgSensorReadingRepository.Create(new AvgSensorReading(avg));
                sensorReadings.clear();
                for (Models.AvgSensorReading avgSensorReading : avgSensorReadingRepository.ReadAll()
                ) {
                    System.out.println(avgSensorReading.sensorReading);
                }
            }
        };
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        stopTrackingButton = findViewById(R.id.stop_tracking);

        stopTrackingButton.setOnClickListener(v -> {
            timer.cancel();
            sensorTimer.cancel();
            Intent intent = new Intent(TimerActivity.this, MainActivity.class);
            int quality= getQuality();
            durationQualityRepository.create(new DurationQuality(hours, minutes, quality));
            intent.putExtra("hours", hours);
            intent.putExtra("minutes", minutes);
            intent.putExtra("quality", quality);

            startActivity(intent);
        });
    }

    public int getQuality() {
        double maxValue = avgSensorReadingRepository.getMax().sensorReading;
        double minValue = avgSensorReadingRepository.getMin().sensorReading;
        double sub = maxValue - minValue;
        double threshold = (sub / 13) + minValue;
        int underThresholdCount = 0;
        List<AvgSensorReading> readings = avgSensorReadingRepository.ReadAll();
        int fullCount = readings.size();
        for (AvgSensorReading reading : readings) {
            if (reading.sensorReading <= threshold) {
                underThresholdCount++;
            }
        }
        System.out.println("threshold  =  "+threshold);
        System.out.println("result = "+ ((underThresholdCount*100 / fullCount) ));
        return (int) ((underThresholdCount*100 / fullCount));

    }

    @Override
    protected void onStart() {
        timer.scheduleAtFixedRate(timerTask, 1000, 1000);
        sensorTimer.scheduleAtFixedRate(sensorTimerTask, 1000, 500);

        sensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                double x = event.values[0];
                double y = event.values[1];
                double z = event.values[2];

                sensorReadings.add((float) Math.sqrt(x * x + y * y + z * z));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        }, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        super.onStart();
    }
}