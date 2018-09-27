package com.rabbitmq.producer.todo.api;

import com.rabbitmq.producer.todo.api.dto.TodoDto;
import com.rabbitmq.producer.todo.model.TodoModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.camel.ProducerTemplate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TodoApi implements BaseVersion {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProducerTemplate producerTemplate;

    @PostMapping("/todo")
    @ApiOperation(
            value = "Create a todo",
            response = TodoDto.class,
            notes = "This operation insert a todo in a queue"
    )
    @ApiResponses( value = {
            @ApiResponse(
                    code = 200,
                    message = "Success to insert todo in queue",
                    response = TodoDto.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "When have some expected error in data",
                    response = Error.class
            ),
            @ApiResponse(
                    code = 500,
                    message = "When have some unexpected error",
                    response = Error.class
            )
    })
    public ResponseEntity<TodoDto> createTodo(@Valid @RequestBody TodoDto todoDto){
        producerTemplate.sendBody("direct:createTodo", convertTodoDtoToTodoModel(todoDto));
        return ok(todoDto);
    }

    private TodoModel convertTodoDtoToTodoModel(TodoDto todoDto){
        return modelMapper.map(todoDto, TodoModel.class);
    }

    private <T> ResponseEntity<T> ok(T body) {
        return ResponseEntity.ok(body);
    }
}
