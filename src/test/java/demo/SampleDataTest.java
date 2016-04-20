package demo;

import demo.config.DemoOutboundMessageGateway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class, initializers = ConfigFileApplicationContextInitializer.class)
public class SampleDataTest {

    @Autowired
    private DemoOutboundMessageGateway demoOutboundMessageGateway;

    @Test
    public void sendTestData() throws Exception {
            demoOutboundMessageGateway.sendMessage(UUID.randomUUID().toString());
    }

    @Configuration
    @EnableAutoConfiguration
    @ComponentScan("config")
    public static class TestConfig {

    }
}
