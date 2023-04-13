package com.leqaa.sleeptrackingapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;



import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private SensorEventListener selg; // sensor event listener for gyroscope
    private SensorEventListener sela;
    private TextView gyroscopeReading;
    private TextView accelerometerReading;
    private Sensor gyroscope ;
    private Sensor accelerometer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);

        gyroscope=sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        accelerometer=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

//        gyroscopeReading=(TextView) findViewById(R.id.gyroscopeReading);
//        accelerometerReading=(TextView) findViewById(R.id.accelerometerReading);

        selg=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
//                gyroscopeReading.setText(String.valueOf(event.values[1]));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                System.out.println("hello world!");
            }
        };
        sela=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
//                accelerometerReading.setText(String.valueOf(event.values[0]));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }
    @Override
    public void onResume(){
        sensorManager.registerListener(selg,gyroscope,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sela,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }
    @Override
    public void onPause(){
        sensorManager.unregisterListener(selg,gyroscope);
        sensorManager.unregisterListener(sela,accelerometer);
        super.onPause();
    }



}