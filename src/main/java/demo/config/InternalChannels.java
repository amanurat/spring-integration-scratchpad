package demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.messaging.MessageChannel;

/**
 * Created by BWilcock on 20/04/2016.
 */
@Configuration
public class InternalChannels {

    @Bean()
    @Description("Used internally to process messages")
    public MessageChannel processingChannel() {
        return MessageChannels.direct().interceptor().get();
    }

    @Bean()
    @Description("Used to send messages from the component to the outside world")
    public MessageChannel outboundChannel() {
        return MessageChannels.direct().get();
    }

//    @ServiceActivator(inputChannel = "logging")
//    @Bean
//    public LoggingHandler loggingHandler() {
//        return new LoggingHandler("INFO");
//    }

}
