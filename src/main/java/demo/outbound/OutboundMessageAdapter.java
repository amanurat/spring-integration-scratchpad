package demo.outbound;

import demo.config.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.amqp.Amqp;

@Configuration
public class OutboundMessageAdapter {
    private static final Logger logger = LoggerFactory.getLogger(OutboundMessageAdapter.class);

    @Autowired
    private RabbitConfig rabbitConfig;

    @Autowired
    private DirectChannel outboundChannel;

    @Bean
    public IntegrationFlow outboundToRabbitFlow() {
        return IntegrationFlows.from(outboundChannel)
                .handle(Amqp.outboundAdapter(rabbitConfig.outboundTemplate()))
                .get();
    }
}
