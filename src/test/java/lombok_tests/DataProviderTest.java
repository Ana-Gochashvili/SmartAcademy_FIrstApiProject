package lombok_tests;

import endpoints.Endpoint;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.BaseAuth;

import static dataObject.StatusCodesData.SUCCESS_200;

public class DataProviderTest extends BaseAuth {
    @DataProvider(name = "isbnId")
    public Object[][] createISBNData(){
        return new Object[][]{
                {"9781449331818"},
                {"9781449337711"},
                {"9781449365035"},
                {"9781491904244"}
        };
    }

    @Test(dataProvider = "isbnId")
    public void validateBookByISBN(String isbn){

        Response responseBody = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .queryParam("ISBN", isbn)
                .when()
                .get(Endpoint.BookStore_Book)
                .then()
                .log()
                .all()
                .statusCode(SUCCESS_200.getValue())
                .extract().response();

        Assert.assertEquals(responseBody.jsonPath().getString("isbn"), isbn,
                "ISBN in response should match the requested ISBN");
    }
}
