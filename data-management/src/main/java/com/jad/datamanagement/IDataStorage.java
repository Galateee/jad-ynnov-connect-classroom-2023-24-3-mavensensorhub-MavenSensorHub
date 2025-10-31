package com.jad.datamanagement;

import com.jad.sensorapi.SensorData;
import com.jad.sensorapi.SensorType;

import java.util.List;

public interface IDataStorage {
    List<SensorData> getAllStoredData();

    List<SensorData> getAllDataBySensorType(SensorType sensorType);
}
