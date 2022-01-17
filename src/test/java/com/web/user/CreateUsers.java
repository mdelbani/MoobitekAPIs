package com.web.user;

import com.db.user.MySQLConnection;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tp.pojo.pick3.PickPjo;
import com.web.pojo.users.CreateUserPjo;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.PrintStream;
import java.sql.*;
import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CreateUsers {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    String registeredToken;
    String userPassword;
    String loginToken;
    String userMobileNumber;
    String userName;
    CreateUserPjo usersPjo = new CreateUserPjo();
    CreateUserPjo desUsers = new CreateUserPjo();
    CreateUserPjo updateInfoPjo = new CreateUserPjo();
    CreateUserPjo confirmToken = new CreateUserPjo();

    @BeforeClass
    public void beforeClass() {

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
        CreateUserPjo statusResponse = given().spec(requestSpecification).when().get("/status").then().spec(responseSpecification).extract().response().as(CreateUserPjo.class);
        assertThat(statusResponse.getMessage(), equalTo("statusAlive"));
        System.out.println("The user service status is: "+statusResponse.getMessage());

    }

    @Test( priority = 2)
    public void createWebUser() {

        // this test case is to create a new user from web.
        // Pojo class used to serialize the payload body
        usersPjo = new CreateUserPjo("Mustapha", "Delbani", "mustapha.delbani@moobitek.com", "96170824488", "mtn", "mdelbani@82",
                "mdelbani@82", "1982-03-02T20:10:10.709Z", "beirut", "lebanon", "male", "Mr", "mdelbani", "lebanese",
                "123456", "2024-01-09T20:10:10.709Z", "Rl 123456", 0, "154875");

        // deserialize the json response and convert it to Pojo class
        desUsers = given().spec(requestSpecification).body(usersPjo).contentType(ContentType.JSON).log().all()
                .when().post("/users")
                .then().spec(responseSpecification).log().ifValidationFails().extract().response().as(CreateUserPjo.class);

        // assertion to ensure that the user created  successfully and to get the register token to use later on confirm registration
        assertThat(desUsers.getMessage(), equalTo("userRegisteredSuccessfully"));
        //just create variables to save the token and password
        registeredToken = desUsers.getToken();
        userPassword = usersPjo.getPassword();
        userMobileNumber = usersPjo.getMobileNumber();
        userName = usersPjo.getUsername();
        System.out.println("My Token Value is: " + registeredToken);
        System.out.println("My Password Value is: " + userPassword);
        System.out.println("My Mobile Number Value is: " + userMobileNumber);
    }

    @Test( priority = 3)
    public void confirmUsersMobile() throws SQLException {
    //calling the MySQL method to get the code value from database
        MySQLConnection mySqlCon = new MySQLConnection();
        String code = mySqlCon.getCodeMethod();
    //Hashmap serialize
    HashMap<String, String> userMobile = new HashMap<>();
    userMobile.put("code",code);
    userMobile.put("type","web");

      String confirmResponseBody = given().spec(requestSpecification).contentType(ContentType.JSON).body(userMobile).log().all().
              when().patch("/tokens/"+registeredToken+"/msisdns").
            then().extract().response().asString();
        System.out.println(confirmResponseBody);
    }

    @Test( priority = 4)
    public void loginUser() throws JsonProcessingException, SQLException {


        // here I am using hashmap class to serialize the payload
        HashMap<String, String> userBody = new HashMap<>();

        userBody.put("password",userPassword);
        userBody.put("type", "web");

        // here serializing the payload outside the rest assured library using the jackson object mapper class
        ObjectMapper objectMapper = new ObjectMapper();
        String mainObjectStr = objectMapper.writeValueAsString(userBody);

        // parsed the response body to a response variable
        String  response = given().spec(requestSpecification).log().all().contentType("application/problem+json; charset=utf-8").
                body(mainObjectStr).when().post("/usernames/"+userName).
                then().spec(responseSpecification).extract().response().asString();

        System.out.println(response);

        //Get JsonPath instance of above JSON string which is response
        JsonPath jsonPath = new JsonPath(response);
        //create a string variable to parse the message value
        String message = jsonPath.getString("message");
        loginToken = jsonPath.getString("token");

        //assert that message value is successfulLogin
        assertThat(message, equalTo("sucessfulLogin"));

        MySQLConnection mySQLConnection = new MySQLConnection();
        mySQLConnection.deleteDataMethod();

    }

//    @Test
//    public void updateUserInfo(){
//
//
//        updateInfoPjo = new CreateUserPjo("test1", "test1", "test@example.com", "96170808080", "mtn", "123456789",
//                "123456789", "2000-01-09T20:10:10.709Z", "beirut", "lebanon", "male", "Mr", "test3", "lebanese",
//                "123456", "2024-01-09T20:10:10.709Z", "Rl 123456", 0, "154875");
//        String updateInfoStr =  given().spec(requestSpecification).contentType(ContentType.JSON).body(updateInfoPjo).log().all().when().put("/tokens/"+loginToken).then().extract().response().asString();
//
//        JsonPath jsonPath = new JsonPath(updateInfoStr);
//        String message = jsonPath.getString("message");
//
//        System.out.println(updateInfoStr);
//
//    }

//    @Test
//    public void fetchWebUserInfo(){
//
//
//    }
//
//


}
