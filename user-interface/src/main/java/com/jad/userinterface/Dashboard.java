package com.jad.userinterface;

import com.jad.sensorapi.DataQueries;
import com.jad.sensorapi.SensorData;
import com.jad.sensorapi.SensorType;

import java.text.MessageFormat;
import java.util.List;

class Dashboard {
    private final DataQueries dataManager;

    public Dashboard(final DataQueries dataManager) {
        this.dataManager = dataManager;
    }

    public void display() {
        System.out.println("--Dashboard--");
        for (SensorType sensorType : SensorType.values()) {
            System.out.println(MessageFormat.format("{0}({1}) : min:{2} max:{3} avg:{4} last:{5}",
                                                    sensorType.getSensorName(),
                                                    sensorType.getUnit(),
                                                    this.dataManager.calculateMinBySensorType(sensorType),
                                                    this.dataManager.calculateMaxBySensorType(sensorType),
                                                    this.dataManager.calculateAverageBySensorType(sensorType),
                                                    this.dataManager.calculateLastCollectorTimeBySensorType(
                                                            sensorType)));
        }
    }

    public void displayAllData(final List<SensorData> allData) {
        System.out.println("--All Data--");
        for (SensorData sensorData : allData) {
            System.out.println(MessageFormat.format("{0} : {1} {2} {3}",
                                                    sensorData.time(),
                                                    sensorData.getSensorName(),
                                                    sensorData.value(),
                                                    sensorData.unit()));
        }
    }
}
