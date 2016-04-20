package demo.processing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

/**
 * Created by BWilcock on 20/04/2016.
 */
@Configuration
public class MessageProcessingFlow {

    private static final Logger logger = LoggerFactory.getLogger(MessageProcessingFlow.class);

    @Autowired
    private MessageChannel processingChannel;

    @Autowired
    private MessageChannel outboundChannel;

    @Bean
    public IntegrationFlow processingFlow() {
        return IntegrationFlows.from(processingChannel)
                .handle((m) -> {
                    String message = m.getPayload().toString();
                    logger.info("Processing message: '{}'", message);
                    message = CoreBusinessLogic.doSomeWork(message);
                    logger.info("Processed message is now: '{}'", message);
                    outboundChannel.send(MessageBuilder.withPayload(message).build());
                })
                .get();
    }
}
