package com.leqaa.sleeptrackingapp;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import DataAccess.AvgSensorReadingRepository;
import DataAccess.DurationQualityRepository;
import Models.DurationQuality;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private TextView qualityValue;
    private TextView durationValue;
    private Button startTrackingBtn;
    private ProgressBar qualityProgressBar;
    private ProgressBar durationProgressBar;
    private AvgSensorReadingRepository avgSensorReadingRepository;
    private DurationQualityRepository durationQualityRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);

        durationQualityRepository=new DurationQualityRepository(this);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        qualityValue = findViewById(R.id.quality_result);
        durationValue = findViewById(R.id.duration_result);
        startTrackingBtn = findViewById(R.id.start_tracking);
        qualityProgressBar = findViewById(R.id.quality_progress);
        durationProgressBar=findViewById(R.id.duration_progress);

//        Bundle bundle = getIntent().getExtras();
//        System.out.println(bundle);
//        if (bundle != null) {
//            double quality = bundle.getDouble("quality");
//            int hours = bundle.getInt("hours");
//            int minutes = bundle.getInt("minutes");
//            LocalTime time = LocalTime.of(hours, minutes);
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
//            String formattedTime = time.format(formatter);
//            durationValue.setText(formattedTime);
//            qualityValue.setText((int)quality+"%");
//            qualityProgressBar.setProgress((int)quality);
//            int allMinutes=hours*60+minutes;
//            if(allMinutes>480)
//                durationProgressBar.setProgress(100);
//            else
//                durationProgressBar.setProgress(allMinutes*100/480);
//        }
//        else{
            DurationQuality durationQuality = durationQualityRepository.readLast();
            int hours = durationQuality.hours;
            int minutes = durationQuality.minutes;
            LocalTime time = LocalTime.of(hours, minutes);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            String formattedTime = time.format(formatter);
            qualityValue.setText(durationQuality.quality+"%");
            durationValue.setText(formattedTime);
            qualityProgressBar.setProgress(durationQuality.quality);
            int allMinutes=hours*60+minutes;
            if(allMinutes>480)
                durationProgressBar.setProgress(100);
            else
                durationProgressBar.setProgress(allMinutes*60/480);

//        }
        startTrackingBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TimerActivity.class);
            startActivity(intent);
        });

        avgSensorReadingRepository = new AvgSensorReadingRepository(this);
    }
}