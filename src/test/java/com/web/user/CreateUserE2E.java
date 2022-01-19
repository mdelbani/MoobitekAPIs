package com.web.user;

import MySQLDatabaseConnection.MySQLConnection;
import UserPojoClasses.GetUserInfoPjo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import UserPojoClasses.CreateUserPjo;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.*;
import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CreateUserE2E {

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
    public void createWebUser() {

        // this test case is to create a new user from web. Pojo class used to serialize the payload body
        usersPjo = new CreateUserPjo("Mustapha", "Delbani", "mustapha.delbani@moobitek.com", "96170824488", "mtn", "mdelbani@82",
                "mdelbani@82", "1982-03-02T20:10:10.709Z", "beirut", "lebanon", "male", "Mr", "mdelbani", "lebanese",
                "123456", "2024-01-09T20:10:10.709Z", "Rl 123456", 0, "154875");

        // deserialize the json response and convert it to Pojo class
        desUsers = given().spec(requestSpecification).body(usersPjo).contentType(ContentType.JSON).log().ifValidationFails()
                .when().post("/users")
                .then().spec(responseSpecification).log().ifValidationFails().extract().response().as(CreateUserPjo.class);

        // assertion to ensure that the user created  successfully and to get the register token to use later on confirm registration
        assertThat(desUsers.getMessage(), equalTo("userRegisteredSuccessfully"));
        System.out.println("You have been registered successfully: "+desUsers.getMessage());
        //just create variables to save the token and password
        registeredToken = desUsers.getToken();
        userPassword = usersPjo.getPassword();
        userMobileNumber = usersPjo.getMobileNumber();
        //the username variable might be used in case account will be activated by mobile number
        userName = usersPjo.getUsername();
//        System.out.println("My Token Value is: " + registeredToken);
//        System.out.println("My Password Value is: " + userPassword);
//        System.out.println("My Mobile Number Value is: " + userMobileNumber);
    }

    @Test( priority = 3)
    public void confirmUsersMobile() throws SQLException {
    //create an instance of a MySQLConnection class to call the getCodeMethod which is returning the code from DB
        MySQLConnection mySqlCon = new MySQLConnection();
        String code = mySqlCon.getCodeMethod();
    //Hashmap serialize which use the code retrieved from the MySQLConnection class and use it in the payload
    HashMap<String, String> userMobile = new HashMap<>();
    userMobile.put("code",code);
    userMobile.put("type","web");

      String confirmResponseBody = given().spec(requestSpecification).contentType(ContentType.JSON).body(userMobile).log().ifValidationFails().
              when().patch("/tokens/"+registeredToken+"/msisdns").
              then().extract().response().asString();
      //Extract the response body using JSON Path class
      JsonPath js = new JsonPath(confirmResponseBody);
      String confirmMessage = js.getString("message");
      assertThat(confirmMessage, equalTo("activationSuccessful"));
        System.out.println("Here I get the code value from database directly and pass it as argument to the confirm API body"+code);
      System.out.println("Your Account has been activated successfully: "+confirmMessage);
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
        String  response = given().spec(requestSpecification).log().ifValidationFails().contentType("application/problem+json; charset=utf-8").
                body(mainObjectStr).when().post("/usernames/"+userName).
                then().spec(responseSpecification).extract().response().asString();

        //Get JsonPath instance of above JSON string which is response
        JsonPath jsonPath = new JsonPath(response);
        //create a string variable to parse the message value
        String message = jsonPath.getString("message");
        loginToken = jsonPath.getString("token");
        //System.out.println(loginToken);
        //assert that message value is successfulLogin
        assertThat(message, equalTo("sucessfulLogin"));
        System.out.println("You have logged in successfully to your account");

    }

    @Test( priority = 5)
    public void updateUserInfo() throws SQLException, JsonProcessingException {

        updateInfoPjo = new CreateUserPjo("Mustapha", "Delbani", "mustapha.delbani@moobitek.com", "96170824488", "mtn", "mdelbani@82",
                "mdelbani@82", "1982-03-02T20:10:10.709Z", "beirut", "USA", "male", "Mr", "mdelbani", "lebanese",
                "123456", "2024-01-09T20:10:10.709Z", "Rl 123456", 0, "112233");
        String updateUserResponse =  given().spec(requestSpecification).contentType(ContentType.JSON).body(updateInfoPjo).log().ifValidationFails().
                when().put("/tokens/"+loginToken).
                then().extract().response().asString();

        String ExpectedCountryName = updateInfoPjo.getCountry();
        JsonPath jsonPath = new JsonPath(updateUserResponse);
        String ActualCountryName = jsonPath.getString("user.country");

        assertThat(ExpectedCountryName, equalTo(ActualCountryName));
        System.out.println("You have updated your account successfully");

        MySQLConnection mySQLConnection = new MySQLConnection();
        mySQLConnection.deleteDataMethod();
    }

    @Test( priority = 6)
    public void fetchWebUserInfo(){

       GetUserInfoPjo fetchUserInfo =  given().spec(requestSpecification).
               when().get("/tokens/"+loginToken).
               then().log().ifValidationFails().extract().response().as(GetUserInfoPjo.class);

       String fetchedMessageInfo = fetchUserInfo.getMessage();
       System.out.println(fetchedMessageInfo);

       assertThat(fetchedMessageInfo, equalTo("userFetchedSuccessfully"));

       System.out.println("Your user information has been fetched successfully");
    }
}
