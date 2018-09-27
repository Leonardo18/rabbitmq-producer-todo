package com.rabbitmq.producer.todo.cucumber.stepdefs;

import com.rabbitmq.producer.todo.TestConfig;
import com.rabbitmq.producer.todo.api.dto.TodoDto;
import com.rabbitmq.producer.todo.config.RabbitConfig;
import com.rabbitmq.producer.todo.cucumber.EventEmitter;
import com.rabbitmq.producer.todo.cucumber.World;
import cucumber.api.java8.En;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class TodoStepdefs  extends TestConfig implements En {

    @Autowired
    private RabbitConfig rabbitConfig;
    @Autowired
    private EventEmitter eventEmitter;
    @Autowired
    private World world;
    @LocalServerPort
    private Integer port;
    private RestTemplate restTemplate;

    public TodoStepdefs () {

        Before(() -> {
            restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        });

        Given("^the todo name (.*)$", (String name) -> {
            world.map.put("name", name);
        });

        Given("^the todo without name$", () -> { });

        Given("^the description (.*)$", (String description) -> {
            world.map.put("description", description);
        });

        Given("^without description$", () -> { });

        Given("^the priority (\\d+)$", (Integer priority) -> {
            world.map.put("priority", priority);
        });

        Given("^without priority$", () -> { });

        Given("^the author (.*)$", (String author) -> {
            world.map.put("author", author);
        });

        Given("^without author", () -> { });

        When("^create a todo$", () -> {
            createListener();
            world.status = 200;
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<TodoDto> entity = new HttpEntity<>(buildTodoDto(), headers);

            try {
                restTemplate.exchange(String.format("http://localhost:%s/api/v1/todo", port),
                        HttpMethod.POST,
                        entity,
                        TodoDto.class)
                        .getBody();
            } catch (HttpServerErrorException | HttpClientErrorException e) {
                world.status = e.getRawStatusCode();
                world.errorMessage = e.getMessage();
            }
        });

        Then("^should return a status code (\\d+)$", (Integer expectedStatus) -> {
            assertEquals(expectedStatus, world.status);
        });

        Then("^should send message to queue$", () ->{
            world.countDownLatch.await(5L, TimeUnit.SECONDS);
            assertEquals(0, world.countDownLatch.getCount());
        });

        Then("^should not send message to queue$", () ->{
            world.countDownLatch.await(5L, TimeUnit.SECONDS);
            assertEquals(1, world.countDownLatch.getCount());
        });
    }

    private TodoDto buildTodoDto(){
        return new TodoDto(
                (String) world.map.get("name"),
                (String) world.map.get("description"),
                (Integer) world.map.get("priority"),
                (String) world.map.get("author"));
    }

    private void createListener() throws Exception {
        world.countDownLatch = new CountDownLatch(1);
        Map<String, Object> args = new HashedMap();
        args.put("x-dead-letter-exchange", "todo.dead");
        args.put("x-dead-letter-routing-key", "#");
        eventEmitter.on(rabbitConfig.getRabbitQueue(), (message) ->{
            world.countDownLatch.countDown();}, args);
    }

}
