# spring-integration-scratchpad

Requires a default istallation of rabbit mq.

You can start the demo from the command line in the root folder using the command:-

```bash
./gradlew bootRun
```

This will create two sets of exchanges and queue's in RabbitMQ which can be used to send messages into the demo (using the routing key 'demo.key'').

