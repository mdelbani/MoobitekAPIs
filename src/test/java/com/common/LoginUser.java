package com.common;

import MySQLDatabaseConnection.MySQLConnection;
import UserPojoClasses.CreateUser.CreateUserPjo;
import UserPojoClasses.FetchUser.GetUserInfoPjo;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LoginUser {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    String loginToken;
    String userName;

    @BeforeClass
    public void beforeClass() {
        //this the first executable class, it contains the basic information used for all APIs
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        requestSpecBuilder.setBaseUri("http://172.16.3.33:5006/user-api/v1");
        responseSpecBuilder.expectStatusCode(200);
        requestSpecification = requestSpecBuilder.build();
        responseSpecification = responseSpecBuilder.build();
    }

    @Test( priority = 1)
    public void checkUserServiceStatus() {

        // this test case is to check the service status of the user API
        CreateUserPjo statusResponse = given().spec(requestSpecification).
                when().get("/status").
                then().spec(responseSpecification).extract().response().as(CreateUserPjo.class);
        assertThat(statusResponse.getMessage(), equalTo("statusAlive"));
        System.out.println("The user service status is: "+statusResponse.getMessage());

    }

    @Test( priority = 2)
    public void loginUser() throws JsonProcessingException, SQLException {

        MySQLConnection mySQLConnection = new MySQLConnection();
        //userPassword = mySQLConnection.getUserPassword();
        userName = mySQLConnection.getUserName();
        // here I am using hashmap class to serialize the payload
        HashMap<String, String> userBody = new HashMap<>();
        userBody.put("password","mdelbani@82");
        userBody.put("type", "web");

        // parsed the response body to a response variable
        GetUserInfoPjo  loginResponse = given().spec(requestSpecification).log().ifValidationFails().contentType("application/problem+json; charset=utf-8").
                body(userBody).when().post("/usernames/"+userName).
                then().spec(responseSpecification).extract().response().as(GetUserInfoPjo.class);

        String expectedResponseMessage = loginResponse.getMessage();
        loginToken = loginResponse.getToken();
        assertThat(expectedResponseMessage, equalTo("sucessfulLogin"));
        System.out.println("You have logged in successfully to your account");
        System.out.println(loginToken);

    }
}
