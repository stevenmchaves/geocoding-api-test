package org.chaves.steven; 

import static io.restassured.RestAssured.*; 
import static org.testng.Assert.*;
import static org.chaves.steven.constants.Constants.*;

import java.util.ArrayList;
import java.util.Collection;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test; 

import io.restassured.parsing.Parser; 
import io.restassured.path.json.JsonPath; 
import io.restassured.response.Response; 

/**
 * Testing against Google geocoding api.
 */
public class GeoCodingApiJsonFormatTest {

    @BeforeClass
    public void beforeClass() {
        // Setting BaseURI once
        baseURI = "https://maps.googleapis.com/maps/api/geocode";
    }

    /**
     * (description="without supplying any parameters you test its active and return is 404")
     */
    @Test
    public void testAPIActive() {
        defaultParser = Parser.JSON;
        Response response = given().get();
        response.then().statusCode(404);
        assertTrue(response.getBody().asString().contains(EXPECTED_NOT_FOUND),
                "An error should have been returned in the message");
    }

    @Test(description = "API call asking for JSON output without KEY and Address")
    public void testAPIActiveJSON() {
        Response response = given().get("/json");
        response.then().statusCode(400);
        JsonPath responseJson = response.jsonPath();
        assertEquals(response.jsonPath().get("error_message"), MISSING_PARAMS);
        assertEquals(responseJson.get("status"), "INVALID_REQUEST");
        assertEquals(responseJson.get("results"), new ArrayList<Object>());
    }

    @Test(description = "API call asking for JSON output without KEY and Address")
    public void testAPIActiveJSONWithAddress() {
        Response response = given().get("/json" + DEFAULT_HAPPY_ADDRESS);
        response.then().statusCode(200);
        JsonPath responseJson = response.jsonPath();
        assertEquals(responseJson.get("error_message"), MISSING_KEY);
        assertEquals(responseJson.get("status"), "REQUEST_DENIED");
        assertEquals(responseJson.get("results"), new ArrayList<Object>());
    }

    @Test(description = "API call asking for JSON output without Address")
    public void testAPIActiveJSONWithKey() {
        Response response = given().get("/json&key=" + API_KEY);
        response.then().statusCode(404);
        assertTrue(response.getBody().asString().contains(EXPECTED_NOT_FOUND),
                "An error should have been returned in the message");
    }

    @Test(description = "API call asking for JSON output")
    public void testHappyPathJSON() {
        Response response = given().get("/json" + DEFAULT_HAPPY_ADDRESS + "&key=" + API_KEY);
        response.then().statusCode(200);
        JsonPath responseJson = response.jsonPath();
        assertNull(responseJson.get("error_message"));
        assertEquals(responseJson.get("status"), "OK");
        Collection<?> actualResults = responseJson.get("results");
        assertEquals(actualResults.size(), 1);
    }
}
