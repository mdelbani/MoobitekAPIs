package com.webapi.pickx;

import com.common.LoginUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.*;

import java.sql.SQLException;
import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class PickaxE2E {

    RequestSpecification pickXRequestSpecification;
    ResponseSpecification pickCResponseSpecification;
    String loginToken;

    @BeforeClass
    public void beforeClass(){

        RequestSpecBuilder pickaxRequestSpecBuilder = new RequestSpecBuilder();
        ResponseSpecBuilder pickaxResponseSpecBuilder = new ResponseSpecBuilder();
        pickaxRequestSpecBuilder.setBaseUri("http://172.16.3.33:5001/pick-x-api/v1");
        pickaxRequestSpecBuilder.setContentType(ContentType.JSON);
        pickaxResponseSpecBuilder.expectStatusCode(200);
        pickXRequestSpecification = pickaxRequestSpecBuilder.build();
        pickCResponseSpecification = pickaxResponseSpecBuilder.build();
    }
    @Test
    public void checkPickXService(){

       String pickXResponse =
               given().spec(pickXRequestSpecification).log().all().
               when().get("/status").
               then().spec(pickCResponseSpecification).log().all().extract().response().asString();

        JsonPath jsonPath = new JsonPath(pickXResponse);
        String pickXMessage = jsonPath.getString("message");
        assertThat(pickXMessage, equalTo("statusAlive"));

    }
    @Test
    public void purchasePickXTicket() throws SQLException, JsonProcessingException {

        LoginUser loginUser = new LoginUser();
        loginUser.beforeClass();
        loginToken = loginUser.loginUser();

        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("IsBridge","false");
        queryParams.put("IsWallet","true");

        HashMap<String, Object> pickXPayload = new HashMap<>();
        pickXPayload.put("stake",200);
        pickXPayload.put("numbers","1,2,3,4,5");
        pickXPayload.put("currency","GHC");
        pickXPayload.put("numberOfPicks",5);
        pickXPayload.put("mobileNumber","22196170824488");
        pickXPayload.put("operator","mtn");

        String pickXPurchaseResponse = given().spec(pickXRequestSpecification).queryParams(queryParams).
                body(pickXPayload).log().all().
                when().post("/tokens/"+loginToken+"/tickets").
                then().extract().response().asString();

        JsonPath jsonPath = new JsonPath(pickXPurchaseResponse);
        String response = jsonPath.getString("message");
        assertThat(response, equalTo("pickXSuccessBought"));


    }
}
