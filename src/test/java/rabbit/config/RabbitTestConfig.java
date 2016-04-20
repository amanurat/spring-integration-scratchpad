package rabbit.config;

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

//@Configuration
public class RabbitTestConfig {
/*
    @Autowired
    private ConnectionFactory rabbitConnectionFactory;

    @Autowired
    private RabbitConfig rabbitConfig;

    @Value("${spring.application.inbound.exchange}")
    private String exchangeName;

    @Value("${spring.application.routing.key}")
    private String routingKey;

    @Bean
    DirectExchange sampleExchange() {
        return new DirectExchange(exchangeName, true, false);
    }


    @Bean
    Binding sampleBinding(DirectExchange inboundExchange, Queue inboundQueue) {
        return BindingBuilder.bind(inboundQueue).to(inboundExchange).with(routingKey);
    }

    @Bean
    @Primary
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate r = new RabbitTemplate(rabbitConnectionFactory);
        r.setExchange(sampleExchange().getName());
        r.setChannelTransacted(true);
        r.setRoutingKey(routingKey);
        return r;
    }
    */
}
