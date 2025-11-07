package com.jad.datamanagement;

import com.jad.sensordata.SensorData;
import com.jad.sensordata.SensorType;

import java.util.List;

public interface IDataStorage {
    List<SensorData> getAllStoredData();

    List<SensorData> getAllDataBySensorType(SensorType sensorType);
}
