package com.webapi.scratch;


import com.common.BaseURI;
import com.common.LoginUser;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import static org.hamcrest.Matchers.*;

public class scratchE2E {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    String loginToken;
    BaseURI baseURI = new BaseURI();
    String BaseURI = baseURI.setBaseURI();

    @BeforeClass
    public void beforeClass(){

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        requestSpecBuilder.setBaseUri("http://"+BaseURI+":5003/scratch-api/v1");
        requestSpecification = requestSpecBuilder.build();
        responseSpecification = responseSpecBuilder.build();
    }
    @Test(priority = 0)
    public void scratchServiceStatus(){
       String scratchServiceResponse = given().spec(requestSpecification).when().get("/status").then().extract().response().asString();
        JsonPath jsonPath = new JsonPath(scratchServiceResponse);
        String message = jsonPath.getString("message");
        assertThat(message, equalTo("statusAlive"));
    }
    @Test(priority = 1)
    public void purchaseScratchTicket() throws JsonProcessingException, SQLException {
        LoginUser loginUser = new LoginUser();
        loginUser.beforeClass();
        loginToken = loginUser.loginUser();
        //any solution using Pojo or Hashmap throws error due to the field called type
        //ScratchPjo scratchPjo = new ScratchPjo(1,2,"GHC");
        HashMap<String, String> queryParam = new HashMap<>();
        queryParam.put("IsWallet","true");
        queryParam.put("IsBridge","false");

        HashMap<String, Object> scratchPayload = new HashMap<>();
        scratchPayload.put("numberOfTickets",1);
        scratchPayload.put("type",2);
        scratchPayload.put("currency","GHC");

        String scratchPurchaseResponse = given().spec(requestSpecification).queryParams(queryParam)
                .body(scratchPayload).contentType(ContentType.JSON).log().all().
                when().get("/tokens/"+loginToken+"/tickets").
                then().spec(responseSpecification).log().all().extract().response().asString();

        JsonPath jsonPath = new JsonPath(scratchPurchaseResponse);
        String message = jsonPath.getString("message");
        assertThat(message,equalTo("scratchSuccessBought"));
    }
    }