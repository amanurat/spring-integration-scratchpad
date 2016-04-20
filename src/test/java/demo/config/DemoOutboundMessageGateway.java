package demo.config;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface DemoOutboundMessageGateway {
    @Gateway(requestChannel = "launch.message.channel")
    void sendMessage(String message);
}
