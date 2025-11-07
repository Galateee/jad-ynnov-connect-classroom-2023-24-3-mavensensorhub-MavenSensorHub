package com.jad.datamanagement;

import com.jad.sensordata.ISensor;
import com.jad.sensordata.SensorData;

import java.util.List;

public interface IDataManager extends IDataProcessor {
    void addDataCollector(ISensor sensor);

    void collectAndStoreData();

    List<SensorData> getAllData();
}
