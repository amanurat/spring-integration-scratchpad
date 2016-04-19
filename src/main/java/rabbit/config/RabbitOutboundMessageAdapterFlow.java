package rabbit.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.amqp.Amqp;
import org.springframework.integration.dsl.channel.MessageChannels;

@Configuration
public class RabbitOutboundMessageAdapterFlow {
    private static final Logger logger = LoggerFactory.getLogger(RabbitOutboundMessageAdapterFlow.class);

    @Autowired
    private RabbitConfig rabbitConfig;

    @Bean(name="message.send.channel")
    public DirectChannel messageSendChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public IntegrationFlow outboundToRabbitFlow() {
        return IntegrationFlows.from(messageSendChannel())
                .handle(Amqp.outboundAdapter(this.rabbitConfig.outboundTemplate()))
                .get();
    }
}
