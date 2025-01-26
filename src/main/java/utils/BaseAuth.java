package utils;

import dataObject.HeadersConfiguration;
import endpoints.Endpoint;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;

public class BaseAuth {
    public HeadersConfiguration headerConfig;

    @BeforeMethod
    public void setBaseURL() {
        RestAssured.baseURI = Endpoint.Base_URL;
        headerConfig = new HeadersConfiguration();
    }
}
