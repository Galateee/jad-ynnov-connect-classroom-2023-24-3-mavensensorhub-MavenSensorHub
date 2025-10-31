package com.jad.sensorapi;

public interface ISensorFactory {
    ISensor make(SensorType sensorType);
}
