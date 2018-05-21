package org.devise.model.impl;

import org.devise.model.DeviceModel;

/**
 * The data device model for energy readings
 *
 * @author Kseniia Orlenko
 * @version 5/16/18
 */
public class EnergyDeviceModel extends DeviceModel {

    private long energy;

    public long getEnergy() {
        return energy;
    }

    public void setEnergy(long energy) {
        this.energy = energy;
    }

    @Override
    public String toString() {
        return "EnergyDeviceModel{" +
                "energy=" + energy +
                ", id='" + id + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
