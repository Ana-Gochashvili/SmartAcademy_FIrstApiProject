package lombok_tests;

import dataObject.AlertsAndMessages;
import dataObject.DataBuilder;
import endpoints.Endpoint;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseAuth;

import static dataObject.StatusCodesData.BED_REQUEST_400;
import static dataObject.StatusCodesData.CREATED_201;
import static dataObject.UserData.*;

public class LombokTest extends BaseAuth {
    @Test
    public void userAccountTesting_ValidPassword(){
        DataBuilder userData = DataBuilder
                .builder()
                .userName(userName_1)
                .password(password)
                .build();

        Response responseBody = RestAssured
                .given()
                .headers(headerConfig.getHeaders())
                .body(userData)
                .when()
                .post(Endpoint.Account_User)
                .then()
                .extract().response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseBody.getStatusCode(), CREATED_201.getValue(), "Incorrect success status code!");
        softAssert.assertTrue(responseBody.getBody().asString().contains("userID"), "The response body should contain an user ID!");
        softAssert.assertAll();
    }

    @Test
    public void userAccountTesting_InvalidPassword() {
        DataBuilder userData = DataBuilder
                .builder()
                .userName(userName_2)
                .password(invalidPassword)
                .build();

        Response responseBody = RestAssured
                .given()
                .headers(headerConfig.getHeaders())
                .body(userData)
                .when()
                .post(Endpoint.Account_User)
                .then()
                .extract().response();

        String actualMessage = responseBody.jsonPath().getString("message");
        String expectedMessage = AlertsAndMessages.passwordValidationErrorMessage;

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseBody.getStatusCode(), BED_REQUEST_400.getValue(), "Incorrect error status code!");
        softAssert.assertEquals(actualMessage, expectedMessage, "Incorrect error message!");
        softAssert.assertAll();
    }
}
