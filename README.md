# Spring Integration Microservice - Java DSL Demo

This code demonstrates the use of Spring Boot, Spring Integration's Java DSL and RabbitMQ in order to create a simple message flow. I'm no Spring expert, but I managed to cobble this together in a day or so. I've probably made a few mistakes, so do call them out so I can correct them.

The Architecture looks like this:

![Architecture](https://github.com/benwilcock/spring-integration-scratchpad/blob/master/slides/Slide03.png "Architecture")

#Running the Demo

Assuming that you have the Java SDK 1.8 and RabbitMQ 3.6.x installed on your machine, you can start the demo from the command line by issuing the command:-

```bash
./gradlew bootRun
```

This will create two sets of exchanges and queue's in RabbitMQ which can be used to send messages into the demo (using the routing key 'demo.key'). To test the microservice, open [Rabbit's management console](http://localhost:15672/) and find the "demo.inbound.exchange". Publish a message on the exchange (any string you like) using the 'demo.key' as your routing key.

If you typed `Hello!` you should see a log entry in the spring boot terminal window telling you that the message was processed and became `**** (0) HELLO! ****`. Similarly, in the [RabbitMQ console](http://localhost:15672/), if you navigate to the 'demo.outbound.queue' you should see that there is a message waiting for you with the same processed content.

```bash
INFO 14000 --- [cTaskExecutor-1] demo.logic.BusinessLogic: Processing message 0: 'Hello!'
INFO 14000 --- [cTaskExecutor-1] demo.logic.BusinessLogic: Processed message is: **** (0) HELLO! ****
```

### REST API

The demo also allows you to utilise the same message processing feature via a RESTful service interface. To use it just point your browser at [http://localhost:9091/demo?message=Hello!](http://localhost:9091/demo?message=Hello!) and you'll see the response `**** (0) HELLO! ****` in your browser window.


# Code Walkthrough

The code used by the Java DSL to configure spring integration is fairly compact, and where appropriate you may like specify behaviours within the configuration using Lambda expressions. This can help to reduce the proliferation of implementation classes within your codebase as you add or leverage spring integration's various features.

As an example of what the code looks like, the sample code below shows a message being received by our `MessageProcessor` on the `processingChannel` which is then routed to some business logic which processes the message before preparing it for it's onward journey via the `outboundChannel`.

```java
@Configuration
public class MessageProcessor {

    @Autowired
    public MessageChannel processingChannel;

    @Autowired
    public MessageChannel outboundChannel;

    @Bean
    public IntegrationFlow processingFlow() {
        return IntegrationFlows.from(processingChannel)
                .transform(BusinessLogic::doSomeWork)
                .channel(outboundChannel)
                .get();
    }
}
```

The whole flow, and the code it's comprised of looks like this:

![Code](https://github.com/benwilcock/spring-integration-scratchpad/blob/master/slides/Slide04.png "Code")

I've purposefully separated the concern for inbound message handling, outbound message handling and code logic from each other and though you could merge them together if you so wish.

# Additional Reading
The Spring-Integration Java DSL is documented here: [https://github.com/spring-projects/spring-integration-java-dsl/wiki/spring-integration-java-dsl-reference](https://github.com/spring-projects/spring-integration-java-dsl/wiki/spring-integration-java-dsl-reference).