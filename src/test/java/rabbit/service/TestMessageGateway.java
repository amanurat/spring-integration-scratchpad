package rabbit.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface TestMessageGateway {
    @Gateway(requestChannel = "launch.message.input.channel")
    void sendMessage(String message);
}
