package com.web.pick3;

import MySQLDatabaseConnection.MySQLConnection;
import UserPojoClasses.FetchUser.GetUserInfoPjo;
import com.common.LoginUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Pick3WebE2E {

    RequestSpecification pick3RequestSpecification;
    ResponseSpecification pick3ResponseSpecification;
    RequestSpecification loginRequestSpecification;
    ResponseSpecification loginResponseSpecification;
    String loginToken;
    String userName;
    @BeforeClass
    public void beforeClass(){

        RequestSpecBuilder loginRequestSpecBuilder = new RequestSpecBuilder();
        ResponseSpecBuilder loginResponseSpecBuilder = new ResponseSpecBuilder();
        RequestSpecBuilder pick3RequestBuilder = new RequestSpecBuilder();
        ResponseSpecBuilder pick3ResponseBuilder = new ResponseSpecBuilder();
        loginRequestSpecBuilder.setBaseUri("http://172.16.3.33:5006/user-api/v1");
        loginResponseSpecBuilder.expectStatusCode(200);
        pick3RequestBuilder.setBaseUri("http://172.16.3.33:5002/pick-three-api/v1");
        pick3RequestBuilder.setContentType("application/problem+json; charset=utf-8");
        pick3ResponseBuilder.expectStatusCode(200);
        loginRequestSpecification = loginRequestSpecBuilder.build();
        loginResponseSpecification = loginResponseSpecBuilder.build();
        pick3RequestSpecification = pick3RequestBuilder.build();
        pick3ResponseSpecification = pick3ResponseBuilder.build();
    }
    @Test(priority = 0)
    public void checkPick3Service(){

        String Pick3ServiceResponse = given().spec(pick3RequestSpecification).when().
                get("/status").then().extract().response().asString();

        JsonPath jsonPath = new JsonPath(Pick3ServiceResponse);
        String message = jsonPath.getString("message");

        assertThat(message, equalTo("statusAlive"));

    }
    @Test(priority = 1)
    public void loginUser() throws JsonProcessingException, SQLException {

        MySQLConnection mySQLConnection = new MySQLConnection();
        //userPassword = mySQLConnection.getUserPassword();
        userName = mySQLConnection.getUserName();
        // here I am using hashmap class to serialize the payload
        HashMap<String, String> userBody = new HashMap<>();
        userBody.put("password","mdelbani@82");
        userBody.put("type", "web");

        // parsed the response body to a response variable
        GetUserInfoPjo loginResponse = given().spec(loginRequestSpecification).log().ifValidationFails().contentType("application/problem+json; charset=utf-8").
                body(userBody).when().post("/usernames/"+userName).
                then().spec(loginResponseSpecification).extract().response().as(GetUserInfoPjo.class);

        String expectedResponseMessage = loginResponse.getMessage();
        loginToken = loginResponse.getToken();
        assertThat(expectedResponseMessage, equalTo("sucessfulLogin"));
        System.out.println("You have logged in successfully to your account");
        System.out.println(loginToken);

    }
    @Test(priority = 2)
    public void purchaseWebTicket() throws SQLException, JsonProcessingException {

        HashMap<String, String> pick3queryParam = new HashMap<>();
        pick3queryParam.put("IsBridge","false");
        pick3queryParam.put("IsWallet","true");
        HashMap<String, Object> pick3bodyPayload = new HashMap<>();
        pick3bodyPayload.put("stake", 200);
        pick3bodyPayload.put("numbers","1,2,3");
        pick3bodyPayload.put("currency","GHC");
        pick3bodyPayload.put("mobileNumber", "22196170824488");
        pick3bodyPayload.put("operator","string");

        String purchasePick3Response =  given().spec(pick3RequestSpecification).queryParams(pick3queryParam).
        body(pick3bodyPayload).log().all().when().post("/tokens/"+loginToken+"/tickets")
                .then().spec(pick3ResponseSpecification).log().all().extract().response().asString();

      JsonPath jsonPath = new JsonPath(purchasePick3Response);
      String actualPick3Response = jsonPath.getString("message");
      assertThat(actualPick3Response, equalTo("pickThreeSuccessBought"));
    }
}
