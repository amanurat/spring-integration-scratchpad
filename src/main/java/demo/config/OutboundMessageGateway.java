package demo.config;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface OutboundMessageGateway {
    @Gateway(requestChannel = "outboundChannel")
    void sendMessage(String message);
}
