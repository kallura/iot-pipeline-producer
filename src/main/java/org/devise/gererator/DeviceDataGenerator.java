package org.devise.gererator;

import org.devise.model.DeviceModel;

/**
 * The device data generator
 *
 * @author Kseniia Orlenko
 * @version 5/16/18
 */
public interface DeviceDataGenerator<T extends DeviceModel> {

    /**
     * Returns a device data
     *
     * @return device model, not null
     */
    T generate();
}
