package com.web.wallet;

import MySQLDatabaseConnection.MySQLConnection;
import UserPojoClasses.FetchUser.GetUserInfoPjo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class WalletWebE2E {

    RequestSpecification walletRequestSpecification;
    RequestSpecification loginRequestSpecification;
    ResponseSpecification walletResponseSpecification;
    ResponseSpecification loginResponseSpecification;
    RequestSpecification TPRequestSpecification;
    ResponseSpecification TPResponseSpecification;
    String userPassword;
    String userName;
    String loginToken;
    int Balance;
    int expectedAmount;
    int amount = 200;
    long msisdn;

    @BeforeClass
    public void beforeClass(){

        RequestSpecBuilder walletRequestSpecBuilder = new RequestSpecBuilder();
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        walletRequestSpecBuilder.setBaseUri("http://172.16.3.33:5007/wallet-transaction-api/v1/tokens/");
        responseSpecBuilder.expectStatusCode(200);
        RequestSpecBuilder loginRequestSpecBuilder = new RequestSpecBuilder();
        ResponseSpecBuilder loginResponseSpecBuilder = new ResponseSpecBuilder();
        loginRequestSpecBuilder.setBaseUri("http://172.16.3.33:5006/user-api/v1");
        responseSpecBuilder.expectStatusCode(200);
        RequestSpecBuilder TPRequestSpecBuilder = new RequestSpecBuilder();
        ResponseSpecBuilder TPResponseSpecBuilder = new ResponseSpecBuilder();
        TPRequestSpecBuilder.setBaseUri("http://172.16.3.33:8076/tp");
        TPResponseSpecBuilder.expectStatusCode(200);
        TPRequestSpecification = TPRequestSpecBuilder.build();
        TPResponseSpecification = TPResponseSpecBuilder.build();
        walletRequestSpecification = walletRequestSpecBuilder.build();
        walletResponseSpecification = responseSpecBuilder.build();
        loginRequestSpecification = loginRequestSpecBuilder.build();
        loginResponseSpecification = loginResponseSpecBuilder.build();
    }
    @Test(priority = 1)
    public void loginUser() throws SQLException {

        MySQLConnection mySQLConnection = new MySQLConnection();
        userPassword = mySQLConnection.getUserPassword();
        userName = mySQLConnection.getUserName();

        // here I am using hashmap class to serialize the payload
        HashMap<String, String> userBody = new HashMap<>();

        userBody.put("password", "mdelbani@82");
        userBody.put("type", "web");

        // parsed the response body to a response variable
        GetUserInfoPjo loginResponse = given().spec(loginRequestSpecification).log().ifValidationFails().contentType("application/problem+json; charset=utf-8").
                body(userBody).when().post("/usernames/"+userName).
                then().spec(loginResponseSpecification).extract().response().as(GetUserInfoPjo.class);

        String expectedResponseMessage = loginResponse.getMessage();
        loginToken = loginResponse.getToken();
        assertThat(expectedResponseMessage, equalTo("sucessfulLogin"));
        System.out.println("You have logged in successfully to your account"+"\n"+loginToken);
    }
    @Test(priority = 2)
    public void getWalletBalance() throws SQLException {

        MySQLConnection mySQLConnection = new MySQLConnection();
        Balance =mySQLConnection.getBalance();


        String walletBalanceResponse = given().spec(walletRequestSpecification).
                when().get(loginToken+"/wallets").then().extract().response().asString();

        JsonPath jsonPath = new JsonPath(walletBalanceResponse);
        expectedAmount = jsonPath.getInt("amount");
        String walletMessage = jsonPath.getString("message");

        assertThat(expectedAmount, equalTo(Balance));
        assertThat(walletMessage, equalTo("balanceFetchedSuccess"));
        System.out.println("You have retrieved your balance amount from your wallet");
        System.out.println("Expected Amount "+expectedAmount+" is equal to Actual Amount "+Balance);
    }
    @Test(priority = 3)
    public void addDepositWallet(){

        HashMap<String, Integer> mainObject = new HashMap<>();
        mainObject.put("amount",amount);

       String walletPaymentResponse =  given().spec(walletRequestSpecification).body(mainObject).
               contentType("application/json; charset=utf-8").log().all()
               .when().post(loginToken+"/amounts").
               then().spec(walletResponseSpecification).extract().response().asString();

       JsonPath jsonPath = new JsonPath(walletPaymentResponse);
       String paymentResponse = jsonPath.getString("message");
        System.out.println(paymentResponse);

       assertThat(paymentResponse, equalTo("paymentInitiationSuccessful"));
    }
    @Test(priority = 4)
    public void walletCallBack() throws SQLException {

        MySQLConnection mySQLConnection = new MySQLConnection();
        int transactionId = mySQLConnection.getTransactionId();

        HashMap<String, Object> queryParam = new HashMap<>();
        queryParam.put("msisdn",msisdn);
        queryParam.put("transactionId",transactionId);
        queryParam.put("processingcode",1);
        queryParam.put("tpTransId",transactionId);
        queryParam.put("amount",amount);
        queryParam.put("operator","mtn");
        queryParam.put("msisdn",22196170824488l);
        queryParam.put("transactionId",transactionId);

        String processPaymentResponse = given().spec(TPRequestSpecification).queryParams(queryParam).when()
                .get("/momo/processPayment").
                then().extract().response().asString();

        JsonPath jsonPath = new JsonPath(processPaymentResponse);

        assertThat(jsonPath.getString("transactionId"), matchesPattern("^[a-zA-Z0-9]{19}$"));
        System.out.println(processPaymentResponse);
    }
    @Test(priority = 5)
    public void withdrawalWallet(){

        HashMap<String, Integer> mainObject = new HashMap<>();
        mainObject.put("amount",amount);

        String walletPaymentResponse =  given().spec(walletRequestSpecification).body(mainObject).
                queryParam("IsWinningWallet","false").
                contentType("application/json; charset=utf-8").log().all()
                .when().patch(loginToken+"/amounts").
                then().spec(walletResponseSpecification).extract().response().asString();

        JsonPath jsonPath = new JsonPath(walletPaymentResponse);
        String paymentResponse = jsonPath.getString("message");
        System.out.println(paymentResponse);

        assertThat(paymentResponse, equalTo("withdrawSuccessful"));
        if (expectedAmount == Balance){

            System.out.println("Congratulation, Expected Amount = Balance");
        }
    }
}
