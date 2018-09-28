package com.rabbitmq.producer.todo.cucumber;

import com.google.common.collect.Maps;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

@Component
@Scope("cucumber-glue")
public class World {
    public Integer status;
    public String errorMessage;
    public Map<String, Object> map = Maps.newHashMap();
    public CountDownLatch countDownLatch;
}