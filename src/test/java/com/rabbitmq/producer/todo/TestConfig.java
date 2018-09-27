package com.rabbitmq.producer.todo;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

@TestConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = RabbitmqProducerTodoApplication.class)
@ComponentScan(basePackages = {"com.rabbitmq.producer.todo"})
public class TestConfig { }
