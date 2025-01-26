package api_tests;

import endpoints.Endpoint;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseAuth;

import static dataObject.StatusCodesData.SUCCESS_200;

public class BookStoreTest extends BaseAuth {
    @Test
    public void getBooks() {
        Response response = RestAssured
                .given()
                .when()
                .get(Endpoint.BookStore_Book)
                .then()
                .extract().response();

        SoftAssert softAssert = new SoftAssert();

        int statusCode = response.getStatusCode();
        String authorName = response.getBody().jsonPath().getString("books[0].author");
        String publisherName = response.getBody().jsonPath().getString("books[0].publisher");

        softAssert.assertEquals(statusCode, SUCCESS_200.getValue());
        softAssert.assertEquals(authorName, "Richard E. Silverman");
        softAssert.assertEquals(publisherName, "O'Reilly Media");
        softAssert.assertAll();
    }
}
