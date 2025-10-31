package com.jad.datamanagement;

import com.jad.sensorapi.ISensor;
import com.jad.sensorapi.SensorData;

import java.util.List;

public interface IDataManager extends IDataProcessor {
    void addDataCollector(ISensor sensor);

    void collectAndStoreData();

    List<SensorData> getAllData();
}
