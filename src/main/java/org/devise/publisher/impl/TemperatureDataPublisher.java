package org.devise.publisher.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.devise.gererator.DeviceDataGenerator;
import org.devise.gererator.impl.TemperatureDataGenerator;
import org.devise.model.DeviceModel;
import org.devise.publisher.Publisher;
import org.devise.wrapper.MqttClientWrapper;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Temperature data publisher
 *
 * @author Kseniia Orlenko
 * @version 5/17/18
 */
@Component
public class TemperatureDataPublisher implements Publisher {

    private static final Logger logger = LoggerFactory.getLogger(TemperatureDataPublisher.class);

    private final ObjectMapper objectMapper;
    private final MqttClientWrapper mqttClientWrapper;
    private final DeviceDataGenerator temperatureDataGenerator;

    @Value("${temperature.topic}")
    private String topic;

    public TemperatureDataPublisher(ObjectMapper objectMapper,
                                    MqttClientWrapper mqttClientWrapper,
                                    TemperatureDataGenerator temperatureDataGenerator) {
        this.objectMapper = objectMapper;
        this.mqttClientWrapper = mqttClientWrapper;
        this.temperatureDataGenerator = temperatureDataGenerator;
    }

    /**
     * Publishes a temperature data to temperature topic in a schedule mode: one message per second
     *
     * @throws JsonProcessingException in case if can't convert object to json
     * @throws MqttException           in case if can't publish a message to topic
     */
    @Async
    @Override
    @Scheduled(cron = "*/1 * * * * *")
    public void publish() throws JsonProcessingException, MqttException {
        DeviceModel data = temperatureDataGenerator.generate();
        byte[] payload = objectMapper.writeValueAsBytes(data);
        mqttClientWrapper.publish(topic, payload);
        logger.info("The temperature data: {} was posted to the broker topic: {}!", data, topic);
    }
}
