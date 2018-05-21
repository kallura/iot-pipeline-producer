package org.devise.model;

/**
 * The device data model that need to be extended by any new device
 *
 * @author Kseniia Orlenko
 * @version 5/16/18
 */
public class DeviceModel {

    protected String id;
    protected long timestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
