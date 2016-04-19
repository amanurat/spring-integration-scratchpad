package rabbit.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.amqp.Amqp;
import org.springframework.integration.dsl.support.Transformers;

@Configuration
public class RabbitInboundMessageAdapterFlow {
    private static final Logger logger = LoggerFactory.getLogger(RabbitInboundMessageAdapterFlow.class);

    @Autowired
    private RabbitConfig rabbitConfig;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private MessageGateway messageGateway;

    @Autowired
    private Environment environment;

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory);
        listenerContainer.setQueues(rabbitConfig.inboundQueue());
        //listenerContainer.setConcurrentConsumers(1);
        //listenerContainer.setExclusive(true);
        return listenerContainer;
    }

    @Bean
    public IntegrationFlow inboundFromRabbitFlow() {
        return IntegrationFlows.from(Amqp.inboundAdapter(simpleMessageListenerContainer()))
                .transform(Transformers.objectToString())
                .handle((m) -> {
                    String message = m.getPayload().toString();
                    logger.info("Received  {}", message);
                    logger.info("Processed {}", message);
                    messageGateway.sendMessage(message);
                    logger.info("Sent {}", message);
                })
//                .handleWithAdapter(a ->
//                        a.httpGateway(m ->
//                                String.format("http://localhost:%s/fme?message=%s",
//                                        this.environment.getProperty("local.server.port"), m.getPayload()))
//                                .expectedResponseType(String.class))
                .get();
    }

/*
    @Bean
    public RequestHandlerRetryAdvice retryAdvice() {
        RequestHandlerRetryAdvice retryAdvice = new RequestHandlerRetryAdvice();
        retryAdvice.setRetryTemplate(retryTemplate());
        return retryAdvice;
    }

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(3);
        retryTemplate.setRetryPolicy(retryPolicy);

        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(2000);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        return retryTemplate;
    }

*/

}
