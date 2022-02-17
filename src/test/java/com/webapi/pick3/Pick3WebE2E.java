package com.webapi.pick3;

import com.common.LoginUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
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
    String loginToken;
    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder pick3RequestBuilder = new RequestSpecBuilder();
        ResponseSpecBuilder pick3ResponseBuilder = new ResponseSpecBuilder();
        pick3RequestBuilder.setBaseUri("http://172.16.3.33:5002/pick-three-api/v1");
        pick3RequestBuilder.setContentType("application/problem+json; charset=utf-8");
        pick3ResponseBuilder.expectStatusCode(200);
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
    public void purchaseWebTicket() throws SQLException, JsonProcessingException {
        LoginUser loginUser = new LoginUser();
        loginUser.beforeClass();
        loginToken = loginUser.loginUser();
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
