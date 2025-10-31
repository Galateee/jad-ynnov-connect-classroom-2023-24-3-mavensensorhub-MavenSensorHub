package com.jad.sensordata;

import com.jad.sensorapi.ISensor;
import com.jad.sensorapi.ISensorFactory;
import com.jad.sensorapi.SensorType;

public class SensorFactory implements ISensorFactory {
    @Override
    public ISensor make(SensorType sensorType) {
        return switch (sensorType) {
            case TEMPERATURE -> new TemperatureSensor();
            case HUMIDITY -> new HumiditySensor();
            case PRESSURE -> new PressureSensor();
            case WIND_SPEED -> new WindSpeedSensor();
        };
    }
}
