package com.tqs.lab6_3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

// Given is only needed if we want to pass parameters to the request

public class RestAssuredTest {
    @Test
    @DisplayName("ToDos Endpoint")
    public void testToDos() {
        when().get("https://jsonplaceholder.typicode.com/todos/").then().statusCode(200);
    }

    @Test
    @DisplayName("ToDo #4")
    public void testToDo4() {
        when().get("https://jsonplaceholder.typicode.com/todos/{id}", 4).then().statusCode(200)
                .body("title", org.hamcrest.Matchers.equalTo("et porro tempora"));
    }

    @Test
    @DisplayName("ToDo check id #198 and #199 when listing all")
    public void testToDo198and199() {
        when().get("https://jsonplaceholder.typicode.com/todos/").then().statusCode(200)
                .body("id[197]", org.hamcrest.Matchers.equalTo(198))
                .body("id[198]", org.hamcrest.Matchers.equalTo(199));
    }

    @Test
    @DisplayName("Listing all ToDos should take less than 2 seconds")
    public void testToDoListTime() {
        when().get("https://jsonplaceholder.typicode.com/todos/").then().statusCode(200)
                .time(org.hamcrest.Matchers.lessThan(2000L));
    }
}