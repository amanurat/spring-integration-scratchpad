package demo.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;

/**
 * Created by BWilcock on 20/04/2016.
 */
@Configuration
public class MessageProcessor {

    @Autowired
    public MessageChannel processingChannel;

    @Autowired
    public MessageChannel outboundChannel;

    @Bean
    public IntegrationFlow processingFlow() {
        return IntegrationFlows.from(processingChannel)
                .transform(BusinessLogic::doSomeWork)
                .channel(outboundChannel)
                .get();
    }
}
