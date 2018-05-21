package org.devise.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * The publisher interface that should be implemented for eny new device
 *
 * @author Kseniia Orlenko
 * @version 5/16/18
 */
public interface Publisher {

    /**
     * Publishes a message in a schedule mode
     *
     * @throws JsonProcessingException in case if can't convert object to json
     * @throws MqttException           in case if can't publish a message to topic
     */
    void publish() throws JsonProcessingException, MqttException;
}
