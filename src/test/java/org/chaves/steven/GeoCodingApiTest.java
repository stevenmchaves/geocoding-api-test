package org.chaves.steven; 

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
import static org.chaves.steven.constants.Constants.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

/**
 * Testing against Google geocoding api.
 */
public class GeoCodingApiTest {

    @BeforeClass
    public void beforeClass() {
        // Setting BaseURI once
        baseURI = "https://maps.googleapis.com/maps/api/geocode";
    }

    @BeforeMethod
    public void beforeMethod() {
        // Setting BaseURI once
        baseURI = "https://maps.googleapis.com/maps/api/geocode";
    }


    @Test(description="without supplying format, any parameters, and key you test its active and return is 404")
    public void testAPIActive() {
        Response response = given().get();
        response.then().statusCode(404);
        assertTrue(response.getBody().asString().contains("was not found on this server."),
                "An error should have been returned in the message");
    }

    @Test(description="without supplying format, any parameters you test its active and return is 404")
    public void testAPIActiveWithAddressKey() {
        Response response = given().get(defaultHappyAddress + "&key=" + API_KEY);
        response.then().statusCode(404);
        assertTrue(response.getBody().asString().contains("was not found on this server."),
                "An error should have been returned in the message");
    }

@Test(description="without supplying format, any parameters you test its active and return is 404")
public void testAPIActiveWithAddress() {
    Response response = given().get("&key=" + API_KEY);
    response.then().statusCode(404);
    assertTrue(response.getBody().asString().contains("was not found on this server."),
            "An error should have been returned in the message");
}
    @Test(description="without supplying format, any parameters you test its active and return is 404")
    public void testAPIActiveWithInvalidKey() {
        Response response = given().get(String.format("/json%s&key=%s11111", defaultHappyAddress, API_KEY));
        response.then().statusCode(200);
        JsonPath responseJson = response.jsonPath();
        assertEquals(responseJson.get("status"), "REQUEST_DENIED");
        assertEquals(responseJson.get("error_message"), "The provided API key is invalid.");

    }
}

