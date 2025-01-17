package api_tests;

import endpoints.AccountsPage;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseAuth;

import static dataObject.StatusCodesData.BED_REQUEST_400;
import static dataObject.StatusCodesData.CREATED_201;

public class AccountsTest extends BaseAuth {

    @Test
    public void postUserAccountWithValidPassword() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("userName", "ANNA1234");
        requestBody.put("password", "Asdasd@1234");

        Response response = RestAssured
                .given()
                .header("connection", "keep-alive")
                .header("content-type", "application/json; charset=utf-8")
                .body(requestBody.toString())
                .when()
                .post(AccountsPage.POST_ACCOUNT_USER)
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
        requestBody.put("userName", "ANN1234");
        requestBody.put("password", "asdasd1234");

        Response response = RestAssured
                .given()
                .header("connection", "keep-alive")
                .header("content-type", "application/json; charset=utf-8")
                .body(requestBody.toString())
                .when()
                .post(AccountsPage.POST_ACCOUNT_USER)
                .then()
                .extract().response();

        int statusCode = response.getStatusCode();
        String actualMessage = response.jsonPath().getString("message");
        String expectedMessage = "Passwords must have at least one non alphanumeric character, one digit ('0'-'9'), one uppercase ('A'-'Z'), " +
                "one lowercase ('a'-'z'), one special character and Password must be eight characters or longer.";

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(statusCode, BED_REQUEST_400.getValue(), "Incorrect error status code!");
        softAssert.assertEquals(actualMessage, expectedMessage, "Incorrect error message!");
        softAssert.assertAll();
    }
}
