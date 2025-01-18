package api_tests;

import dataObject.AlertsAndMessages;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseAuth;

import static dataObject.StatusCodesData.BED_REQUEST_400;
import static dataObject.StatusCodesData.CREATED_201;
import static dataObject.UserData.*;
import static endpoints.AccountsPage.POST_ACCOUNT_USER;

public class AccountsTest extends BaseAuth {
    @Test
    public void postUserAccountWithValidPassword() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("userName", userName_1);
        requestBody.put("password", password);

        Response response = RestAssured
                .given()
                .headers(headerConfig.getHeaders())
                .body(requestBody.toString())
                .when()
                .post(POST_ACCOUNT_USER)
                .then()
                .extract().response();

        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asString();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(statusCode, CREATED_201.getValue(), "Incorrect success status code!");
        softAssert.assertTrue(responseBody.contains("userID"), "The response body should contain an user ID!");
        softAssert.assertAll();
    }

    @Test
    public void postUserAccountWithInvalidPassword() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("userName", userName_2);
        requestBody.put("password", invalidPassword);

        Response response = RestAssured
                .given()
                .headers(headerConfig.getHeaders())
                .body(requestBody.toString())
                .when()
                .post(POST_ACCOUNT_USER)
                .then()
                .extract().response();

        int statusCode = response.getStatusCode();
        String actualMessage = response.jsonPath().getString("message");
        String expectedMessage = AlertsAndMessages.passwordValidationErrorMessage;

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(statusCode, BED_REQUEST_400.getValue(), "Incorrect error status code!");
        softAssert.assertEquals(actualMessage, expectedMessage, "Incorrect error message!");
        softAssert.assertAll();
    }
}
