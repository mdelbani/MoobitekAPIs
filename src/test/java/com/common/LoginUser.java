package com.common;

import MySQLDatabaseConnection.MySQLConnection;
import UserPojoClasses.FetchUser.GetUserInfoPjo;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
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
    public String loginToken;
    public String userName;
    BaseURI baseURI = new BaseURI();
    String BaseURI = baseURI.setBaseURI();

    public void beforeClass() {
        //this the first executable class, it contains the basic information used for all APIs
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        requestSpecBuilder.setBaseUri("http://"+BaseURI+":5006/user-api/v1");
        requestSpecBuilder.setContentType("application/problem+json; charset=utf-8");
        responseSpecBuilder.expectStatusCode(200);
        requestSpecification = requestSpecBuilder.build();
        responseSpecification = responseSpecBuilder.build();
    }

    public String loginUser() throws JsonProcessingException, SQLException {

        MySQLConnection mySQLConnection = new MySQLConnection();
        //userPassword = mySQLConnection.getUserPassword();
        userName = mySQLConnection.getUserName();
        // here I am using hashmap class to serialize the payload
        HashMap<String, String> userBody = new HashMap<>();
        userBody.put("password","mdelbani@82");
        userBody.put("type", "web");

        // parsed the response body to a response variable
        GetUserInfoPjo  loginResponse = given().spec(requestSpecification).
                body(userBody).when().post("/usernames/"+userName).
                then().spec(responseSpecification).extract().response().as(GetUserInfoPjo.class);

        String expectedResponseMessage = loginResponse.getMessage();
        loginToken = loginResponse.getToken();
        assertThat(expectedResponseMessage, equalTo("sucessfulLogin"));
        System.out.println("You have logged in successfully to your account");
        System.out.println(loginToken);
        return loginToken;
    }

    public static void main(String[] args) throws SQLException, JsonProcessingException {

        LoginUser loginUser = new LoginUser();
        loginUser.beforeClass();
        loginUser.loginUser();
    }
}
