package com.globalbet;

import GlobalbetPojoClasses.*;
import MySQLDatabaseConnection.MySQLConnection;
import com.common.BaseURI;
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

public class GlobalbetAgentE2E {

    RequestSpecification globalBetRequestSpecification;
    ResponseSpecification globalBetResponseSpecification;
    String SubjectSessionId;
    String Subject;
    String Login;
    String Balance;
    BaseURI baseURI = new BaseURI();
    String BaseURI = baseURI.setBaseURI();

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
    //the below three APIs are for Agent Adapter module
    @Test(priority = 0)
    public void loginAgentByPassword() throws JsonProcessingException {

        GlobalBetAuthorizationPjo nestedObject = new GlobalBetAuthorizationPjo("virtualsports","systemPwd");
        LoginAgentByPassPjo mainObject = new LoginAgentByPassPjo("Mojabet-TZ","P@ssw0rd", nestedObject);

        String loginAgentByPassword = given().spec(globalBetRequestSpecification).
                body(mainObject).when().post("/loginagentbypassword").
                then().spec(globalBetResponseSpecification).extract().response().asString();

        XmlPath xmlPath = new XmlPath(loginAgentByPassword);
        SubjectSessionId = xmlPath.get("LoginAgentByPasswordResponse.SubjectSessionId");
        Subject = xmlPath.get("LoginAgentByPasswordResponse.Subject");
        Login = xmlPath.get("LoginAgentByPasswordResponse.Login");

        assertThat(mainObject.getLogin(), equalTo(Subject));
        System.out.println("Agent Logged In Successfully");
    }
    @Test(priority = 1)
    public void getAgentBalance(){

        GlobalBetAuthorizationPjo authorizationGetBalancePjo = new GlobalBetAuthorizationPjo("virtualsports","systemPwd");
        GetAgentBalance getAgentBalance = new GetAgentBalance(SubjectSessionId,Subject,authorizationGetBalancePjo);

        String response = given().spec(globalBetRequestSpecification).body(getAgentBalance).
                when().post("/getagentbalance").
                then().spec(globalBetResponseSpecification).extract().response().asString();

        XmlPath xmlPath = new XmlPath(response);
        Balance = xmlPath.get("GetAgentBalanceResponse.Balance");

        assertThat(Balance, equalTo("0"));
        System.out.println("get Agent API works fine");
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
        System.out.println("load agent tree succeed");
    }
}
