Feature: Create registries of todo's in a queue

  Scenario: A create of a todo in a queue occurred with success
    Given the todo name MyFirstTodo
    And the description My todo to do something
    And the priority 3
    And the author Aragorn
    When create a todo
    Then should return a status code 200
    And should send message to queue

  Scenario: A create of a todo in a queue occurred with error because name not was informed
    Given the todo without name
    And the description My todo to do something
    And the priority 3
    And the author Aragorn
    When create a todo
    Then should return a status code 400
    And should not send message to queue

  Scenario: A create of a todo in a queue occurred with error because description not was informed
    Given the todo name MyFirstTodo
    And without description
    And the priority 3
    And the author Aragorn
    When create a todo
    Then should return a status code 400
    And should not send message to queue

  Scenario: A create of a todo in a queue occurred with error because priority not was informed
    Given the todo name MyFirstTodo
    And the description My todo to do something
    And without priority
    And the author Aragorn
    When create a todo
    Then should return a status code 400
    And should not send message to queue

  Scenario: A create of a todo in a queue occurred with error because priority is incorrect
    Given the todo name MyFirstTodo
    And the description My todo to do something
    And the priority 0
    And the author Aragorn
    When create a todo
    Then should return a status code 400
    And should not send message to queue

  Scenario: A create of a todo in a queue occurred with error because priority is incorrect
    Given the todo name MyFirstTodo
    And the description My todo to do something
    And the priority 6
    And the author Aragorn
    When create a todo
    Then should return a status code 400
    And should not send message to queue

  Scenario: A create of a todo in a queue occurred with error because priority is incorrect
    Given the todo name MyFirstTodo
    And the description My todo to do something
    And the priority 4
    And without author
    When create a todo
    Then should return a status code 400
    And should not send message to queue