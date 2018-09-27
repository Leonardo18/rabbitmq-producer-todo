# RabbitMq-Producer-Todo
Java microservice with api interface using ***Apache Camel*** and ***RabbitMQ*** to produce messages in a queue

### Usage
* First thing to do is use the command `docker-compose -f docker-compose-local.yml up` in root folder, this command will make ***RabbitMQ*** available
* After run tests to have sure everything is available, if tests pass, can now run application and make API calls to send messages to ***RabbitMQ*** queue

### Acknowledgments
* [RabbitMQ documentation](https://www.rabbitmq.com/api-guide.html)
* [Apache Camel Java DSL](http://camel.apache.org/java-dsl.html)
* [Apache Camel RabbitMQ documentation](http://camel.apache.org/rabbitmq.html)
* [Apache Camel JSON documentation](http://people.apache.org/~dkulp/camel/json.html)


