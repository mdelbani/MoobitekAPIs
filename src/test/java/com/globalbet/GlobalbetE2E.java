package com.globalbet;

import GlobalbetPojoClasses.*;
import MySQLDatabaseConnection.MySQLConnection;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.path.xml.XmlPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.*;

import java.sql.SQLException;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class GlobalbetE2E {

    RequestSpecification globalBetRequestSpecification;
    ResponseSpecification globalBetResponseSpecification;
    String SubjectSessionId;
    String Subject;
    String Login;
    String Balance;
    String VirtualId;
    String Code;
    String SessionToken;
    String PlayerLogin;
    String PlayerBalance;
    String playerExpectedBalance;

    @BeforeClass
    public void beforeClass(){

        RequestSpecBuilder globalBetRequestSpecBuilder = new RequestSpecBuilder();
        ResponseSpecBuilder globalBetResponseSpecBuilder = new ResponseSpecBuilder();
        globalBetRequestSpecBuilder.setBaseUri("http://172.16.3.33:5013/globalbet-api/v1");
        globalBetRequestSpecBuilder.setContentType("application/xml; charset=utf-8");
        globalBetResponseSpecBuilder.expectStatusCode(200);
        globalBetRequestSpecification = globalBetRequestSpecBuilder.build();
        globalBetResponseSpecification = globalBetResponseSpecBuilder.build();
    }
    //the below three APIs are for Agent Adapter module
    @Test(priority = 0)
    public void loginAgentByPassword() throws JsonProcessingException {

        GlobalBetAuthorizationPjo nestedObject = new GlobalBetAuthorizationPjo("virtualsports","systemPwd");
        LoginAgentByPassPjo mainObject = new LoginAgentByPassPjo("Mojabet-QA","P@ssw0rd", nestedObject);

        String loginAgentByPassword = given().spec(globalBetRequestSpecification).
                body(mainObject).when().post("/loginagentbypassword").
                then().spec(globalBetResponseSpecification).extract().response().asString();

        XmlPath xmlPath = new XmlPath(loginAgentByPassword);
        SubjectSessionId = xmlPath.get("LoginAgentByPasswordResponse.SubjectSessionId");
        Subject = xmlPath.get("LoginAgentByPasswordResponse.Subject");
        Login = xmlPath.get("LoginAgentByPasswordResponse.Login");

        assertThat(mainObject.getLogin(), equalTo(Subject));
    }

    @Test(priority = 1)
    public void getAgentBalance(){

        GlobalBetAuthorizationPjo authorizationGetBalancePjo = new GlobalBetAuthorizationPjo("virtualsports","systemPwd");
        GetAgentBalance getAgentBalance = new GetAgentBalance(SubjectSessionId,Subject,authorizationGetBalancePjo);

        String response = given().spec(globalBetRequestSpecification).body(getAgentBalance).log().all().
                when().post("/getagentbalance").
                then().spec(globalBetResponseSpecification).extract().response().asString();

        XmlPath xmlPath = new XmlPath(response);
        Balance = xmlPath.get("GetAgentBalanceResponse.Balance");

        assertThat(Balance, equalTo("0"));
    }

    @Test(priority = 2)
    public void loadAgentTree(){

        GlobalBetAuthorizationPjo authorizationGetBalancePjo = new GlobalBetAuthorizationPjo("virtualsports","systemPwd");
        GetAgentTree getAgentTree = new GetAgentTree(SubjectSessionId,Subject,authorizationGetBalancePjo);

        String responseTree = given().spec(globalBetRequestSpecification).body(getAgentTree).
                when().post("/loadagenttree").
                then().spec(globalBetResponseSpecification).extract().response().asString();

        XmlPath xmlPath = new XmlPath(responseTree);
        String fullName = xmlPath.get("LoadAgentTreeResponse.Agent.SubAgents.Agent.FullName");
        assertThat(fullName, equalTo("www.mojabet.co.tz"));
    }
    // The below APIs are for Authentication & User Management
    @Test(priority = 3)
    public void generateCode() throws SQLException {

        MySQLConnection mySQLConnection = new MySQLConnection();
        VirtualId = mySQLConnection.getVirtualId();

        Code = given().baseUri("http://172.16.3.33:5000/globalbet-api").queryParam("playerid",VirtualId)
                .when().get("/generatecode").then().spec(globalBetResponseSpecification).extract().response().asString();

        System.out.println(Code);
    }
    @Test(priority = 4)
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
        System.out.println(SessionToken);
    }
    @Test(priority = 5)
    public void getBalance() throws SQLException {

        MySQLConnection mySQLConnection = new MySQLConnection();
        PlayerBalance = String.valueOf(mySQLConnection.getBalance());

        GlobalBetAuthorizationPjo globalBetAuthorizationPjo = new GlobalBetAuthorizationPjo("virtualsports","systemPwd");
        GetPlayerBalance getPlayerBalance = new GetPlayerBalance(PlayerLogin,SessionToken,globalBetAuthorizationPjo);

        String playerBalance = given().spec(globalBetRequestSpecification).body(getPlayerBalance).log().all().
                when().post("/getbalance").
                then().extract().response().asString();

        XmlPath xmlPath = new XmlPath(playerBalance);
        playerExpectedBalance = xmlPath.get("GetBalanceResponse.Balance");
        assertThat(playerExpectedBalance, equalTo(PlayerBalance));
    }
}
