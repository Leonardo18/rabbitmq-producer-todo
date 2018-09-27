package com.rabbitmq.producer.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.rabbitmq.producer.todo")
public class RabbitmqProducerTodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqProducerTodoApplication.class, args);
	}
}
