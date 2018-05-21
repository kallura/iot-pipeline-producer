package org.devise.publisher.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.io.JsonEOFException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.devise.gererator.impl.TemperatureDataGenerator;
import org.devise.model.impl.TemperatureDeviceModel;
import org.devise.wrapper.MqttClientWrapper;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;

/**
 * @author Kseniia Orlenko
 * @version 5/17/18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@TestPropertySource(properties = {
        "temperature.min=0",
        "temperature.max=40",
        "temperature.device.id=TemperatureDevice",
        "temperature.topic=temperature_topic",
})
public class TemperatureDataPublisherTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MqttClientWrapper mqttClientWrapper;
    @Autowired
    private TemperatureDataGenerator temperatureDataGenerator;
    @Autowired
    private TemperatureDataPublisher temperatureDataPublisher;

    @Test
    public void publishPositiveFlow() throws MqttException, JsonProcessingException {
        resetMocks();
        TemperatureDeviceModel model = createModel();
        when(temperatureDataGenerator.generate()).thenReturn(model);
        when(objectMapper.writeValueAsBytes(model)).thenReturn(new byte[] {});

        temperatureDataPublisher.publish();

        verify(temperatureDataGenerator, times(1)).generate();
        verify(objectMapper, times(1)).writeValueAsBytes(model);
        verify(mqttClientWrapper, times(1)).publish(any(String.class), any(byte[].class));
    }

    @Test(expected = JsonProcessingException.class)
    public void publishJsonException() throws MqttException, JsonProcessingException {
        resetMocks();
        when(objectMapper.writeValueAsBytes(null)).thenThrow(new JsonEOFException(null, null, null));

        temperatureDataPublisher.publish();
    }

    @Test(expected = MqttException.class)
    public void publishMqttException() throws MqttException, JsonProcessingException {
        resetMocks();
        doThrow(new MqttException(null)).when(mqttClientWrapper).publish("temperature_topic", null);

        temperatureDataPublisher.publish();
    }

    private void resetMocks() {
        reset(objectMapper);
        reset(mqttClientWrapper);
        reset(temperatureDataGenerator);
    }

    private TemperatureDeviceModel createModel() {
        TemperatureDeviceModel model = new TemperatureDeviceModel();
        model.setId("id");
        model.setTemperature(35);
        model.setTimestamp(System.currentTimeMillis() );
        return model;
    }

    @Configuration
    static class Context {

        @Bean
        public ObjectMapper objectMapper() {
            return mock(ObjectMapper.class);
        }

        @Bean
        public MqttClientWrapper mqttClientWrapper() {
            return mock(MqttClientWrapper.class);
        }

        @Bean
        public TemperatureDataGenerator temperatureDataGenerator() {
            return mock(TemperatureDataGenerator.class);
        }

        @Bean
        public TemperatureDataPublisher temperatureDataPublisher(ObjectMapper objectMapper,
                                                            MqttClientWrapper mqttClientWrapper,
                                                            TemperatureDataGenerator temperatureDataGenerator) {
            return new TemperatureDataPublisher(objectMapper, mqttClientWrapper, temperatureDataGenerator);
        }
    }
}