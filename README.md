# spring-integration-scratchpad

Requires a default istallation of demo mq.

You can start the demo from the command line in the root folder using the command:-

```bash
./gradlew bootRun
```

This will create two sets of exchanges and queue's in RabbitMQ which can be used to send messages into the demo (using the routing key 'demo.key'').

Here I'm using the Spring-Integration Java DSL which is documented [here](https://github.com/spring-projects/spring-integration-java-dsl/wiki/spring-integration-java-dsl-reference).

Using this DSL and integration flow may be defined using configuration similar to this:-

```java
@Configuration
@EnableIntegration
public class MyConfiguration {
    @Bean
    public MessageSource<?> integerMessageSource() {
        MethodInvokingMessageSource source = new MethodInvokingMessageSource();
        source.setObject(new AtomicInteger());
        source.setMethodName("getAndIncrement");
        return source;
    }
    @Bean
    public DirectChannel inputChannel() {
        return new DirectChannel();
    }
    @Bean
    public IntegrationFlow myFlow() {
        return IntegrationFlows.from(this.integerMessageSource(), c ->
                                                   c.poller(Pollers.fixedRate(100)))
                    .channel(this.inputChannel())
                    .filter((Integer p) -> p > 0)
                    .transform(Object::toString)
                    .channel(MessageChannels.queue())
                    .get();
    }
}
```

In this example a message source is polled regularly for a new message, when one arrives it is filtered, transformed and sent onwards using several convenience methods. If appropriate, you may also specify behaviours in the configuration using Lambdas. This will reduce the proliferation of classes within your codebase as you add or leverage features.