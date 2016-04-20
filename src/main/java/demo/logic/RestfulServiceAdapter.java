package demo.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.http.Http;
import org.springframework.messaging.MessageChannel;

import java.util.List;

/**
 * Created by ben on 07/04/16.
 */
@SpringBootApplication
@IntegrationComponentScan
public class RestfulServiceAdapter {

    @Autowired
    public MessageChannel processingChannel;

    @Bean
    public IntegrationFlow restServiceProxyFlow() {
        return IntegrationFlows
                .from(Http.inboundGateway("/demo")
                        .requestMapping(r -> r.params("message"))
                        .payloadExpression("#requestParams.message"))
                .<List<String>, String>transform(p -> BusinessLogic.doSomeWork(p.get(0)))
                .get();
    }
}
