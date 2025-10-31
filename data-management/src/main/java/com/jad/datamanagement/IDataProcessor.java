package com.jad.datamanagement;

import com.jad.sensorapi.DataQueries;
import com.jad.sensorapi.SensorData;
import com.jad.sensorapi.SensorType;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

interface IDataProcessor extends DataQueries {
    Map<SensorType, Double> calculateAverage();

    Map<SensorType, Double> calculateMax();

    Map<SensorType, Double> calculateMin();

    Map<SensorType, LocalDateTime> calculateLastCollectorTime();

    Optional<SensorData> calculateLastSensorDataBySensorType(SensorType sensorType);
}
