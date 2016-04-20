package demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.amqp.Amqp;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.messaging.MessageChannel;

@Configuration
@EnableIntegration
@IntegrationComponentScan(basePackageClasses = DemoOutboundMessageGateway.class)
public class DemoOutboundMessageAdapter {
    private static final Logger logger = LoggerFactory.getLogger(DemoOutboundMessageAdapter.class);

    @Autowired
    private RabbitTemplate inboundTemplate;

    @Bean(name = "launch.message.channel")
    public MessageChannel messageInputChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public IntegrationFlow sendTestMessageFlow() {
        return IntegrationFlows.from(messageInputChannel())
                .handle(Amqp.outboundAdapter(inboundTemplate))
                .get();
    }
}
