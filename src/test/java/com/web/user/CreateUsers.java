package com.web.user;

import com.tp.pojo.pick3.PickPjo;
import com.web.pojo.users.UsersPjo;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.PrintStream;
import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CreateUsers {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    String registeredToken;
    String password;
    UsersPjo usersPjo = new UsersPjo();
    UsersPjo desUsers = new UsersPjo();

    @BeforeClass
    public void beforeClass(){

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        requestSpecBuilder.setBaseUri("http://172.16.3.33:5006/user-api/v1");
        responseSpecBuilder.expectStatusCode(200);
        requestSpecification = requestSpecBuilder.build();
        responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void checkUserServiceStatus(){

        // this test case is to check the service status of the user API
        UsersPjo statusResponse = given().spec(requestSpecification).when().get("/status").then().spec(responseSpecification).extract().response().as(UsersPjo.class);
        assertThat(statusResponse.getMessage(), equalTo("statusAlive"));

    }

    @Test
    public void createWebUser(){

        //SessionFilter session = new SessionFilter();

        // this test case is to create a new user from web.
        // Pojo class used to serialize the payload body
        usersPjo = new UsersPjo("test1","test1","test@example.com","96170808080","mtn","123456789",
                "123456789","2000-01-09T20:10:10.709Z","beirut","lebanon","male","Mr","test2","lebanese",
                "123456","2024-01-09T20:10:10.709Z","Rl 123456",0,"154875");

        // deserialize the json response and convert it to Pojo class
        desUsers = given().spec(requestSpecification).body(usersPjo).contentType(ContentType.JSON).log().all()
            .when().post("/users")
                .then().spec(responseSpecification).log().all().extract().response().as(UsersPjo.class);

        // assertion to ensure that the user created  successfully and to get the register token to use later on confirm registration
        assertThat(desUsers.getMessage(), matchesPattern("userRegisteredSuccessfully"));
        //just create variables to save the token and password
        registeredToken = desUsers.getToken();
        password = usersPjo.getPassword();
        System.out.println("My Token Value is: " + registeredToken);
        System.out.println("My Password Value is: " + password);
    }

    @Test
    public void loginUser(){

        // for now, I am using hashmap class to serialize the payload, but later I will try to use the same pjo class created above

        HashMap<String, String> userBody = new HashMap<>();

        userBody.put("password","P@ssw0rd");
        userBody.put("type","web");

           UsersPjo loginPjo = given().spec(requestSpecification).contentType( "application/problem+json; charset=utf-8").
                        body(userBody).log().all().when().post("/usernames/mdelbani").
                   then().spec(responseSpecification).log().all().extract().response().as(UsersPjo.class);

//                assertThat(loginPjo.getMessage(), equalTo("sucessfulLogin"));
//
//                String loginToken = loginPjo.getToken();
//                System.out.println(loginToken);
    }

//    @Test
//    public void getUserInformation(){
//
//        given().spec(requestSpecification).when().get("/tokens/{registeredToken}").then().statusCode(200).log().all();
//    }
}
