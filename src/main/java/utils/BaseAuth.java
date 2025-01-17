package utils;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;

public class BaseAuth {
    @BeforeMethod
    public void setBaseURL() {
        RestAssured.baseURI = "https://bookstore.toolsqa.com/";
    }
}
