package rabbit.config.outbound;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface OnwardMessageGateway {
    @Gateway(requestChannel = "message.send.channel")
    void sendMessage(String message);
}
