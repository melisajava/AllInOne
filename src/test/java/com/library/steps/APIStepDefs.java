package com.library.steps;

import com.library.pages.BasePage;
import com.library.pages.LoginPage;
import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import com.library.utility.LibraryAPI_Util;
import io.cucumber.java.en.*;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@Slf4j
public class APIStepDefs {

    RequestSpecification givenPart = given().log().uri();
    Response response;
    ValidatableResponse thenPart;
    JsonPath jp;
    public static Map<String, Object> randomDataMap;
    public static String userId;
    String accessToken ;


    //US01
    @Given("I logged Library api as a {string}")
    public void i_logged_library_api_as_a(String role) {
        accessToken = LibraryAPI_Util.getToken(role);
        givenPart.header("x-library-token", accessToken);
    }

    @Given("Accept header is {string}")
    public void accept_header_is(String expectedAcceptHeader) {
        givenPart.accept(expectedAcceptHeader);
    }

    @When("I send GET request to {string} endpoint")
    public void i_send_get_request_to_endpoint(String endpoint) {
        response = givenPart.when().get(endpoint);
        response.prettyPrint();
        //jp = response.jsonPath();
    }

    @Then("status code should be {int}")
    public void status_code_should_be(int expectedStatusCode) {
        thenPart = response.then();
        thenPart.statusCode(expectedStatusCode);

    }

    @Then("Response Content type is {string}")
    public void response_content_type_is(String expectedContentType) {
        thenPart.contentType(expectedContentType);
    }

    @Then("Each {string} field should not be null")
    public void each_field_should_not_be_null(String path) {
        thenPart.body(path, everyItem(notNullValue()));
    }

    //US02
    @And("Path param {string} is {string}")
    public void pathParamIs(String id, String expectedValue) {
        givenPart.pathParam(id, expectedValue);
    }

    @And("{string} field should be same with path param")
    public void fieldShouldBeSameWithPathParam(String path) {
        jp = response.jsonPath();
        //String id = jp.getString(path);
        thenPart.body(path, is(jp.getString(path)));

    }

    @And("following fields should not be null")
    public void followingFieldsShouldNotBeNull(List<String> paths) {
        for (String each : paths) {
            jp = response.jsonPath();
            assertNotNull(jp.getString(each));
        }
    }

    //US03/SCENARIO-1
    @And("Request Content Type header is {string}")
    public void requestContentTypeHeaderIs(String expectedContentType) {
        givenPart.contentType(expectedContentType);

    }

    @And("I create a random {string} as request body")
    public void iCreateARandomAsRequestBody(String dataType) {

        switch (dataType) {
            case "book":
                randomDataMap = LibraryAPI_Util.getRandomBookMap();
                break;
            case "user":
                randomDataMap = LibraryAPI_Util.getRandomUserMap();
                break;
            default:
                throw new RuntimeException("Wrong data type is provided.");

        }
        givenPart.formParams(randomDataMap);
        System.out.println("randomDataMap = " + randomDataMap);

    }

    @When("I send POST request to {string} endpoint")
    public void iSendPOSTRequestToEndpoint(String endpoint) {
        response = givenPart.when().post(endpoint);
        response.prettyPrint();
    }

    @And("the field value for {string} path should be equal to {string}")
    public void theFieldValueForPathShouldBeEqualTo(String value, String path) {
        thenPart.body(value, equalTo(path));
    }

    @And("{string} field should not be null")
    public void fieldShouldNotBeNull(String field) {
        thenPart.body(field, is(notNullValue()));
        jp = response.jsonPath();
        userId = jp.getString("user_id");

    }

    //US05
    @Given("I logged Library api with credentials {string} and {string}")
    public void iLoggedLibraryApiWithCredentialsAnd(String email, String password) {
        accessToken = LibraryAPI_Util.getToken(email, password);

    }

    @And("I send {string} information as request body")
    public void iSendInformationAsRequestBody(String infoType) {
        givenPart.formParam(infoType, accessToken);

    }


}