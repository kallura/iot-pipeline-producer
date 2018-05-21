package org.devise.gererator.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.devise.model.impl.EnergyDeviceModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Kseniia Orlenko
 * @version 5/17/18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@TestPropertySource(properties = {
        "energy.device.id=EnergyDevice",
})
public class EnergyDataGeneratorTest {

    @Autowired
    private EnergyDataGenerator energyDataGenerator;

    @Test
    public void generate() {
        EnergyDeviceModel model = energyDataGenerator.generate();

        assertNotNull("The model should be not null!", model);
        assertEquals("The model id should be set to EnergyDevice", "EnergyDevice", model.getId());
    }

    @Configuration
    static class Context {

        @Bean
        public EnergyDataGenerator energyDataGenerator() {
            return new EnergyDataGenerator();
        }
    }
}