package com.tqs.lab6_3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import  static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class RestAssuredTest {
    @Test
    @DisplayName("ToDos Endpoint")
    public void testToDos() {
        given().when().get("https://jsonplaceholder.typicode.com/todos").then().statusCode(200);
    }

}