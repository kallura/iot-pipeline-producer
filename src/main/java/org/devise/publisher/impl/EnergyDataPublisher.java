package org.devise.publisher.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.devise.wrapper.MqttClientWrapper;
import org.devise.gererator.DeviceDataGenerator;
import org.devise.gererator.impl.EnergyDataGenerator;
import org.devise.model.DeviceModel;
import org.devise.publisher.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Energy data publisher
 *
 * @author Kseniia Orlenko
 * @version 5/16/18
 */
@Component
public class EnergyDataPublisher implements Publisher {

    private static final Logger loggger = LoggerFactory.getLogger(EnergyDataPublisher.class);

    private final ObjectMapper objectMapper;
    private final MqttClientWrapper mqttClientWrapper;
    private final DeviceDataGenerator energyDataGenerator;

    @Value("${energy.topic}")
    private String topic;

    @Autowired
    public EnergyDataPublisher(ObjectMapper objectMapper,
                               MqttClientWrapper mqttClientWrapper,
                               EnergyDataGenerator energyDataGenerator) {
        this.objectMapper = objectMapper;
        this.energyDataGenerator = energyDataGenerator;
        this.mqttClientWrapper = mqttClientWrapper;
    }


    /**
     * Publishes a energy data to energy topic in a schedule mode: one message per second
     *
     * @throws JsonProcessingException in case if can't convert object to json
     * @throws MqttException           in case if can't publish a message to topic
     */
    @Async
    @Override
    @Scheduled(cron = "*/1 * * * * *")
    public void publish() throws JsonProcessingException, MqttException {
        DeviceModel data = energyDataGenerator.generate();
        byte[] payload = objectMapper.writeValueAsBytes(data);
        mqttClientWrapper.publish(topic, payload);
        loggger.info("The energy data: {} was posted to the broker topic: {}!", data, topic);
    }
}
