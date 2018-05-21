package org.devise.gererator.impl;

import org.devise.gererator.DeviceDataGenerator;
import org.devise.model.impl.TemperatureDeviceModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * The temperature data generator
 *
 * @author Kseniia Orlenko
 * @version 5/16/18
 */
@Component
public class TemperatureDataGenerator implements DeviceDataGenerator<TemperatureDeviceModel> {

    private static final Random random = new Random();

    @Value("${temperature.device.id}")
    private String deviceId;

    /**
     * Returns a temperature device data with a random temperature value in a range [minTemp, maxTemp]
     * The range is specified in application properties.
     *
     * @return temperature device data, not null
     */
    @Override
    public TemperatureDeviceModel generate() {
        TemperatureDeviceModel model = new TemperatureDeviceModel();
        model.setTemperature(random.nextLong());
        model.setTimestamp(System.currentTimeMillis());
        model.setId(deviceId);
        return model;
    }
}
