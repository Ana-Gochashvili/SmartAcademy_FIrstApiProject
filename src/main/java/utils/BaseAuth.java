package utils;

import dataObject.HeadersConfiguration;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;

public class BaseAuth {
    public HeadersConfiguration headerConfig;

    @BeforeMethod
    public void setBaseURL() {
        RestAssured.baseURI = "https://bookstore.toolsqa.com/";
        headerConfig = new HeadersConfiguration();
    }
}
