package org.devise.gererator.impl;

import org.devise.gererator.DeviceDataGenerator;
import org.devise.model.impl.EnergyDeviceModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * The energy data generator
 *
 * @author Kseniia Orlenko
 * @version 5/16/18
 */
@Component
public class EnergyDataGenerator implements DeviceDataGenerator<EnergyDeviceModel> {

    private static final Random random = new Random();

    @Value("${energy.device.id}")
    private String deviceId;

    /**
     * Returns a energy device data
     *
     * @return energy device data, not null
     */
    @Override
    public EnergyDeviceModel generate() {
        EnergyDeviceModel model = new EnergyDeviceModel();
        model.setId(deviceId);
        model.setEnergy(random.nextLong());
        model.setTimestamp(System.currentTimeMillis());
        return model;
    }
}
