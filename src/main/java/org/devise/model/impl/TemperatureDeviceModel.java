package org.devise.model.impl;

import org.devise.model.DeviceModel;

/**
 * The data device model for temperature readings
 *
 * @author Kseniia Orlenko
 * @version 5/16/18
 */
public class TemperatureDeviceModel extends DeviceModel {

    private long temperature;

    public long getTemperature() {
        return temperature;
    }

    public void setTemperature(long temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "TemperatureDeviceModel{" +
                "temperature=" + temperature +
                ", id='" + id + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
