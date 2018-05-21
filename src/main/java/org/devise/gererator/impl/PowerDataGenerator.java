package org.devise.gererator.impl;

import org.devise.gererator.DeviceDataGenerator;
import org.devise.model.impl.PowerDeviceModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * The power data generator
 *
 * @author Kseniia Orlenko
 * @version 5/16/18
 */
@Component
public class PowerDataGenerator implements DeviceDataGenerator<PowerDeviceModel> {

    private static final Random random = new Random();

    @Value("${power.device.id}")
    private String deviceId;

    /**
     * Returns a power device data
     *
     * @return power device data, not null
     */
    @Override
    public PowerDeviceModel generate() {
        PowerDeviceModel model = new PowerDeviceModel();
        model.setTimestamp(System.currentTimeMillis());
        model.setPower(random.nextLong());
        model.setId(deviceId);
        return model;
    }
}
