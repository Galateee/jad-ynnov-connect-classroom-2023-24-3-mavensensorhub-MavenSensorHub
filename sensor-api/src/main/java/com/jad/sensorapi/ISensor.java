package com.jad.sensorapi;

public interface ISensor {
    SensorType getSensorType();

    SensorData getSensorData();
}
