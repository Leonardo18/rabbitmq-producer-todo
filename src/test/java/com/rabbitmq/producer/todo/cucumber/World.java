package com.rabbitmq.producer.todo.cucumber;

import com.google.common.collect.Maps;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.producer.todo.api.dto.TodoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;

@Component
@Scope("cucumber-glue")
public class World {
    public Integer status;
    public String errorMessage;
    public Map<String, Object> map = Maps.newHashMap();
    public List<TodoDto> todoDtoList;
    public CountDownLatch countDownLatch;
}