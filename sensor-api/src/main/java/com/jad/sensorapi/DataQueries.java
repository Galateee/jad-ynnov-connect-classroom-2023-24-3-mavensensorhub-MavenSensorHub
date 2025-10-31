package com.jad.sensorapi;

import java.time.LocalDateTime;

public interface DataQueries {
    Double calculateAverageBySensorType(SensorType sensorType);
    Double calculateMaxBySensorType(SensorType sensorType);
    Double calculateMinBySensorType(SensorType sensorType);
    LocalDateTime calculateLastCollectorTimeBySensorType(SensorType sensorType);
}
