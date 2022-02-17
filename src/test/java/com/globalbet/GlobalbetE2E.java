package com.globalbet;

import GlobalbetPojoClasses.AuthorizationPjo;
import GlobalbetPojoClasses.GlobalbetPjo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.path.xml.XmlPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.checkerframework.common.value.qual.MatchesRegex;
import org.hamcrest.text.MatchesPattern;
import org.testng.annotations.*;
import javax.xml.bind.annotation.*;
import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class GlobalbetE2E {

    RequestSpecification globalBetRequestSpecification;
    ResponseSpecification globalBetResponseSpecification;

    String SubjectSessionId;
    String Subject;

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
    @Test
    public void loginAgentByPassword() throws JsonProcessingException {

        AuthorizationPjo nestedObject = new AuthorizationPjo("virtualsports","systemPwd");
        GlobalbetPjo mainObject = new GlobalbetPjo("Mojabet-QA","P@ssw0rd", nestedObject);

        String loginAgentByPassword = given().spec(globalBetRequestSpecification).
                body(mainObject).
                when().post("/loginagentbypassword").
                then().spec(globalBetResponseSpecification).extract().response().asString();

        XmlPath xmlPath = new XmlPath(loginAgentByPassword);
        SubjectSessionId = xmlPath.get("LoginAgentByPasswordResponse.SubjectSessionId");
        Subject = xmlPath.get("LoginAgentByPasswordResponse.Subject");

        assertThat(mainObject.getLogin(), equalTo(Subject));
        assertThat(SubjectSessionId, matchesPattern("^[a-zA-Z0-9]{152}$"));

        System.out.println(Subject);
    }
}
