package demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.amqp.Amqp;
import org.springframework.messaging.MessageChannel;

@Configuration
public class OutboundMessageAdapter {

    @Autowired
    public ConnectionFactory connectionFactory;

    @Autowired
    public MessageChannel outboundChannel;

    @Value("${spring.application.outbound.exchange}")
    private String outboundExchangeName;

    @Value("${spring.application.outbound.queue}")
    private String outboundQueueName;

    @Value("${spring.application.routing.key}")
    private String routingKey;

    @Bean
    public DirectExchange outboundExchange() {
        return new DirectExchange(outboundExchangeName, true, false);
    }

    @Bean
    public Queue outboundQueue() {
        return new Queue(outboundQueueName, true, false, false);
    }

    @Bean
    public Binding outboundBinding(DirectExchange outboundExchange, Queue outboundQueue) {
        return BindingBuilder.bind(outboundQueue).to(outboundExchange).with(routingKey);
    }

    @Bean
    @Primary
    public RabbitTemplate outboundTemplate() {
        RabbitTemplate r = new RabbitTemplate(connectionFactory);
        r.setExchange(outboundExchange().getName());
        r.setChannelTransacted(true);
        r.setRoutingKey(routingKey);
        return r;
    }

    @Bean
    public IntegrationFlow outboundToRabbitFlow(RabbitTemplate outboundTemplate) {
        return IntegrationFlows.from(outboundChannel)
                .handle(Amqp.outboundAdapter(outboundTemplate))
                .get();
    }
}
