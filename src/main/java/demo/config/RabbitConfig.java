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

@Configuration
public class RabbitConfig {

    @Autowired
    private ConnectionFactory rabbitConnectionFactory;

    @Value("${spring.application.routing.key}")
    private String routingKey;

    @Value("${spring.application.inbound.queue}")
    private String inboundQueueName;

    @Value("${spring.application.inbound.exchange}")
    private String inboundExchangeName;

    @Value("${spring.application.outbound.queue}")
    private String outboundQueueName;

    @Value("${spring.application.outbound.exchange}")
    private String outboundExchangeName;

    @Bean
    public Queue inboundQueue() {
        return new Queue(inboundQueueName, true, false, false);
    }

    @Bean
    public Queue outboundQueue() {
        return new Queue(outboundQueueName, true, false, false);
    }

    @Bean
    DirectExchange inboundExchange() {
        return new DirectExchange(inboundExchangeName, true, false);
    }

    @Bean
    DirectExchange outboundExchange() {
        return new DirectExchange(outboundExchangeName, true, false);
    }


    @Bean
    Binding inboundBinding(DirectExchange inboundExchange, Queue inboundQueue) {
        return BindingBuilder.bind(inboundQueue).to(inboundExchange).with(routingKey);
    }

    @Bean
    @Primary
    public RabbitTemplate inboundTemplate() {
        RabbitTemplate r = new RabbitTemplate(rabbitConnectionFactory);
        r.setExchange(inboundExchange().getName());
        r.setChannelTransacted(true);
        r.setRoutingKey(routingKey);
        return r;
    }

    @Bean
    Binding outboundBinding(DirectExchange outboundExchange, Queue outboundQueue) {
        return BindingBuilder.bind(outboundQueue).to(outboundExchange).with(routingKey);
    }

    @Bean
    public RabbitTemplate outboundTemplate() {
        RabbitTemplate r = new RabbitTemplate(rabbitConnectionFactory);
        r.setExchange(outboundExchange().getName());
        r.setChannelTransacted(true);
        r.setRoutingKey(routingKey);
        return r;
    }

}
