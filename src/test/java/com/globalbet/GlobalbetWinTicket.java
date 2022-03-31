package com.globalbet;

import GlobalbetPojoClasses.*;
import MySQLDatabaseConnection.MySQLConnection;
import com.common.BaseURI;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.path.xml.XmlPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GlobalbetWinTicket {

    RequestSpecification globalBetRequestSpecification;
    ResponseSpecification globalBetResponseSpecification;
    String VirtualId;
    String Code;
    String SessionToken;
    String PlayerLogin;
    String PlayerBalance;
    String playerExpectedBalance;
    float Stake;
    long ticketNumber;
    String TicketStatus;
    BaseURI baseURI = new BaseURI();
    MySQLConnection mySQLConnection = new MySQLConnection();
    String BaseURI = baseURI.setBaseURI();

    public GlobalbetWinTicket() throws SQLException {
    }

    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder globalBetRequestSpecBuilder = new RequestSpecBuilder();
        ResponseSpecBuilder globalBetResponseSpecBuilder = new ResponseSpecBuilder();
        globalBetRequestSpecBuilder.setBaseUri("http://"+BaseURI+":5013/globalbet-api/v1");
        globalBetRequestSpecBuilder.setContentType("application/xml; charset=utf-8");
        globalBetResponseSpecBuilder.expectStatusCode(200);
        globalBetRequestSpecification = globalBetRequestSpecBuilder.build();
        globalBetResponseSpecification = globalBetResponseSpecBuilder.build();
    }
    @Test(priority = 0)
    public void generateCode() throws SQLException {

        MySQLConnection mySQLConnection = new MySQLConnection();
        VirtualId = mySQLConnection.getVirtualId();

        Code = given().baseUri("http://172.16.3.33:5013/globalbet-api/v1").queryParam("playerid",VirtualId)
                .when().get("/generatecode").then().spec(globalBetResponseSpecification).extract().response().asString();

        assertThat(VirtualId, matchesPattern("^[a-zA-Z0-9]{64}$"));
        System.out.println("This is the player id: "+VirtualId);
        System.out.println("This is the generated code: "+Code);
    }
    @Test(priority = 1)
    public void authenticatePlayerByCode(){

        GlobalBetAuthorizationPjo globalBetAuthorizationPjo = new GlobalBetAuthorizationPjo("virtualsports","systemPwd");
        //here I use the VirtualId as Login since the login filed should contain the virtualId of the user
        AuthenticateByCodeRequest authenticateByCodeRequest = new AuthenticateByCodeRequest(VirtualId,Code,globalBetAuthorizationPjo);

        String authenticateResponse = given().spec(globalBetRequestSpecification).body(authenticateByCodeRequest).
                when().post("/authenticateplayerbycode").
                then().extract().response().asString();

        XmlPath xmlPath = new XmlPath(authenticateResponse);
        SessionToken = xmlPath.get("AuthenticateByCodeResponse.SessionToken");
        PlayerLogin = xmlPath.get("AuthenticateByCodeResponse.PlayerLogin");
        assertThat(PlayerLogin, equalTo(VirtualId));
        System.out.println("Player authenticated successfully and the following is the session token: "+SessionToken);
    }
    @Test(priority = 2)
    public void getBalance() throws SQLException {

        GlobalBetAuthorizationPjo globalBetAuthorizationPjo = new GlobalBetAuthorizationPjo("virtualsports","systemPwd");
        GetPlayerBalance getPlayerBalance = new GetPlayerBalance(PlayerLogin,SessionToken,globalBetAuthorizationPjo);

        String playerBalance = given().spec(globalBetRequestSpecification).body(getPlayerBalance).
                when().post("/getbalance").
                then().extract().response().asString();
        PlayerBalance = String.valueOf(mySQLConnection.getBalance());
        XmlPath xmlPath = new XmlPath(playerBalance);
        playerExpectedBalance = xmlPath.get("GetBalanceResponse.Balance");
        System.out.println(playerExpectedBalance);
        assertThat(playerExpectedBalance, equalTo(PlayerBalance));
        System.out.println("Balance retrieved successfully : "+playerExpectedBalance);
    }
    //the below APIs are for open tickets & add to wallet
    @Test(priority = 3)
    public void openTicket(){

        GlobalBetAuthorizationPjo globalBetAuthorizationPjo = new GlobalBetAuthorizationPjo("virtualsports","systemPwd");
        OpenTicketRequest openTicketRequest = new OpenTicketRequest(VirtualId,SessionToken,200,200,"TZS",globalBetAuthorizationPjo);

        String openTicketResponse = given().spec(globalBetRequestSpecification).body(openTicketRequest)
                .when().post("/openTicket").then().extract().response().asString();

        XmlPath xmlPath = new XmlPath(openTicketResponse);
        ticketNumber = openTicketRequest.getTicketNumber();
        Stake = openTicketRequest.getStake();
        String externalTicketId = xmlPath.get("OpenTicketResponse.ExternalTicketId");

        assertThat(externalTicketId, containsString(String.valueOf(ticketNumber)));
        System.out.println("Ticket purchased successfully");
    }
    @Test(priority = 4)
    public void winTicket() throws SQLException {

        GlobalBetAuthorizationPjo globalBetAuthorizationPjo = new GlobalBetAuthorizationPjo("virtualsports","systemPwd");
        PayoutTicketPjo payoutTicketPjo = new PayoutTicketPjo(VirtualId,SessionToken,200,200,200,200,0,"TZS",globalBetAuthorizationPjo);

        String winResponse = given().spec(globalBetRequestSpecification).body(payoutTicketPjo)
                .when().post("/payoutTicket").then().log().all().extract().response().asString();

        XmlPath xmlPath = new XmlPath(winResponse);
        playerExpectedBalance = xmlPath.get("PayoutTicketResponse.Balance");
        PlayerBalance = String.valueOf(mySQLConnection.getBalance());
        assertThat(playerExpectedBalance, equalTo(PlayerBalance));
        System.out.println("Ticket Wins & Balance retrieved successfully : "+playerExpectedBalance);
        TicketStatus = mySQLConnection.getTicketStatus();
        assertThat(TicketStatus, equalTo("win"));
        System.out.println("Ticket Status is :"+TicketStatus);
        mySQLConnection.getTicketId();
        mySQLConnection.deleteTicket();
    }
}
