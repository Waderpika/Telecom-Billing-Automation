package com.ayush.tests;

import com.ayush.framework.base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * ApiTest — REST API tests using Rest Assured against reqres.in (public mock API).
 *
 * Demonstrates:
 *  - GET request validation (status code, response body)
 *  - POST request with payload and response assertion
 *  - Schema validation using Hamcrest matchers
 *  - Chained fluent Rest Assured syntax
 */
public class ApiTest extends BaseTest {

    @BeforeClass
    public void setUpApi() {
        RestAssured.baseURI = config.getApiBaseUrl();
    }

    @Test(description = "GET /users should return 200 with user list")
    public void testGetUsers() {
        given()
            .queryParam("page", 1)
        .when()
            .get("/users")
        .then()
            .statusCode(200)
            .body("page", equalTo(1))
            .body("data", notNullValue())
            .body("data.size()", greaterThan(0))
            .body("data[0].id", notNullValue())
            .body("data[0].email", containsString("@"));
    }

    @Test(description = "GET /users/{id} should return correct user details")
    public void testGetSingleUser() {
        given()
        .when()
            .get("/users/2")
        .then()
            .statusCode(200)
            .body("data.id", equalTo(2))
            .body("data.email", notNullValue())
            .body("data.first_name", notNullValue());
    }

    @Test(description = "GET /users/{id} for non-existent user should return 404")
    public void testGetNonExistentUser() {
        given()
        .when()
            .get("/users/9999")
        .then()
            .statusCode(404);
    }

    @Test(description = "POST /users should create a new user and return 201")
    public void testCreateUser() {
        String requestBody = """
            {
                "name": "Ayush Kumar",
                "job": "SDET"
            }
            """;

        Response response = given()
            .header("Content-Type", "application/json")
            .body(requestBody)
        .when()
            .post("/users")
        .then()
            .statusCode(201)
            .body("name", equalTo("Ayush Kumar"))
            .body("job", equalTo("SDET"))
            .body("id", notNullValue())
            .body("createdAt", notNullValue())
            .extract().response();

        // Additional assertion using Response object
        String userId = response.jsonPath().getString("id");
        Assert.assertNotNull(userId, "Created user should have an ID");
        System.out.println("Created user with ID: " + userId);
    }

    @Test(description = "PUT /users should update user and return 200")
    public void testUpdateUser() {
        String requestBody = """
            {
                "name": "Ayush Kumar",
                "job": "Senior SDET"
            }
            """;

        given()
            .header("Content-Type", "application/json")
            .body(requestBody)
        .when()
            .put("/users/2")
        .then()
            .statusCode(200)
            .body("job", equalTo("Senior SDET"))
            .body("updatedAt", notNullValue());
    }
}
