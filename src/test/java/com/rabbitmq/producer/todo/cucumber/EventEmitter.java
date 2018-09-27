package com.rabbitmq.producer.todo.cucumber;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

@Component
public class EventEmitter {

    @Autowired
    private RabbitConnection rabbitConnection;
    private Channel channel;

    public String on(String queue, final Consumer<Object> callback, Map<String, Object> args) throws IOException, TimeoutException {
        this.getChannel().queueDeclare(queue, true, false, false, args);
        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(this.getChannel()) {
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                callback.accept(message);
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        return this.getChannel().basicConsume(queue, true, consumer);
    }

    private Channel getChannel() throws IOException {
        if(channel == null || !channel.isOpen()) {
            this.channel = rabbitConnection.getConnection().createChannel();
        }
        return channel;
    }
}
