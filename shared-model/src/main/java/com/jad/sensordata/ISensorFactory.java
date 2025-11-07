package com.jad.sensordata;

public interface ISensorFactory {
    ISensor make(SensorType sensorType);
}
