package demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.amqp.Amqp;
import org.springframework.integration.dsl.support.Transformers;
import org.springframework.messaging.MessageChannel;

@Configuration
public class InboundMessageAdapter {

    @Autowired
    public ConnectionFactory connectionFactory;

    @Autowired
    public MessageChannel processingChannel;


    @Value("${spring.application.inbound.exchange}")
    private String inboundExchangeName;

    @Value("${spring.application.inbound.queue}")
    private String inboundQueueName;

    @Value("${spring.application.routing.key}")
    private String routingKey;

    @Bean
    public DirectExchange inboundExchange() {
        return new DirectExchange(inboundExchangeName, true, false);
    }

    @Bean
    public Queue inboundQueue() {
        return new Queue(inboundQueueName, true, false, false);
    }

    @Bean
    public Binding inboundBinding(DirectExchange inboundExchange, Queue inboundQueue) {
        return BindingBuilder.bind(inboundQueue).to(inboundExchange).with(routingKey);
    }

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer(Queue inboundQueue) {
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory);
        listenerContainer.setQueues(inboundQueue);
        return listenerContainer;
    }

    @Bean
    public IntegrationFlow inboundFromRabbitMqFlow(SimpleMessageListenerContainer simpleMessageListenerContainer) {
        return IntegrationFlows.from(Amqp.inboundAdapter(simpleMessageListenerContainer))
                .transform(Transformers.objectToString())
                .channel(processingChannel)
                .get();
    }
}
