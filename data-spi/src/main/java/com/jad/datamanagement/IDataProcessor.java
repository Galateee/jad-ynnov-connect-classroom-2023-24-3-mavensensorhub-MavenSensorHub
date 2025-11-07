package com.jad.datamanagement;

import com.jad.sensordata.SensorData;
import com.jad.sensordata.SensorType;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

public interface IDataProcessor {
    Map<SensorType, Double> calculateAverage();

    Map<SensorType, Double> calculateMax();

    Map<SensorType, Double> calculateMin();

    Map<SensorType, LocalDateTime> calculateLastCollectorTime();

    Double calculateAverageBySensorType(SensorType sensorType);

    Double calculateMaxBySensorType(SensorType sensorType);

    Double calculateMinBySensorType(SensorType sensorType);

    LocalDateTime calculateLastCollectorTimeBySensorType(SensorType sensorType);

    Optional<SensorData> calculateLastSensorDataBySensorType(SensorType sensorType);
}
