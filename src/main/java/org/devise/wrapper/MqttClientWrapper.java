package org.devise.wrapper;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Wraps the mqtt client and does a configuration
 *
 * @author Kseniia Orlenko
 * @version 5/16/18
 */
@Component
public class MqttClientWrapper {

    @Value("${mqtt.broker}")
    private String broker;
    @Value("${mqtt.client.id}")
    private String clientId;

    private MqttClient mqttClient;

    /**
     * Publishes a message to mqtt broker
     *
     * @param topic is a topic in broker
     * @param data  is a object that should be published
     */
    public void publish(final String topic, final byte[] data) throws MqttException {
        mqttClient.publish(topic, new MqttMessage(data));
    }

    /**
     * Creates a mqtt client and connects to a broker
     */
    @PostConstruct
    public void init() throws MqttException {
        mqttClient = new MqttClient(broker, clientId, new MemoryPersistence());
        mqttClient.connect(new MqttConnectOptions());
    }

    /**
     * Disconnects from a broker
     */
    @PreDestroy
    public void destroy() throws MqttException {
        mqttClient.disconnect();
    }
}
