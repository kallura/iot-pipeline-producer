package org.devise.model.impl;

import org.devise.model.DeviceModel;

/**
 * The data device model for power readings
 *
 * @author Kseniia Orlenko
 * @version 5/16/18
 */
public class PowerDeviceModel extends DeviceModel {

    private long power;

    public long getPower() {
        return power;
    }

    public void setPower(long power) {
        this.power = power;
    }

    @Override
    public String toString() {
        return "PowerDeviceModel{" +
                "power=" + power +
                ", id='" + id + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
