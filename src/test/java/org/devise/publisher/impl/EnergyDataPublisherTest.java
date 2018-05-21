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
import org.devise.gererator.impl.EnergyDataGenerator;
import org.devise.model.impl.EnergyDeviceModel;
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
        "energy.topic=energy_topic",
})
public class EnergyDataPublisherTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MqttClientWrapper mqttClientWrapper;
    @Autowired
    private EnergyDataGenerator energyDataGenerator;
    @Autowired
    private EnergyDataPublisher energyDataPublisher;

    @Test
    public void publishPositiveFlow() throws MqttException, JsonProcessingException {
        resetMocks();
        EnergyDeviceModel model = createModel();
        when(energyDataGenerator.generate()).thenReturn(model);
        when(objectMapper.writeValueAsBytes(model)).thenReturn(new byte[] {});

        energyDataPublisher.publish();

        verify(energyDataGenerator, times(1)).generate();
        verify(objectMapper, times(1)).writeValueAsBytes(model);
        verify(mqttClientWrapper, times(1)).publish(any(String.class), any(byte[].class));
    }

    @Test(expected = JsonProcessingException.class)
    public void publishJsonException() throws MqttException, JsonProcessingException {
        resetMocks();
        when(objectMapper.writeValueAsBytes(null)).thenThrow(new JsonEOFException(null, null, null));

        energyDataPublisher.publish();
    }

    @Test(expected = MqttException.class)
    public void publishMqttException() throws MqttException, JsonProcessingException {
        resetMocks();
        doThrow(new MqttException(null)).when(mqttClientWrapper).publish("energy_topic", null);

        energyDataPublisher.publish();
    }

    private void resetMocks() {
        reset(objectMapper);
        reset(mqttClientWrapper);
        reset(energyDataGenerator);
    }

    private EnergyDeviceModel createModel() {
        EnergyDeviceModel model = new EnergyDeviceModel();
        model.setId("id");
        model.setEnergy(11);
        model.setTimestamp(System.currentTimeMillis());
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
        public EnergyDataGenerator energyDataGenerator() {
            return mock(EnergyDataGenerator.class);
        }

        @Bean
        public EnergyDataPublisher energyDataPublisher(ObjectMapper objectMapper,
                                                       MqttClientWrapper mqttClientWrapper,
                                                       EnergyDataGenerator energyDataGenerator) {
            return new EnergyDataPublisher(objectMapper, mqttClientWrapper, energyDataGenerator);
        }
    }
}