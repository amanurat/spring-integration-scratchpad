package rabbit.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.http.Http;
import rabbit.logic.CoreBusinessLogic;

import java.util.List;

/**
 * Created by ben on 07/04/16.
 */
@SpringBootApplication
@IntegrationComponentScan
public class RestServiceAdapterFlow {


    @Bean
    public IntegrationFlow restServiceProxyFlow() {
        return IntegrationFlows
                .from(Http.inboundGateway("/fme")
                        .requestMapping(r -> r.params("message"))
                        .payloadExpression("#requestParams.message"))
                .<List<String>, String>transform(p -> CoreBusinessLogic.doSomeWork(p.get(0)))
                .get();
    }

/*
    @Bean
    public IntegrationFlow publicFmeServiceFlow() {
        return IntegrationFlows
                .from((MessagingGateways g) ->
                        g.httpGateway("/service")
                                .requestMapping(r -> r.params("name"))
                                .payloadFunction(httpEntity ->
                                        ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                                                .getRequest()
                                                .getQueryString()))

                .handleWithAdapter(a ->
                        a.httpGateway(m ->
                                String.format("http://localhost:%s/service/internal?%s", 9090, m.getPayload()))
                                .expectedResponseType(String.class))
                .get();
    }


    @Bean
    public Queue startFlowQueue() {
        return new Queue("LegacyWrapper.start.flow.queue");
    }

    @Bean
    public Queue endFlowQueue() {
        return new Queue("LegacyWrapper.end.flow.queue");
    }
*/
}
