package com.rabbitmq.producer.todo.route;

import com.rabbitmq.producer.todo.config.RabbitConfig;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class TodoRoute extends RouteBuilder {

    @Autowired
    private RabbitConfig rabbitConfig;

    @Override
    public void configure() throws Exception {
        onException(Exception.class)
                .asyncDelayedRedelivery()
                .redeliveryDelay(2000)
                .maximumRedeliveries(2)
                .useOriginalMessage()
                .log(LoggingLevel.ERROR,"Exception: Sending message to dead letter. TodoModel ${body}")
                .logRetryAttempted(true);

        from("direct:createTodo")
                .routeId("createTodo")
                .log(LoggingLevel.INFO, "Initializing integration from todo to queue")
                .process((exchange) -> {
                    System.out.println("");
                })
                .marshal()
                .json(JsonLibrary.Jackson)
                .to(buildUriRabbit())
        .end();
    }

    private String buildUriRabbit(){
        return UriComponentsBuilder
                .newInstance()
                .scheme("rabbitmq")
                .host(rabbitConfig.getRabbitUrl() + "/" + rabbitConfig.getRabbitExchange())
                .queryParam("queue", rabbitConfig.getRabbitQueue())
                .queryParam("exchangeType", "direct")
                .queryParam("username", rabbitConfig.getRabbitUser())
                .queryParam("password", rabbitConfig.getRabbitPass())
                .queryParam("autoDelete", false)
                .queryParam("autoAck", false)
                .queryParam("deadLetterExchangeType", "fanout")
                .queryParam("deadLetterExchange", "todo.dead")
                .queryParam("deadLetterQueue", "dead.letter")
                .queryParam("deadLetterRoutingKey", "#")
                .build()
                .toString();
    }
}
