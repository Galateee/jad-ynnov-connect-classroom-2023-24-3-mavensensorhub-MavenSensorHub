package com.jad.userinterface;

import com.jad.sensorapi.DataQueries;

public interface IApplication {
    void manageOrder(UserAction userAction);

    DataQueries getDataQueries();
}
