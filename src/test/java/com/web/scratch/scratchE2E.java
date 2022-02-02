package com.web.scratch;


import Pick3PojoClasses.PickPjo;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class scratchE2E {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    @BeforeClass
    public void beforeClass(){

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        requestSpecBuilder.setBaseUri("http://172.16.3.33:8076/tp/instantlotto/ussd/pick3");
        requestSpecification = requestSpecBuilder.build();
        responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void reqTicket() {

        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("msisdn", "96170824488");
        queryParams.put("amount", "100");
        queryParams.put("operator", "MTN");
        queryParams.put("numbers", "1,2,3");
        queryParams.put("accessChannel", "USSD");

        PickPjo deserializerPjo = given().spec(requestSpecification).queryParams(queryParams).log().ifValidationFails().
                when().get("/reqestsSellPick3Ticket").
                then().log().ifValidationFails().extract().response().as(PickPjo.class);

        assertThat(queryParams.get("amount"),equalTo(deserializerPjo.getPrice()));
        assertThat(deserializerPjo.getSerialNumber(), matchesPattern("^[a-zA-Z0-9]{7}$"));
    }
    }