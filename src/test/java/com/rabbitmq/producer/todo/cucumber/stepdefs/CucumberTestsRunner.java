package com.rabbitmq.producer.todo.cucumber.stepdefs;

import com.rabbitmq.producer.todo.RabbitmqProducerTodoApplication;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", glue = "com.rabbitmq.producer.todo.cucumber")
@ContextConfiguration(loader = SpringBootContextLoader.class, classes = RabbitmqProducerTodoApplication.class)
public class CucumberTestsRunner { }
